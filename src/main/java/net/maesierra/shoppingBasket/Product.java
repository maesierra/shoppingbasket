package net.maesierra.shoppingBasket;

/**
 * Class to represent an product that will be purchased
 *
 */
public class Product {
	
	private final int id;
	private final String name;

	/**
	 * @param id product id
	 * @param name product name
	 */
	public Product(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
