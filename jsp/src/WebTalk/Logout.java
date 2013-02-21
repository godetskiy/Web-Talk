package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;
/**
 * User: alex
 * Date: 21.02.13
 * Time: 14:38
 */
public class Logout extends HttpServlet {
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession hs = request.getSession();
        hs.setAttribute("logged", false);
        response.sendRedirect("/");
        return;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.logout(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.logout(request, response);
    }
}
