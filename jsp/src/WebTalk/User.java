package WebTalk;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: alex
 * Date: 21.02.13
 * Time: 1:57
 */

public class User {
    private int usr_id;
    private String username;
    private String name;
    private String password;		//пароль пользователя в виде MD5

    User() {
        username = null;
        password = null;
        name = null;
    }

    User(String newLogin, String newName, String newPassword) {
        this.setUser(newLogin, newName, newPassword);
    }

    public static String createTableSQL() {
        return //"drop table if exists user; " +
                "create table user(" +
                "id_usr int auto_increment primary key, " +
                "username varchar(50), " +
                "name varchar(50), " +
                "password varchar(50)" +
                ");";
    }

    public String getSQL() {
        return "insert into user (username, name, password) values('" + username +"', '" + name +"', '" + password + "');";
    }

    public boolean save() {
        Database db = new Database();
        if (!db.createConnection())
            return false;
        if (!db.executeSQL(this.getSQL()))
            return false;
        return true;
    }

    public static User[] getUsersArray() {
        Database db = new Database();
        ResultSet rs = null;            //Результат запроса
        User[] result = null;           //Возвращаемый результат
        int count = 0;                  //Кол-во возвращаемых объектов
        if (!db.createConnection())
            return null;

        try {
            //Кол-во пользователей в таблице
            rs = db.executeQuery("SELECT COUNT(*) AS COUNT FROM user;");
            if (rs != null) {
                //Запрос выполнился
                rs.next();
                count = rs.getInt("COUNT");
                result = new User[count];
            } else {
                return null;
            }

            //Получение данных
            rs = db.executeQuery("SELECT * FROM user;");
            for (int i = 0; rs.next(); i++) {
                result[i] = new User(rs.getString("USERNAME"), rs.getString("NAME"), rs.getString("PASSWORD"));
                result[i].setUsr_id(rs.getInt("ID_USR"));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    public static String getNameById(int usr_id) {
        String query_txt = "SELECT * FROM USER WHERE ID_USR = " + usr_id +";";
        String name = "";
        try {
            Database db = new Database();
            db.createConnection();
            ResultSet rs = db.executeQuery(query_txt);
            if (rs.next()) {
                name = rs.getString("NAME");
            }
        } catch (SQLException e) {
            name = null;
        }
        return name;
    }

    public static String getMD5(String str) {
        byte bytesOfMessage[] = null;		//пароль в виде массива байт
        byte theDigest[] = null;					//MD5 пароль
        try {
            bytesOfMessage = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            theDigest = md.digest(bytesOfMessage);
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch(NoSuchAlgorithmException e) {
            return null;
        }
        return new String(theDigest);
    }

    public boolean setUser(String newUsername, String newName, String newPassword) {
        username = newUsername;
        name = newName;
        password = newPassword;
        return true;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getUsr_id() {return usr_id;}
    private void setUsr_id(int newUsrId) {usr_id = newUsrId;}
    public String getName() {return name;}

}
