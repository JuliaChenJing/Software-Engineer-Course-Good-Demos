package reading_data;

import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		ReadCustomer rc = new ReadCustomer();
		System.out.println("Looking up Jim");
		try {
			Customer cust = rc.getCustomer("Jim");
			System.out.println(cust);
		} catch(SQLException e) {
			for(Throwable t : e) {
				System.out.println("* " + t.getMessage());
			}
		}
		System.out.println("Listing all Customers");
		try {
			List<Customer> custs = rc.getCustomerList();
			System.out.println(custs);
		} catch(SQLException e) {
			for(Throwable t : e) {
				System.out.println("* " + t.getMessage());
			}
		}
	}

}
