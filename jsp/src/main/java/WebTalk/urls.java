package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class urls extends HttpServlet {

    private void manageUrls(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        switch (uri) {
            //Home page
            case "/":
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;
            case "/login":
                Authentication.login(request, response);

                break;
            case "/logout":
                Authentication.logout(request, response);
                break;
            default:
                response.sendRedirect("/");
                break;
        }
        return;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.manageUrls(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.manageUrls(request, response);
    }
}
