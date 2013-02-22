package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * User: alex
 * Date: 22.02.13
 * Time: 4:47
 */
public class View extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int msg_id = Integer.valueOf(request.getParameter("msg_id"));
        HttpSession hs = request.getSession();
        int usr_id = Integer.valueOf(hs.getAttribute("id_usr").toString());
        Message msg = null;
        msg = Message.getMessageById(msg_id);
        request.setAttribute("to", User.getNameById(msg.getTo()));
        request.setAttribute("subject", msg.getSubject());
        request.setAttribute("text", msg.getText());
        request.setAttribute("action", "/message?msg_id=" + msg_id);
        if (msg.getSender() == usr_id)
            request.setAttribute("type", "hidden");
        else
            request.setAttribute("type", "submit");
        request.getRequestDispatcher("msg_view.jsp").forward(request, response);
    }
}
