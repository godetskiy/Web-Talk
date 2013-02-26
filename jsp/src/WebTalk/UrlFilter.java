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
        boolean logged;
        try {
            logged = Boolean.valueOf(request.getSession().getAttribute("logged").toString());
            if (logged) {
                chain.doFilter(req, resp);
                return;
            }
        } catch (NullPointerException e) {

        }
        response.sendRedirect("/");
        return;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
