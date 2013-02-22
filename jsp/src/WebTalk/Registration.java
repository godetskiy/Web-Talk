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
        int id_usr = -1;    //id текущего пользователя
        //Параметры из POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        boolean find = false;   //Найдено совпадение username && имя пользователя
        User[] user = User.getUsersArray();

        if (user == null) {
            //Если не удалось соединиться
            request.setAttribute("err", "Драйвер базы данных не найден");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        for (int i = 0; (i < user.length) && (find == false); i++) {
            if (user[i].getUsername().equals(username) &&
                    user[i].getName().equals(name)) {
                //Если найдены username && имя пользователя
                find = true;
            }
        }

        if (find == true) {
            //error: занято
            request.setAttribute("err", "Username и имя пользователя уже заняты");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        } else {
            //Запись нового пользователя в базу
            User newUser = new User(username, name, password);
            if (!newUser.save()) {
                //Ошибка записи
                request.setAttribute("err", "Ошибка SQL");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            //Создание сесии пользователя
            HttpSession hs = request.getSession(true);
            hs.setAttribute("logged", true);
            hs.setAttribute("username", username);
            hs.setAttribute("id_usr", id_usr);
            response.sendRedirect("/");
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
