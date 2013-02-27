package WebTalk;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class urls extends HttpServlet {
    public void init(ServletConfig sc) throws ServletException {
        String sql_user = "create table if not exists user(" +
                "id_usr int auto_increment primary key, " +
                "username varchar(50), " +
                "name varchar(50), " +
                "password varchar(50)" +
                ");";
        String sql_message = //"drop table if exists message; " +
                "create table if not exists message(" +
                        "id_msg int auto_increment, " +
                        "idfrom int,  " +
                        "idto int, " +
                        "subject varchar(30), " +
                        "text clob, " +
                        "date varchar(40), " +
                        "foreign key (idfrom) REFERENCES user (id_usr), " +
                        "foreign key (idto) REFERENCES user (id_usr), " +
                        ");";
        //Создание таблиц
        Database db = new Database();
        if (db.createConnection()) {
            try {
                PreparedStatement ps = db.getConnection().prepareStatement(sql_user);
                ps.executeUpdate();
                ps = db.getConnection().prepareStatement(sql_message);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void manageUrls(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/login":
                Authentication.login(request, response);
                break;
            case "/logout":
                Authentication.logout(request, response);
                break;
            case "/registration":
                Authentication.registration(request, response);
                break;
            case "/box":
                Box.getMessagesArray(request, response);
                break;
            case "/message":
                Box.createNewMessage(request, response);
                break;
            case "/view":
                Box.viewMessage(request, response);
                break;
            case "/send":
                Box.send(request, response);
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
