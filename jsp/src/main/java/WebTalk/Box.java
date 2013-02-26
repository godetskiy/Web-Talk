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
        HttpSession hs = request.getSession();
        String strId = (String) hs.getAttribute("id_usr");
        if (strId == null) {
            request.setAttribute("error", "Авторизация отсутствует");
            request.getRequestDispatcher("box.jsp").forward(request, response);
            return;
        }
        int usr_id = Integer.valueOf(strId);

        //Получение списка сообщение
        int count = 0;
        Message result[] = new Message[count];
        Database db = new Database();
        if (!db.createConnection()) {

        } else {
            Connection con = db.getConnection();
            String query_txt_count = "SELECT count(*) AS count FROM message " +
                    "WHERE idfrom = ? OR idto = ?";
            String query_txt = "SELECT * FROM message WHERE idfrom = ? OR idto = ?";
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

    public static void createNewMessage() {

    }

    public static void viewMessage() {

    }
}
