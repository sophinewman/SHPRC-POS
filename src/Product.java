/**
 * 
 */

/**
 * @author sophi
 *
 */
public class Product {

	private int productID;
	private int price;
	private int cost;
	private String name;
	private int categoryID;
	
	
	/**
	 * 
	 */
	public Product(int productID, int price, int cost, String name, int categoryID) {
		this.productID = productID;
		this.price = price;
		this.cost = cost;
		this.name = name;
		this.categoryID = categoryID;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	
	
	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	
	
	/**
	 * @return the productID
	 */
	public int getProductID() {
		return productID;
	}
	
	
	/**
	 * @return the categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
