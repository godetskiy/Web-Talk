package WebTalk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
/**
 * User: alex
 * Date: 21.02.13
 * Time: 22:48
 */
public class Box extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Получение usr_id из сессии
        HttpSession hs = request.getSession();
        int usr_id = Integer.valueOf(hs.getAttribute("id_usr").toString());
        String usr_name = hs.getAttribute("name").toString();
        String htmlText = "<tr style='text-align: center'><td>Дата</td><td>Информация</td><td>Тема</td></tr>"; //html текст
        Message message[] = Message.getMessagesArray(usr_id);

        if (message == null) {
            request.setAttribute("err", "Внутренняя ошибка");
            request.getRequestDispatcher("box.jsp").forward(request, response);
            return;
        }
        if (message.length == 0) {
            htmlText += "<tr><td colspan='3'>Сообщения отсутствуют</td></tr>";
        } else {
            for (int i = 0; i < message.length; i++) {
                htmlText += "<tr>";
                //1 часть. Дата
                htmlText += "<td>" + message[i].getDate() + "</td>";
                //2 часть. Информация
                htmlText += "<td>";
                if (message[i].getSender() == usr_id) {
                    //Исходящее сообщение
                    htmlText += "Исходящее к " + usr_name + ".";
                } else {
                    //Входящее сообщение
                    String str = User.getNameById(message[i].getSender());
                    htmlText += "Входящее от " + str + ".";
                }
                htmlText += "</td>";
                //3 часть. Тема
                htmlText += "<td><a href='/view?msg_id=" +
                        message[i].getMsg_id() + "'> " + message[i].getSubject() + "</td></tr>";
            }
        }
        //Переход в ящик писем
        request.setAttribute("htmlText", htmlText);
        request.getRequestDispatcher("box.jsp").forward(request, response);
        return;

    }
}
