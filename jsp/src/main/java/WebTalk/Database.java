package WebTalk;

import com.sun.corba.se.pept.transport.ConnectionCache;

import java.sql.*;
public class Database {
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String JDBC_URL = "jdbc:h2:tcp://localhost/test";
    public static final String JDBC_USER = "sa";
    public static final String JDBC_PASSWORD = "";

    private Connection connection;

    Database() {
        connection = null;
    }

    public boolean createConnection(){
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_URL,JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    protected void finalize (){
        this.closeConnection();
    }

    public Connection getConnection() {return connection;}
}