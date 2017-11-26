package sql_injection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import connect.ConnectManagerMySql;


public class SelectDemoApp {
	static final String userInput = " ' or '1'='1";
	static final String userInput_2 = "Jim";
	
	public static void main(String[] args) {
		withoutPrepared();
		usingPrepared();
	}
	
	private static void withoutPrepared() {
		System.out.println("in  the method withoutPrepared()");
        String query = "SELECT * FROM Customer WHERE fname = '%s'";
        Connection conn = null;
        try {
            conn = ConnectManagerMySql.getConnection(ConnectManagerMySql.DB.ACCT);
            Statement stmt = conn.createStatement();
            
            query = String.format(query, userInput);//for the safety reason
            
            System.out.println("1 "+query);
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\n2 Result from Database:");
            while(rs.next()) {
                System.out.println("\nfirst name: "+rs.getString("fname"));
                System.out.println("last name: "+rs.getString("lname"));
                System.out.println("ssn: "+rs.getString("ssn"));
                
            }
            System.out.println();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }   
    }
	private static void usingPrepared() {
		System.out.println("in  the method usingPrepared()");
		Connection con = null;
		String query = "SELECT ssn FROM customer WHERE fname = ?";
		try {
			//get connection
			con = ConnectManagerMySql.getConnection(ConnectManagerMySql.DB.ACCT);
			//prepare statement
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, userInput_2);//userInput_2 = "Jim";
			System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery();
			 System.out.println("3 "+query);//printout query
			System.out.println("\nResult from Database:\n");
			while(rs.next()) {
				System.out.println(rs.getString("ssn"));////515282426
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
