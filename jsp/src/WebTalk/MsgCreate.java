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
    private String getUsersList() throws SQLException, ClassNotFoundException {
        Database db = new Database();
        String htmlText = "";
        db.createConnection();
        String sql = "SELECT * FROM user;";
        ResultSet rs = db.executeQuery(sql);
        htmlText += "<option value='' SELECTED> Выберите получателя";
        while (rs.next()) {
            String tbUsername = rs.getString("USERNAME");
            String tbPassword = rs.getString("PASSWORD");
            String tbName = rs.getString("NAME");
            int id_usr = rs.getInt("ID_USR");
            htmlText += "<option value='" + id_usr + "'> " + tbName + " (" + tbUsername + ")";
        }
        return htmlText;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        try {
            String options = this.getUsersList();
            request.setAttribute("options", options);
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
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

    }
}
