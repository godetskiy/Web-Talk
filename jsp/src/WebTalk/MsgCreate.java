package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
/**
 * User: alex
 * Date: 21.02.13
 * Time: 16:46
 */
public class MsgCreate extends HttpServlet {

    private String getUsersList(int curr_id, int sel_id) {
        //curr_id - пользователь на исключение из списка
        //sel_id - выделенный пользователь
        String htmlText = "";   //результат
        User[] user = User.getUsersArray();
        if (user == null) {
            //Не удалось считать из базы
            return null;
        }
        if (sel_id == -1)
            htmlText += "<option value='' SELECTED> Выберите получателя";

        for (int i = 0; i < user.length; i++) {
            int usr_id = user[i].getUsr_id();
            if (curr_id != usr_id)      //Исключение пользователя из списка
                if (curr_id == sel_id)  //Выделить пользователя в списке
                    htmlText += "<option value='" + usr_id + "' SELECTED> " +
                            user[i].getName() + " (" + user[i].getUsername() + ")";
                else
                    htmlText += "<option value='" + usr_id + "'> " +
                            user[i].getName() + " (" + user[i].getUsername() + ")";
        }
        return htmlText;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Отправка нового сообщения
        //Получение данных
        HttpSession hs = request.getSession();
        int idFrom = Integer.valueOf(hs.getAttribute("id_usr").toString());
        int idTo = Integer.valueOf(request.getParameter("to"));
        String subject = request.getParameter("subject");
        String text = request.getParameter("msg");

        //Инициализация нового сообщения
        Message newMsg = new Message();
        newMsg.createNewMessage(idFrom, idTo, subject, text);

        //Запись значения в базу данных
        Database db = new Database();
        if (!db.createConnection()) {
            //Если не удалось соединиться
            request.setAttribute("err", "Ошибка соединения с базой");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        }

        if (!db.executeSQL(newMsg.getSQL())) {
            request.setAttribute("err", "Ошибка SQL");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/box");
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Создание формы нового или предзаполненого сообщения

        //Получение usr_id из сессии
        HttpSession hs = request.getSession();
        int usr_id = Integer.valueOf(hs.getAttribute("id_usr").toString());
        String options;     //html текст для поля "кому"
        //Определение новое или предзаполненоe сообщение
        int msg_id = -1;    //id сообщения
        Message msg = null; //Новое сообщение

        try {
            msg_id = Integer.valueOf(request.getParameter("msg_id"));
        } catch (NullPointerException e) {
            //Параметр не был передан (сообщение новое)
            msg_id = -1;
        } catch (NumberFormatException e) {
            msg_id = -1;
        }


        //Получение поля "Кому"
        //try {
        int to_id = -1;     //id получателя
        if (msg_id == -1) {
            //Новое сообщение
            to_id = -1;
        } else {
            msg = Message.getMessageById(msg_id);
            if (msg == null) {
                request.setAttribute("err", "Внутренняя ошибка");
                request.getRequestDispatcher("msg_form.jsp").forward(request, response);
                return;
            }
            to_id = msg.getTo();
        }
        options = this.getUsersList(usr_id, to_id);
        request.setAttribute("options", options);
        if (msg_id != -1)
            request.setAttribute("subject", "RE: " + msg.getSubject());
        request.getRequestDispatcher("msg_form.jsp").forward(request, response);
        return;
    }
}
