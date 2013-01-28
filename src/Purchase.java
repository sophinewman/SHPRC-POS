import java.util.HashMap;



public class Purchase {
	
	private static final int SUBTOTAL = 0, CREDIT = 1, PT_SUBSIDY = 2, TOTAL = 3;
	/* total stores the subtotal, credit used, preg. test subsidy, and total of an order */
	private int[] totals = {0,0,0,0};
	
	/* currentSUID stores the SUID of whose purchase is being tallied */
	private Client currentClient;
	
	 
	/* boolean that indicates whether a pregnancy test subsidy has been used in purchase */
	//private boolean subsidyUsed;
	//Not sure I need this
	
	
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
	public void setCurrentClient(int suid, int affiliationID) {
		currentClient = rDB.lookupClient(suid);
		if (currentClient == null) {
			int[] credits = rDB.lookupAffiliationCredits(affiliationID);
			currentClient = new Client(suid, credits[0], credits[1]);
		}
	}
	
	
	/**
	 *  @param product the product to be added to a purchase
	 *  @param qty the quantity of the product to be added to a purchase
	 *  Adds a product to the purchase's products and updates the total.
	 */
	public void addProduct(Product product, int qty) {
		int cost = getCurrentProductCost(product);
		totals[SUBTOTAL] -= cost;
		products.put(product, qty);
		totals[SUBTOTAL] += product.getPrice() * qty;
	}
	
	/**
	 * @param product the product to be removed from a purchase
	 * Removes an product from the purchase's products and updates the total.
	 */
	public void removeProduct(Product product) {
		products.remove(product);
		totals[SUBTOTAL] -= getCurrentProductCost(product);
	}
	
	
	/**
	 * @param product the product to be examined
	 * @return the cost in cents that this product at its quantity contributes to the total.
	 */
	private int getCurrentProductCost(Product product) {
		if (products.get(product) != null) {
			return product.getPrice() * products.get(product);
		}
		return 0;
	}
	
	/**
	 * 
	 * @return the total cost of a purchase after credits and subsidies
	 * TODO: this is currently incorrect. needs to have some "apply subsidies" rule.
	 */
	public int getPurchaseTotal() {
		int total = 0;
		for (int i = 0; i < TOTAL; i++) {
			total += totals[i];
		}
		return total;
	}

}
