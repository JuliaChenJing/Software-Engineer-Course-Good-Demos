package reading_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//connect manager for JDBC
public class ConnectManager {
	
	private static final String DB_URL 
		= "jdbc:derby://localhost:1527/MPP_DB;create=true";
	private static final String USERNAME = "app";
	private static final String PASSWORD = "app";
	public static Connection getConnection() throws SQLException {	
		Connection conn = DriverManager.getConnection(DB_URL, USERNAME,
				PASSWORD);
		System.out.println("Got connection...");
		return conn;
	}
	
}
