package net.maesierra.shoppingBasket;

import java.math.BigDecimal;

/**
 * Inner class to hold  the items in a basket and its extra information (price and quantity) 
 *
 */
public class BasketItem {
	private final Product product;
	private int quantity;
	private BigDecimal price;
	
	
	/**
	 * @param product product data
	 * @param quantity quantity 
	 */
	public BasketItem(final Product product, final int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Increases the quantity
	 * @param increment
	 */
	public void addQuantity(int increment) {
		if (increment < 0) {
			throw new IllegalArgumentException("Increment must be positive");
		}
		this.quantity += increment;
	}
	
	/**
	 * Decreases the quantity
	 * @param decrement
	 */
	public void decreaseQuantity(final int decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException("Decrement must be positive");
		}
		this.quantity = Math.max(0, this.quantity - Math.abs(decrement)); //Make sure it never goes bellow zero
		
		
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}



	


}