import java.sql.SQLException;


public class RunnableTester {


	/**
	 * Runs some preliminary tests to verify that database intialization is
	 * working successfully and that the Purchase and Client classes are
	 * behaving as expected.
	 * @param args not used.
	 */
	public static void main(String[] args) {
		RuntimeDatabase rDB = null;
		// TODO Auto-generated method stub
		try {
			rDB = new RuntimeDatabase();
		}
		catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		if (!rDB.initRuntimeDatabase()) {
			System.err.println("Database initialization failed.");
			System.exit(1);
		}

		testPurchaseAndClient(rDB);

		try {
			rDB.closeDatabase();
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void testPurchaseAndClient(RuntimeDatabase rDB) {
		System.out.println(rDB.getPregnancyTestProduct());

		boolean print = rDB.qualifiesForPregnancyTestSubsidy(1000);
		System.out.println("Frosh qualifies for Pregnancy Test Subsidy: " + print);
		
		Client sophi = rDB.lookupClient(5573646);
		System.out.println("Sophi: " + sophi);
		
		Purchase purchase = new Purchase(rDB);
		Product condom = rDB.getProduct(100);
		purchase.addProduct(condom, 15);
		int total = purchase.tallyPurchaseTotal();
		System.out.println("total for 15 condoms before client set: " + total);
		purchase.addProduct(condom, 10);
		purchase.setCurrentClient(5573646, sophi.getAffiliation());
		total = purchase.tallyPurchaseTotal();
		System.out.println("total for 30 condoms after client set: " + total);
		
	}

}
