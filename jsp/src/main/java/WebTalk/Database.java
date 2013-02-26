package WebTalk;

import com.sun.corba.se.pept.transport.ConnectionCache;

import java.sql.*;
/**
 * User: alex
 * Date: 21.02.13
 * Time: 1:11
 */
public class Database {
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String JDBC_URL = "jdbc:h2:tcp://localhost/test";
    public static final String JDBC_USER = "sa";
    public static final String JDBC_PASSWORD = "";

    private Connection connection;
    Statement st;

    Database() {
        connection = null;
        st = null;
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

    public boolean executeSQL(String sql){
        try {
            st = connection.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public ResultSet executeQuery(String query){
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            return null;
        }
        return rs;
    }


    protected void finalize () throws SQLException{
        if (connection != null) connection.close();
        if (st != null) st.close();
    }

    public Connection getConnection() {return connection;}
}