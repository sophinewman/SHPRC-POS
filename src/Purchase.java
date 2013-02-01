import java.util.HashMap;



public class Purchase {
	
	/* indices for the totals array */
	private static final int SUBTOTAL = 0, CREDIT = 1, PT_SUBSIDY = 2, TOTAL = 3;
	
	/* total stores the subtotal, credit used, preg. test subsidy, and total of an order */
	private int[] totals = {0,0,0,0};

	/* currentClient stores information about the purchaser*/
	private Client currentClient;


	/* products stores the products and their quantities in a given purchase */
	private HashMap<Product, Integer> products; 

	/* allows for lookup of global/backend data */
	private RuntimeDatabase rDB;


	//New Purchase will be constructed at launch or immediately following submission of last
	public Purchase (RuntimeDatabase rDB) {
		this.rDB = rDB;

	}


	/**
	 * @param currentSUID the SUID to set
	 * @param affiliationID the class or community affiliation the client belongs to
	 * TODO allow for those with certain combinations to opt out of SUID
	 */
	/* Method: setCurrentClient
	 * ------------------------
	 * Takes a Stanford University IDentification (7-digit number, e.g., 5555555) and
	 * an affiliation identification number (e.g., "frosh," "grad," "other") and uses
	 * these parameters to look up the client in the client database. If the client is
	 * found, a Client object is created. If not, a new Client object is made based on
	 * a set of parameters determined by administrative settings.
	 */
	public void setCurrentClient(int suid, int affiliationID) {
		currentClient = rDB.lookupClient(suid);
		if (currentClient == null) {
			int creditAvailable = rDB.getCredit(affiliationID);
			boolean qualifiesForPregnancyTest = rDB.qualifiesForPregnancyTestSubsidy(affiliationID);
			currentClient = 
				new Client(suid, affiliationID, creditAvailable, qualifiesForPregnancyTest, false);
		}
	}


	/**
	 *  @param product the product to be added to a purchase
	 *  @param qty the quantity of the product to be added to a purchase
	 */
	/*  Method: addProduct
	 * -------------------
	 *  Adds a product to the purchase's products and updates the total.
	 */
	public void addProduct(Product product, int qty) {
		totals[SUBTOTAL] -= getCurrentProductCost(product);
		products.put(product, qty);
		totals[SUBTOTAL] += product.getPrice() * qty;
	}

	
	/**
	 * @param product the product to be removed from a purchase
	 */
	/* Method: removeProduct
	 * ---------------------
	 * Removes an product from the purchase's products, calculating and
	 * subtracting its cost contribution to the total.
	 */
	public void removeProduct(Product product) {
		products.remove(product);
		totals[SUBTOTAL] -= getCurrentProductCost(product);
	}
	

	/**
	 * @return the total cost of a purchase after credits and subsidies
	 */
	/* Method: tallyPurchaseTotal
	 * --------------------------
	 * Calculates how much, if any, credit can be applied to this purchase,
	 * and sets the credit index in totals. Determines whether a pregnancy
	 * test is in this purchase and if the client qualifies for a subsidy;
	 * if both are true, the subsidy is set in the subsidy index in totals.
	 * The total index is totals is set to the sum of the subtotal, the credit,
	 * and the pregnancy test subsidy. This total is also returned to the caller.
	 */
	public int tallyPurchaseTotal() {
		totals[CREDIT] = calculateCredit();
		totals[PT_SUBSIDY] = applyPregnancyTestSubsidy();
		int total = totals[SUBTOTAL] + totals[CREDIT] + totals[PT_SUBSIDY];
		totals[TOTAL] = total;
		return total;
	}

	
	/* Method: applyPregnancyTestSubsidy
	 * ---------------------------------
	 * Tests whether the order contains a pregnancy test. if it does AND
	 * the client qualifies for the PT subsidy, the subsidy is applied.
	 * Otherwise, the line stays at 0.
	 */
	private int applyPregnancyTestSubsidy() {
		Product pregnancyTest = rDB.getPregnancyTestProduct();
		if (products.containsKey(pregnancyTest) && currentClient.pregnancyTestAvailable()) {
			return -1 * pregnancyTest.getPrice();
		}
		return 0;
	}
	
	
	/* Method: calculateCredit
	 * -----------------------
	 * Tests to see whether the subtotal is less than the amount of available credit.
	 * If so, the applied credit is set to the merchandise total. Otherwise, the credit 
	 * is set to the available credit.
	 */
	private int calculateCredit() {
		int availableCredit = currentClient.getCredit();
		if (totals[SUBTOTAL] < availableCredit) {
			return -1 * totals[SUBTOTAL];
		}
		return availableCredit;
	}
	

	/**
	 * @param product the product to be examined
	 * @return the cost in cents that this product at its quantity contributes to the total.
	 */
	/* Method: getCurrentProductCost
	 * ----------------------------- 
	 * Calculates the total value in cents that a product is contributing to
	 * an order.
	 */
	private int getCurrentProductCost(Product product) {
		if (products.get(product) != null) {
			return product.getPrice() * products.get(product); // price * qty
		}
		return 0;
	}
	
}
