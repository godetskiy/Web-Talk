package WebTalk;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class Message {
    private int msg_id;
    private int idFrom;
    private int idTo;
    private String subject;
    private String text;
    private String date;

    Message() {
        msg_id = -1;
        idFrom = -1;
        idTo = -1;
        subject = new String();
        text = new String();
    }

    Message(int from, int to, String newSubject, String newText) {
        this.setMessage(from, to, newSubject, newText, this.createDate());
    }

    Message(int newID, int from, int to, String newSubject, String newText, String newDate) {
        this.setMsg_id(newID);
        this.setMessage(from, to, newSubject, newText, newDate);
    }

    /*public void createNewMessage(int from, int to, String newSubject, String newText) {
        this.setMessage(from, to, newSubject, newText, this.createDate());
    } */

    public static String createTableSQL() {
        return  //"drop table if exists message; " +
                "create table message(" +
                "id_msg int auto_increment, " +
                "idfrom int,  " +
                "idto int, " +
                "subject varchar(30), " +
                "text clob, " +
                "date varchar(40), " +
                "foreign key (idfrom) REFERENCES user (id_usr), " +
                "foreign key (idto) REFERENCES user (id_usr), " +
                ");";
    }

    public String getSQL() {
        return "insert into message(" +
                "idfrom, idto, subject, text, date) values(" +
                String.valueOf(idFrom) + ", " + String.valueOf(idTo) + ", '" +
                subject + "', '" + text + "', '" + date + "');";
    }

    public boolean save() {
        Database db = new Database();
        boolean result = false;
        if (!db.createConnection()) {
            result = false;
        } else {
            Connection conn = db.getConnection();
            String sql_str = "INSERT INTO message(idfrom, idto, subject, text, date) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql_str);
                ps.setInt(1, idFrom);
                ps.setInt(2, idTo);
                ps.setString(3, subject);
                ps.setString(4, text);
                ps.setString(5, date);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                result = false;
            }
            result = true;
        }
        return result;
    }

    //Получение сообщения по его id
    public static Message getMessageById(int msg_id){
        Database db = new Database();
        Message newMessage = null;
        if (!db.createConnection())
            return null;
        String query_txt = "SELECT * FROM MESSAGE WHERE ID_MSG = " + msg_id + ";";
        ResultSet rs = db.executeQuery(query_txt);
        try {
            rs.next();
            newMessage = new Message();
            newMessage.setMessage(rs.getInt("IDFROM"), rs.getInt("IDTO"), rs.getString("SUBJECT"),
                    rs.getString("TEXT"), rs.getString("DATE"));
        } catch (SQLException e) {
            return null;
        }
        return newMessage;
    }

   /* //Получение массива сообщений для заданного пользователя
    public static Message[] getMessagesArray(int usr_id) {
        Message result[] = new Message[0];       //Результат
        int count = 0;              //Кол-во в результате
        Database db = new Database();
        ResultSet rs = null;        //Результат запроса
        if (!db.createConnection())
            return null;

        try {
            //Подсчет кол-ва записей
            String query = "SELECT COUNT(*) AS COUNT FROM MESSAGE " +
                    "WHERE IDFROM = " + usr_id + " OR IDTO = " + usr_id;
            rs = db.executeQuery(query);
            if (rs != null) {
                //Запрос выполнился
                rs.next();
                count = rs.getInt("COUNT");
                result = new Message[count];
            } else {
                return null;
            }

            query = "SELECT * FROM MESSAGE WHERE IDFROM = " + usr_id + " OR IDTO = " + usr_id + "" +
                    " ORDER BY DATE DESC";
            rs = db.executeQuery(query);
            for (int i = 0; rs.next(); i++) {
                result[i] = new Message();
                result[i].setMessage(rs.getInt("IDFROM"), rs.getInt("IDTO"), rs.getString("SUBJECT"),
                        rs.getString("TEXT"), rs.getString("DATE"));
                result[i].setMsg_id(rs.getInt("ID_MSG"));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    } */

    private String createDate() {
        return (new SimpleDateFormat()).format(new Date());
    }

    public void setMessage(int from, int to, String newSubject, String newText, String newDate) {
        idFrom = from;
        idTo = to;
        subject = new String(newSubject);
        text = new String(newText);
        date = newDate;
    }


    public int getSender() {return idFrom;}
    public int getTo() {return idTo;}
    public String getSubject() {return subject;}
    public String getText() {return text;}
    public String getDate() {return date;}
    public int getMsg_id() {return msg_id;};
    public void setMsg_id(int newMsgId) {msg_id = newMsgId;}


}
