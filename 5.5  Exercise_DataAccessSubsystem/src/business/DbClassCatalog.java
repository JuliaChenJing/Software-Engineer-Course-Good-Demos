package business;

import java.sql.ResultSet;

import middleware.exceptions.DatabaseException;
import middleware.externalinterfaces.DbClass;
import java.util.*;

public class DbClassCatalog implements DbClass {
	enum Type {READ_ALL, INSERT, DELETE};
	
	public List<Catalog> getAllCatalogs() {
		//implement
		return new ArrayList<>();
	}
	
	public int insertNewCatalog(String catalogName) {
		//implement
		return 55;  //new catalogid
	}
	
	public int deleteCatalog(int id) {
		//implement
		return -1;  //num rows updated
	}
	
	///interface implementations
	@Override
	public void populateEntity(ResultSet resultSet) throws DatabaseException {
		//implement	
	}

	@Override
	public String getDbUrl() {
		// implement
		return null;
	}

	@Override
	public String getQuery() {
		// implement
		return null;
	}

	@Override
	public Object[] getQueryParams() {
		// implement
		return null;
	}

	@Override
	public int[] getParamTypes() {
		// implement
		return null;
	}

}
