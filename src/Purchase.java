import java.util.HashMap;



public class Purchase {
	
	private static final int SUBTOTAL = 0, CREDIT = 1, PT_SUBSIDY = 2, TOTAL = 3;
	
	/* currentClient stores the client whose purchase is being tallied */
	private Client currentClient;
	
	/* products stores the products and their quantities in a given purchase */
	private HashMap<Product, Integer> products; 
	
	/* total stores the subtotal, credit used, preg. test subsidy, and total of an order */
	private int[] totals = {0,0,0,0};
	
	/* boolean that indicates whether pregnancy test subsidies should be applied */
	private boolean subsidyActive; // will be passed in to constructor by the caller
	
	public Purchase (Client client, boolean subsidyActive) {
		this.subsidyActive = subsidyActive;
		if (currentClient != null) {
			setCurrentClient(client);
		}
		if (this.subsidyActive) {
			totals[PT_SUBSIDY] = -400; //TODO replace magic # with db query!
			//select * from PregnancyTest - 0 when subsidy is toggled, price of pregnancy test when it's not.
			//THIS DOESN'T BELONG HERE!
		}
	}


	/**
	 * @param currentClient the currentClient to set
	 */
	public void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
		if (this.currentClient != null) { //TODO do I need this check?
			totals[CREDIT] = this.currentClient.getCredit();
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
	 */
	public int getPurchaseTotal() {
		int total = 0;
		for (int i = 0; i < TOTAL; i++) {
			total += totals[i];
		}
		return total;
	}

}
