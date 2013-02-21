package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: alex
 * Date: 21.02.13
 * Time: 1:02
 */
public class Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_usr = -1;
        String username = request.getParameter("username");
        String password = User.getMD5(request.getParameter("password"));
        String name = request.getParameter("name");
        Database db = new Database();
        boolean find = false;   //found same user
        String msg = "";
        try {
            db.createConnection();
            String sql = "SELECT * FROM user;";
            ResultSet rs = db.executeQuery(sql);
            boolean res = rs.next();     //Result rs.next() operation
            while ((res == true) && (find == false)) {
                String tbUsername = rs.getString("USERNAME");
                String tbName = rs.getString("NAME");
                id_usr = rs.getInt("ID_USR");
                if (tbUsername.equals(username) && tbName.equals(name)) {
                    find = true;
                }
                res = rs.next();
            }
            if (find == true) {
                //error: username уже занят
                request.setAttribute("err", "Username и имя пользователя уже заняты");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
                return;
            } else {
                //Запись нового пользователя в базу
                User newUser = new User(username, name, password);
                db.executeSQL(newUser.getSQL());

                //Создание сесии пользователя
                HttpSession hs = request.getSession(true);
                hs.setAttribute("logged", true);
                hs.setAttribute("username", username);
                hs.setAttribute("id_usr", id_usr);
                response.sendRedirect("/");
                return;
            }
        } catch (SQLException e) {
            //error
            request.setAttribute("err", "Ошибка SQL");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        } catch (ClassNotFoundException e) {
            //error
            request.setAttribute("err", "Драйвер базы данных не найден");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
