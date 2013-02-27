package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
public class Box{

    private static ResultSet executeQuery(Connection con, String query_str, int usr_id) throws SQLException{
        PreparedStatement ps = con.prepareStatement(query_str);
        ps.setInt(1, usr_id);
        ps.setInt(2, usr_id);
        return ps.executeQuery();
    }


    public static void getMessagesArray(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Получение usr_id из сессии
        int usr_id = Authentication.fetchUserId(request);

        if (usr_id == -1) {
            request.setAttribute("error", "Авторизация отсутствует");
            request.getRequestDispatcher("box.jsp").forward(request, response);
            return;
        }

        //Получение списка сообщение
        int count = 0;
        Message result[] = new Message[count];
        Database db = new Database();
        if (!db.createConnection()) {

        } else {
            Connection con = db.getConnection();
            String query_txt_count = "SELECT count(*) AS count FROM message " +
                    "WHERE idfrom = ? OR idto = ?";
            String query_txt = "SELECT * FROM message WHERE idfrom = ? OR idto = ? ORDER BY date";
            try {
                //Подсчет кол-ва писем для заданного usr_id
                ResultSet rs = Box.executeQuery(con, query_txt_count, usr_id);
                while (rs.next()) {
                    count = rs.getInt("count");
                }

                //Получение массива писем
                result = new Message[count];
                rs = Box.executeQuery(con, query_txt, usr_id);
                int i = 0;
                while (rs.next()) {
                    result[i] = new Message(rs.getInt("ID_MSG"),rs.getInt("IDFROM"), rs.getInt("IDTO"),
                            rs.getString("SUBJECT"), rs.getString("TEXT"), rs.getString("DATE"));
                    i++;
                }
            } catch (SQLException e) {

            }
            //Закрытие соединения с базой
        }
        //Переход в ящик писем
        request.setAttribute("messages", result);
        request.getRequestDispatcher("box.jsp").forward(request, response);
        return;
    }

    private static User[] getUsersArray() {
        Database db = new Database();
        ResultSet rs = null;            //Результат запроса
        int count = 0;                  //Кол-во возвращаемых объектов
        User[] result = new User[count];           //Возвращаемый результат

        if (!db.createConnection()) {

        } else {
            try {
                Connection conn = db.getConnection();
                String query_str = "";
                PreparedStatement ps = null;

                //Кол-во пользователей в таблице
                query_str = "SELECT count(*) AS count FROM user";
                ps = conn.prepareStatement(query_str);
                rs = ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("COUNT");
                }

                //Получение данных
                result = new User[count];
                query_str = "SELECT * FROM user";
                ps = conn.prepareStatement(query_str);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    result[i] = new User(rs.getInt("ID_USR"), rs.getString("USERNAME"), rs.getString("NAME"));
                    i++;
                }
            } catch (SQLException e) {

            }
            //Закрытие базы данных
        }
        return result;
    }

    public static void createNewMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", Box.getUsersArray());

        int sel_id = -1;
        String subject = "";

        try {
            sel_id = Integer.valueOf(request.getParameter("sel_id"));
            subject = request.getParameter("subject");
        } catch (NullPointerException e) {
            sel_id = -1;
            subject = "";
        } catch (NumberFormatException e) {

        }

        if (sel_id != -1) {
            //Предзаполненое сообщение
            subject = "RE: " + subject;
        }

        request.setAttribute("sel_id", sel_id);
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("msg_form.jsp").forward(request, response);
    }

    public static void viewMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int msg_id = -1;
        int usr_id = Authentication.fetchUserId(request);

        try {
            msg_id = Integer.valueOf(request.getParameter("msg_id"));
        } catch (NullPointerException e) {
            request.setAttribute("err", "Ошибка передачи параметра");
            request.getRequestDispatcher("box.jsp").forward(request, response);
            return;
        }

        Message msg = Message.getMessageById(msg_id);
        if (msg != null) {
            request.setAttribute("to", User.getNameById(msg.getTo()));
            request.setAttribute("subject", msg.getSubject());
            request.setAttribute("text", msg.getText());
            String action = "/message?sel_id=" + msg.getSender() + "&subject=" + msg.getSubject();
            request.setAttribute("action", action);
            if (msg.getSender() == usr_id)
                request.setAttribute("type", "hidden");
            else
                request.setAttribute("type", "submit");
        }
        request.getRequestDispatcher("msg_view.jsp").forward(request, response);
    }

    public static void send(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Отправка нового сообщения
        //Получение данных
        String subject = request.getParameter("subject");
        String text = request.getParameter("msg");
        int idFrom = -1;
        int idTo = -1;
        HttpSession hs = request.getSession();
        try {
            User tmpUser = (User) hs.getAttribute("user");
            idFrom = tmpUser.getUsr_id();
            idTo = Integer.valueOf(request.getParameter("to"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if ((idFrom == -1) || (idTo == -1)) {
            //Если не инициализировались поля
            request.setAttribute("err", "Ошибка передачи параметров");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        }

        //Запись соообщения в БД
        Message newMsg = new Message(idFrom, idTo, subject, text);
        if (!newMsg.save()) {
            //Ошибка записи в БД
            request.setAttribute("err", "Ошибка записи в базу данных");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/box");
        return;
    }
}
