package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    public static void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User tmpUser = new User();
        boolean find = false;   //найдено совпадение username & password
        Database db = new Database();

        if (!db.createConnection()) {

        } else {
            Connection con = db.getConnection();
            String query_txt = "SELECT * FROM user WHERE username  = ? AND password = ?";
            try {
                PreparedStatement ps = con.prepareStatement(query_txt);
                ps.setString(1, request.getParameter("username"));
                ps.setString(2, request.getParameter("password"));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tmpUser = new User(true, rs.getInt("ID_USR"),
                            rs.getString("USERNAME"),
                            rs.getString("NAME"));
                    find = true;
                }
            } catch (SQLException e) {

            }
            //Закрытие соединения с базой
        }
        if (find) {
            //Создание сесии пользователя
            HttpSession hs = request.getSession(true);
            hs.setAttribute("user", tmpUser);
            response.sendRedirect("/");
            return;
        } else {
            request.setAttribute("err", "Неверное username или пароль");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hs = request.getSession();
        hs.invalidate();
        response.sendRedirect("/");
        return;
    }
}
