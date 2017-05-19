package launch;

import business.DbClassCatalog;

public class Main {

	public static void main(String[] args) {
		DbClassCatalog dbclass = new DbClassCatalog();
		//try {
			System.out.println(dbclass.getAllCatalogs());
			int newId = dbclass.insertNewCatalog("TestCat");
			System.out.println("New catalog id for TestCat is " + newId); 
			int numRowsDeleted = dbclass.deleteCatalog(newId);
			System.out.println("Num rows deleted = " + numRowsDeleted);
			
		//}

	}
	
	

}
