package launch;

import java.util.List;

import business.Address;
import business.DbClassAddress;
import middleware.exceptions.DatabaseException;

public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("1   doInsert--------------------");
			doInsert();
			System.out.println("2   doIUpdate--------------------");
			doUpdate();
			System.out.println("3  doDelete--------------------");
			doDelete();
			System.out.println("4  doRead--------------------");
			doRead();
			
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		

	}
	private static void doRead() throws DatabaseException {
		DbClassAddress dbclass = new DbClassAddress();
		List<Address> addresses = dbclass.getCustAddresses("1");
		addresses.forEach(addr -> System.out.println(addr));
	}
	private static void doInsert() throws DatabaseException {
		DbClassAddress dbclass = new DbClassAddress();
		int result = dbclass.insertNewAddress("1", "3 Fourth Day St.", "Thompson", "IA", "54552");
		System.out.println("Address id for new address: " + result);
		
	}
	
	private static void doUpdate() throws DatabaseException {
		DbClassAddress dbclass = new DbClassAddress();
		int result = dbclass.updateFairfieldAddresses("11221");
		System.out.println("Num rows affected: " + result);
		
	}
	
	private static void doDelete() throws DatabaseException {
		DbClassAddress dbclass = new DbClassAddress();
		int result = dbclass.deleteFairfieldAddresses("54551");
		System.out.println("Num rows affected: " + result);		
	}
	

}
