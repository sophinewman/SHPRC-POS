import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RuntimeDatabase {

	private Connection connection;

	public RuntimeDatabase() throws ClassNotFoundException {
		// load up the JDBC Driver with the class loader
		Class.forName("org.sqlite.JDBC");
		connection = null;
		try {		
			connection = DriverManager.getConnection("jdbc:sqlite:/Users/sophi/Documents/Workspace/SHPRC-POS/toy.db");
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}

	public void closeDatabase() throws SQLException {
		try
		{
			if (connection != null)
				connection.close();
		}
		catch(SQLException e)
		{
			System.err.println(e);
		}
	}
	public Client lookupClient(int SUID) {
		return null;
	}

	public int[] lookupAffiliationCredits(int affiliationID) {
		return null;
	}

	public Product getPregnancyTestProduct() {
		return null;
	}

	public int getPregnancyTestSubsidy() {
		return 0;
	}
}
