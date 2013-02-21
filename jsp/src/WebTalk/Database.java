package WebTalk;

import java.sql.*;
/**
 * User: alex
 * Date: 21.02.13
 * Time: 1:11
 */
public class Database {
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String JDBC_USER = "sa";
    public static final String JDBC_PASSWORD = "";

    private Connection connection;
    Statement st;

    Database() {
        connection = null;
        st = null;
    }

    public void createConnection() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(JDBC_URL,JDBC_USER, JDBC_PASSWORD);
    }

    public void executeSQL(String sql) throws SQLException{
        st = connection.createStatement();
        st.execute(sql);
    }

    public ResultSet executeQuery(String query) throws SQLException{
        st = connection.createStatement();
        return st.executeQuery(query);
    }


    protected void finalize () throws SQLException{
        if (connection != null) connection.close();
        if (st != null) st.close();
    }
	/*public static void main(String[] args) {
		try {

			Class.forName("org.h2.Driver");
	        Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test", "sa", "");
	        // add application code here
	        conn.close();

			Class.forName("org.h2.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:h2:test",
			"sa", "");
			Statement st = null;
			st = conn.createStatement();
			st.execute("INSERT INTO TEST VALUES(default,'HELLO')");
			st.execute("INSERT INTO TEST(NAME) VALUES('JOHN')");
			String name1 = "Jack";
			String q = "insert into TEST(name) values(?)";
			PreparedStatement st1 = null;

			st1 = conn.prepareStatement(q);
			st1.setString(1, name1);
			st1.execute();

			ResultSet result;
			result = st.executeQuery("SELECT * FROM TEST");
			while (result.next()) {
			String name = result.getString("NAME");
			System.out.println(result.getString("ID")+" "+name);
			}
			} catch (Exception e) {
			e.printStackTrace();
			}
	}*/
}