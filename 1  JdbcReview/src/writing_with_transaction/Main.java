package writing_with_transaction;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		UpdateCustomer uc = new UpdateCustomer();
		Object[] updateInfo = prepareUpdateInfo();
		Customer cust = (Customer)updateInfo[0];
		UpdateData ud = (UpdateData)updateInfo[1];
		
		//Two steps to perform:
		//  1. Insert a new Customer record
		//  2. Update that record with new data
		//We want to do these within a transaction
		//- cust contains the new customer record
		//- ud contains the update info to be inserted after the record has been added
		try {
			int newCustId = uc.customerUpdates(cust,  ud);
			System.out.println("Updates successful. New customer id for " +
			   cust.getName() + " is " + newCustId);
		} catch(SQLException e) {
			for(Throwable t : e) {
				System.out.println("* " + t.getMessage());
			}
		}

	}
	private static Object[] prepareUpdateInfo() {
		UpdateData upData = new UpdateData("Thomas Redding", "Mountain View");
		Customer cust = new Customer("Thomas Redding","111 Hamilton Ave","Palo Alto", "94301");
		Object[] retval = {cust,upData};
		return retval;
	}
}
