package connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//stores the information used to connect to the database
public class ConnectManagerMySql {
	public enum DB {
		PROD, ACCT;
	}
	
	private static final String DB_URL_PROD 
		= "jdbc:mysql:///ProductsDb";//in mysql
	private static final String DB_URL_ACCT 
		= "jdbc:mysql:///AccountsDb";//in mysql
	//user name
	private static final String USERNAME = "root";//mysql user name
	//password
	private static final String PASSWORD = "";//mysql password
	public static Connection getConnection(DB db) throws SQLException {	
		Connection conn = null;
		switch(db) {
			case PROD:
				conn = DriverManager.getConnection(DB_URL_PROD, USERNAME,
						PASSWORD);
				break;
			case ACCT:
				conn = DriverManager.getConnection(DB_URL_ACCT, USERNAME,
						PASSWORD);
				break;
			default:
				//do nothing
		}
		
		System.out.println("Got connection...");
		return conn;
	}
	
}
