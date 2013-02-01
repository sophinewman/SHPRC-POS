import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class RuntimeDatabase {

	/* The JDBC Connection that backs the class */
	private Connection connection;

	private HashMap<Integer, Integer> affiliationCreditMap;
	private HashMap<Integer, Boolean> affiliationPregnancyTestSubsidy;
	private HashMap<Integer, Product> productMap;
	private Product pregnancyTest;

	public RuntimeDatabase() throws ClassNotFoundException {
		//PUT THE CONSTRUCTOR IN A TRY-CATCH BLOCK! - first declare and then allocate in try catch
		// load the JDBC Driver with the class loader
		Class.forName("org.sqlite.JDBC");

	}

	public boolean initRuntimeDatabase() {
		try {		
			connection = 
				DriverManager.getConnection("jdbc:sqlite:/Users/sophi/Documents/Workspace/SHPRC-POS/SHPRC-POS.db");
			if (connection == null) {
				return false;
			}
			if (!initializeProductMap()) {
				return false;
			}
			if (!initializeAffiliationMaps()) {
				return false;
			}
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return true;
	}


	/**
	 * @throws SQLException
	 */
	/* Method: closeDatabase
	 * ---------------------
	 * Closes the Connection object that underpins the RuntimeDatabase class.
	 */
	public void closeDatabase() throws SQLException {
		// CALL THIS IN A TRY CATCH BLOCK
		try {
			if (connection != null)
				connection.close();
		}
		catch(SQLException e) {
			System.err.println(e);
		}
	}


	private boolean initializeProductMap() {
		productMap = new HashMap<Integer, Product>();
		try {
			Statement stmt = connection.createStatement();
			// Return all rows in the Product relation
			ResultSet rs = stmt.executeQuery(("SELECT * FROM Product"));
			
			// Read in the information about each row and store in Product object
			// Stores the flagged pregnancy test product in an instance variable
			while (rs.next()) {
				// Fetching row information
				int productID = rs.getInt("productID");
				String productName = rs.getString("productName");
				int price = rs.getInt("price");
				int categoryID = rs.getInt("CategoryID");
				// Storing in Product object
				Product product = new Product(productID, price, productName, categoryID);
				// Determining whether this is a pregnancy test
				boolean isPregnancyTest = rs.getBoolean("isPregnancyTest");
				if (isPregnancyTest) {
					pregnancyTest = product;
				}
				// Adds product to map of all products
				productMap.put(productID, product);
			}
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Ensures that the pregnancyTest and productMap objects have been initialized
		return (pregnancyTest != null && productMap != null);
	}


	/* Method: initializeAffiliationMaps
	 * ---------------------------------
	 * Reads in the Affiliation relation and creates two local data structures
	 * that will store information about what a given affiliaton qualifies for.
	 */
	private boolean initializeAffiliationMaps() {
		affiliationCreditMap = new HashMap<Integer, Integer>();
		affiliationPregnancyTestSubsidy = new HashMap<Integer, Boolean>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Affiliation");
			while (rs.next()) {
				int affiliationID = rs.getInt("affiliationID");
				int affiliationCredit = rs.getInt("affiliationCredit");
				boolean qualifiesForSubsidy = rs.getBoolean("qualifiesForSubsidy");
				affiliationCreditMap.put(affiliationID, affiliationCredit);
				affiliationPregnancyTestSubsidy.put(affiliationID, qualifiesForSubsidy);

			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (affiliationCreditMap != null && affiliationPregnancyTestSubsidy != null);

	}


	/**
	 * @param SUID
	 * @return a client object retrieved from the database, if it exists
	 */
	/* Method: lookupClient
	 * --------------------
	 * Tests to see if the specified client exists in the client database. If so,
	 * the corresponding client object is created and returned to the user. If a
	 * client is not in the database, lookupClient returns null.
	 */
	public Client lookupClient(int SUID) {
		try {
			// A PreparedStatement is used here to ensure that the SQL query is correctly formatted
			// and to allow for more easily human-readable variable insertion.
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Client WHERE SUID = ?");
			pstmt.setInt(0, SUID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int creditAvailable = rs.getInt("creditAvailable");
				boolean pregnancyTestUsed = rs.getBoolean("pregnancyTestUsed");
				int affiliationID = rs.getInt("affilitionID");
				boolean qualifiesForPregnancyTestSubsidy = qualifiesForPregnancyTestSubsidy(affiliationID);
				Client client = 
					new Client(SUID, affiliationID, creditAvailable, 
							pregnancyTestUsed, qualifiesForPregnancyTestSubsidy);
				return client;
			}
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}


	/**
	 * @param affiliationID
	 * @return the amount of credit a given affiliation receives
	 */
	public int getCredit(int affiliationID) {
		if (affiliationCreditMap.containsKey(affiliationID)) {
			return affiliationCreditMap.get(affiliationID);
		}
		return 0;
	}

	public Product getPregnancyTestProduct() {
		return pregnancyTest;
	}


	public boolean qualifiesForPregnancyTestSubsidy(int affiliationID) {
		if (affiliationPregnancyTestSubsidy.containsKey(affiliationID)) {
			return affiliationPregnancyTestSubsidy.get(affiliationID);
		}
		return false;
	}
}
