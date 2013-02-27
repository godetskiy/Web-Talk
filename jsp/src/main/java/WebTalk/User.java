package WebTalk;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private boolean logged;
    private int usr_id;
    private String username;
    private String name;
    private String password;

    User() {
        logged = false;
        usr_id = -1;
        username = null;
        password = null;
        name = null;
    }

    User(String newLogin, String newName, String newPassword) {
        this.setUser(newLogin, newName, newPassword);
    }

    User(int newId, String newLogin, String newName) {
        usr_id = newId;
        username = newLogin;
        name = newName;
    }

    User(boolean newLogged, int newId, String newUsername, String newName) {
        logged = newLogged;
        usr_id = newId;
        username = newUsername;
        name = newName;
    }

    public boolean save() {
        Database db = new Database();
        if (!db.createConnection()) {
            return false;
        }
        String sql_str = "INSERT INTO user (username, name, password) VALUES (?, ?, ?);";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql_str);
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.closeConnection();
        }

        return true;
    }



    public static String getNameById(int usr_id) {
        String query_str = "SELECT * FROM user WHERE ID_USR = ?";
        String result = "";
        Database db = new Database();
        if (db.createConnection()) {
            try {
                PreparedStatement ps = db.getConnection().prepareStatement(query_str);
                ps.setInt(1, usr_id);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    result = rs.getString("NAME");
                }
            } catch (SQLException e) {
                return result;
            } finally {
                db.closeConnection();
            }
        }
        return result;
    }

    public boolean setUser(String newUsername, String newName, String newPassword) {
        username = newUsername;
        name = newName;
        password = newPassword;
        return true;
    }

    public String getUsername() {return username;}
    public int getUsr_id() {return usr_id;}
    //private void setUsr_id(int newUsrId) {usr_id = newUsrId;}
    public String getName() {return name;}
    public boolean isLogged() {return logged;}
    public void setLogged() {logged = true;}
}
