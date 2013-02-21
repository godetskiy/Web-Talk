package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: alex
 * Date: 21.02.13
 * Time: 1:02
 */
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = User.getMD5(request.getParameter("password"));
        Database db = new Database();
        boolean find = false;   //found same username/password
        String msg = "";
        try {
            db.createConnection();
            String sql = "SELECT * FROM user;";
            ResultSet rs = db.executeQuery(sql);
            boolean res = rs.next();     //Result rs.next() operation
            while ((res == true) && (find == false)) {
                String tbUsername = rs.getString("USERNAME");
                String tbPassword = rs.getString("PASSWORD");
                if (tbUsername.equals(username) && tbPassword.equals(password)) {
                    find = true;
                }
                res = rs.next();
            }
        } catch (SQLException e) {
            //error
            request.setAttribute("err", "SQL error");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } catch (ClassNotFoundException e) {
            //error
            request.setAttribute("err", "Database error");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        if (find == true) {
            //found match username/password
            response.sendRedirect("/box");
            return;
        } else {
            request.setAttribute("err", "Wrong username or password");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
