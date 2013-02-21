package WebTalk;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * User: alex
 * Date: 21.02.13
 * Time: 1:57
 */

public class User {
    public String username;
    public String name;
    public String password;		//пароль пользователя в виде MD5

    User() {
        username = null;
        password = null;
        name = null;
    }

    User(String newLogin, String newName, String newPassword) {
        this.setUser(newLogin, newName, newPassword);
    }

    public static String createTableSQL() {
        return "drop table if exists user; " +
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
        password = this.getMD5(newPassword);
        return true;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}

}
