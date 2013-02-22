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
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_usr = -1;    //id текущего пользователя
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = "";       //Имя пользователя
        boolean find = false;   //найдено совпадение username & password

        User[] user = User.getUsersArray();

        if (user == null) {
            //Если не удалось соединиться
            request.setAttribute("err", "Драйвер базы данных не найден");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        for (int i = 0; (i < user.length) && (find == false); i++) {
            if (user[i].getUsername().equals(username) &&
                    user[i].getPassword().equals(password)) {
                //Если совпадают
                find = true;
                name = user[i].getName();
                id_usr = user[i].getUsr_id();
            }
        }
        if (find == true) {
            //Создание сесии пользователя
            HttpSession hs = request.getSession(true);
            hs.setAttribute("logged", true);
            hs.setAttribute("username", username);
            hs.setAttribute("name", name);
            hs.setAttribute("id_usr", id_usr);
            response.sendRedirect("/");
            return;
        } else {
            request.setAttribute("err", "Неверное username или пароль");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
