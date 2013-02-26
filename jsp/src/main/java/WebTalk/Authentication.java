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
    public static int fetchUserId(HttpServletRequest request) {
        HttpSession hs = request.getSession();
        User tmpUser = (User) hs.getAttribute("user");
        if (tmpUser != null) return tmpUser.getUsr_id();
            else return -1;
    }

    private static User find(String query_str, String param1, String param2) {
        Database db = new Database();
        User result = null;
        if (!db.createConnection()) {

        } else {
            Connection con = db.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement(query_str);
                ps.setString(1, param1);
                ps.setString(2, param2);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result = new User(true, rs.getInt("ID_USR"),
                            rs.getString("USERNAME"),
                            rs.getString("NAME"));
                }
            } catch (SQLException e) {

            }
            //Закрытие соединения с базой
        }
        return result;
    }

    private static void createUserSession(HttpServletRequest request, User user) {
        HttpSession hs = request.getSession(true);
        hs.setAttribute("user", user);
    }

    public static void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String query_txt = "SELECT * FROM user WHERE username  = ? AND password = ?";

        User tmpUser = Authentication.find(query_txt, username, password);

        if (tmpUser != null) {
            //Создание сесии пользователя
            Authentication.createUserSession(request, tmpUser);

            response.sendRedirect("/");
        } else {
            request.setAttribute("err", "Неверное username или пароль");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        return;
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hs = request.getSession();
        hs.invalidate();
        response.reset();
        response.sendRedirect("/");
        return;
    }

    public static void registration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String query_txt = "SELECT * FROM user WHERE username  = ? AND name = ?";

        User tmpUser = Authentication.find(query_txt, username, name);

        if (tmpUser == null) {
            tmpUser = new User(username, name, password);
            if (tmpUser.save()) {
                tmpUser = Authentication.find(query_txt, username, name);
                tmpUser.setLogged();

                //Создание сесии пользователя
                Authentication.createUserSession(request, tmpUser);
                response.sendRedirect("/");
            } else {
                request.setAttribute("err", "Ошибка записи в базу данных");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("err", "Username и имя пользователя уже заняты");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        }
        return;
    }
}
