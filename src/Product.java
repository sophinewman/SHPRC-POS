/**
 * SHPRC-POS
 * Product.java
 * Stores information about a product.
 * 
 * @author Sophi Newman
 * @version 0.1 2/4/13
 */

public class Product {

	/* The unique ID number associated with the given product */
	private int productID;
	
	/* The retail price for the given product */
	private int price;
	
	/* The name of the given product */
	private String name;
	
	/* The ID number of the category to which the product belongs */
	private int categoryID;
	
	
	
	/**
	 * @param productID the productID of the product to be created
	 * @param price the cost of the product in cents
	 * @param name
	 * @param categoryID
	 */
	public Product(int productID, int price, String name, int categoryID) {
		this.productID = productID;
		this.price = price;
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
	
	public String toString() {
		return "Name: " + name + " Category ID: " + categoryID + " Price: "+ price;
	}

}
