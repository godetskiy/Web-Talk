package WebTalk;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class UrlFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        HttpSession hs = request.getSession();
        User user = (User) hs.getAttribute("user");


        if (user != null && user.isLogged()) {
            chain.doFilter(req, resp);
            return;
        } else {
            response.sendRedirect("/");
            return;
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
