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
        HttpSession hs = request.getSession();
        int usr_id = Integer.valueOf(hs.getAttribute("id_usr").toString());
        String usr_name = hs.getAttribute("name").toString();
        String query = "SELECT * FROM MESSAGE WHERE IDFROM = " + usr_id + " OR IDTO = " + usr_id + " ORDER BY DATE DESC";
        Database db = new Database();
        String msgs = "<tr><td>Дата</td><td>Информация</td><td>Тема</td></tr>";

        try {
            db.createConnection();
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                int msg_id = rs.getInt("ID_MSG");
                Message tbMessage = new Message();
                tbMessage.setMessage(rs.getInt("IDFROM"), rs.getInt("IDTO"), rs.getString("SUBJECT"),
                        rs.getString("TEXT"), rs.getString("DATE"), rs.getBoolean("TYPE"), rs.getBoolean("READ"));
                msgs += "<tr>";
                //1 часть. Дата
                msgs += "<td>" + tbMessage.getDate() + "</td>";
                //2 часть. Информация
                msgs += "<td>";
                if (tbMessage.getSender() == usr_id) {
                    //Исходящее сообщение
                    msgs += "Исходящее к " + usr_name + ".";
                } else {
                    //Входящее сообщение
                    String str = User.getNameById(tbMessage.getSender());
                    msgs += "Входящее от " + str + ".";
                }
                msgs += "</td>";
                //3 часть. Тема
                msgs += "<td>Тема: <a href='/view?msg_id=" + msg_id + "'> " + tbMessage.getSubject() + "</td>";
                msgs += "</tr>";
            }
        } catch (SQLException e) {
            request.setAttribute("err", "Ошибка SQL");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
            return;
        } catch (ClassNotFoundException e) {
            request.setAttribute("err", "Драйвер базы данных не найден");
            request.getRequestDispatcher("msg_form.jsp").forward(request, response);
        }
        //Переход в ящик писем
        request.setAttribute("msgs", msgs);
        request.getRequestDispatcher("box.jsp").forward(request, response);
        return;
    }
}
