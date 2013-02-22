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
    private String getUsersList(int curr_id, int sel_id) throws SQLException, ClassNotFoundException {
        Database db = new Database();
        String htmlText = "";
        db.createConnection();
        String sql = "SELECT * FROM user;";
        ResultSet rs = db.executeQuery(sql);
        if (sel_id == -1)
            htmlText += "<option value='' SELECTED> Выберите получателя";
        while (rs.next()) {
            String tbUsername = rs.getString("USERNAME");
            String tbName = rs.getString("NAME");
            int id_usr = rs.getInt("ID_USR");
            //Новое сообщение
            if (curr_id != id_usr)  //Исключение пользователя из списка
                if (curr_id == sel_id)
                    htmlText += "<option value='" + id_usr + "' SELECTED> " + tbName + " (" + tbUsername + ")";
                else
                    htmlText += "<option value='" + id_usr + "'> " + tbName + " (" + tbUsername + ")";
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

        try {
            //Запись значения в базу данных
            Database db = new Database();
            db.createConnection();
            String sql = newMsg.getSQL();
            db.executeSQL(sql);
            response.sendRedirect("/box");
            return;
        } catch (SQLException e) {
            request.setAttribute("err", "Ошибка SQL");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        } catch (ClassNotFoundException e) {
            request.setAttribute("err", "Драйвер базы данных не найден");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Создание формы нового или предзаполненого сообщения

        //Получение usr_id из сессии
        HttpSession hs = request.getSession();
        int usr_id = Integer.valueOf(hs.getAttribute("id_usr").toString());

        String options; //html текст для поля "кому"

        //Определение новое или предзаполненоe сообщение
        int to_id = -1;
        int msg_id;
        String subject = "";
        try {
            msg_id = Integer.valueOf(request.getParameter("msg_id").toString());
        } catch (NullPointerException e) {
            msg_id = -1;
        }

        //Получение текста для поля "Кому"
        try {
            if (msg_id == -1) {
                //Новое сообщение
                to_id = -1;
            } else {
                Message msg = Message.getMessageById(msg_id);
                subject = "RE: " + msg.getSubject();
                to_id = msg.getTo();
            }
            options = this.getUsersList(usr_id, to_id);
        } catch (SQLException e) {
            //error
            request.setAttribute("err", "Ошибка SQL");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        } catch (ClassNotFoundException e) {
            //error
            request.setAttribute("err", "Драйвер базы данных не найден");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        }
        request.setAttribute("options", options);
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("msg_form.jsp").forward(request, response);
        return;
    }
}
