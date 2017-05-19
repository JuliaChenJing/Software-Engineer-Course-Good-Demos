package business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.exceptions.DatabaseException;
import middleware.externalinterfaces.DbClass;

public class DbClassAddress implements DbClass {
	enum QueryType {READ, INSERT, UPDATE, DELETE};
	QueryType queryType;
	List<Address> addresses = new ArrayList<>(); 
	String readQuery = "SELECT street, city, state, zip FROM altaddress WHERE custid=?";
	String insertQuery = "INSERT into altaddress " +
    		"(custid,street,city,state,zip, isship, isbill) " +
    		"VALUES(?, ?, ?, ?, ?, ?, ?)";
	String updateQuery = "UPDATE ALTADDRESS SET zip = ? WHERE street = 'New One'";
	String deleteQuery = "DELETE from ALTADDRESS where zip = ?";
	
	Object[] readParams = null;
	int[] readParamTypes = null;
	Object[] insertParams = null;
	int[] insertParamTypes = null;
	Object[] updateParams = null;
	int[] updateParamTypes = null;
	Object[] deleteParams = null;
	int[] deleteParamTypes = null;
	@SuppressWarnings("unused")
	private static final String DB_URL_PROD = "jdbc:mysql:///ProductsDb";
	private static final String DB_URL_ACCT = "jdbc:mysql:///AccountsDb";

	public List<Address> getCustAddresses(String custId) throws DatabaseException {
		queryType = QueryType.READ;
		readParams = new Object[]{custId};
		readParamTypes = new int[]{Types.VARCHAR};
		DataAccessSubsystemFacade dss = new DataAccessSubsystemFacade();
		dss.establishConnection(this);
		dss.read();
		dss.releaseConnection();
		return addresses;
	}
	public int insertNewAddress(String custId, String str, String city, String st, String zip) 
			throws DatabaseException {
		queryType = QueryType.INSERT;
		insertParams = new Object[] {custId, str, city, st, zip, Boolean.FALSE, Boolean.FALSE};
		insertParamTypes = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.BOOLEAN, Types.BOOLEAN};
		DataAccessSubsystemFacade dss = new DataAccessSubsystemFacade();
		int autoVal = dss.insertWithinTransaction(this);
		return autoVal;
	}
	
	public int updateFairfieldAddresses(String newStreet) throws DatabaseException {
		queryType = QueryType.UPDATE;
		updateParams = new Object[]{newStreet};
		updateParamTypes = new int[]{Types.VARCHAR};
		DataAccessSubsystemFacade dss = new DataAccessSubsystemFacade();
		int numrows = dss.updateWithinTransaction(this);
		return numrows;
	}
	
	public int deleteFairfieldAddresses(String zip) throws DatabaseException {
		queryType = QueryType.DELETE;
		deleteParams = new Object[]{zip};
		deleteParamTypes = new int[]{Types.VARCHAR};
		DataAccessSubsystemFacade dss = new DataAccessSubsystemFacade();
		int numrows = dss.deleteWithinTransaction(this);
		return numrows;
	}

	@Override
	public void populateEntity(ResultSet rs) throws DatabaseException {
		addresses = new ArrayList<>();
		try {
			String street = null;
			String city = null;
			String state = null;
			String zip = null;
			while(rs.next()) {
				street = rs.getString("street").trim();
				city = rs.getString("city").trim();
				state = rs.getString("state").trim();
				zip = rs.getString("zip").trim();
				addresses.add(new Address(street, city, state, zip));
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
		
	}

	@Override
	public String getDbUrl() {
		return DB_URL_ACCT;
	}

	@Override
	public String getQuery() {
		switch(queryType) {
			case READ :
				return readQuery;
			case INSERT :
				return insertQuery;
			case UPDATE :
				return updateQuery;
			case DELETE :
				return deleteQuery;
			default :
				return null;
					
		}
	}

	@Override
	public Object[] getQueryParams() {
		switch(queryType) {
			case READ :
				return readParams;
			case INSERT :
				return insertParams;
			case UPDATE :
				return updateParams;
			case DELETE :
				return deleteParams;
			default :
				return null;
				
		}
	}

	@Override
	public int[] getParamTypes() {
		switch(queryType) {
		case READ :
			return readParamTypes;
		case INSERT :
			return insertParamTypes;
		case UPDATE :
			return updateParamTypes;
		case DELETE :
			return deleteParamTypes;
		default :
			return null;
			
	}
}
	
}
