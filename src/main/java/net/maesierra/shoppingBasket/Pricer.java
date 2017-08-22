package net.maesierra.shoppingBasket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to price a product. Really simple implementation with a map
 * @author María-Eugenia Sierra
 *
 */
public class Pricer {	

	/**
	 * Exception thrown when the pricer doesn't contains price information for the product
	 *
	 */
	public class NoPriceException extends Exception {
		
		private NoPriceException(final Product product) {
			super("No price information for product " + (product != null ? product.getName() : "null"));
		}
	}
	
	private Map<Product, BigDecimal> prices = new HashMap<>();

	/**
	 * Prices a product
	 * @param product 
	 * @param quantity
	 * @return price 
	 * @throws NoPriceException if there are no price set for the product
	 */
	public BigDecimal getPrice(final Product product, int quantity) throws NoPriceException {
		if (product == null || !prices.containsKey(product)) {
			throw new NoPriceException(product);
		}
		if (quantity < 1) {
			throw new IllegalArgumentException("Quantity must be at least 1");
		}
		return prices.get(product).multiply(new BigDecimal(quantity));
		
	}

	/**
	 * Adds a price for a product (or updates the existing one) 
	 * @param product
	 * @param unitPrice
	 */
	public Pricer addPrice(final Product product, final BigDecimal unitPrice) {
		this.prices.put(product, unitPrice);
		return this;
		
	}
}
