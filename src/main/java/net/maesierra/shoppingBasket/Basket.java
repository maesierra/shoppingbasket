package net.maesierra.shoppingBasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.maesierra.shoppingBasket.Pricer.NoPriceException;

/**
 * Main class to represent the a shopping basket
 * @author María-Eugenia Sierra
 *
 */
public class Basket  {
	
	private List<BasketItem> items = new ArrayList<>();
	private Map<Product, BasketItem> productMap = new HashMap<>();
	private Pricer pricer;
	

	/**
	 * @param pricer pricer to be used
	 */
	public Basket(final Pricer pricer) {
		this.pricer = pricer;
	}


	/**
	 * Adds a product to the basket with quantity 1
	 * @param product
	 */
	public void add(final Product product) {
		this.add(product, 1);
	}
			
	
	/**
	 * Adds a product to the basket
	 * @param product product instance
	 * @param quantity product quantity
	 */
	public void add(final Product product, final int quantity) {
		if (product == null) {
			return; 			
		}
		if (quantity < 1) {
			throw new IllegalArgumentException("Invalid quantity");
		}
		//If the product isn't in the basket => create a basket item and add it to the map
		if (!productMap.containsKey(product)) {
			final BasketItem item = new BasketItem(product, quantity);
			this.items.add(item);
			this.productMap.put(product, item);
		} else { 
			//Update the quantity
			this.productMap.get(product).addQuantity(quantity);
		}
		
		
	}
	
	/**
	 * Updates the prices calling the pricer for all the items in the basket 
	 * @throws NoPriceException
	 */
	private void updatePrices() throws NoPriceException {
		for (BasketItem item : items) {
			item.setPrice(this.pricer.getPrice(item.getProduct(), item.getQuantity()));
		}
						
	}
	/**
	 * Method that returns an stream for the contents. 
	 * The main reason to return an stream instead of a collection is that
	 * an stream cannot be modified and can be used to iterate and processing. 
	 * Forcing to add products via the add methods
	 *  
	 * @return
	 * @throws NoPriceException 
	 */
	public Stream<BasketItem> getContents() throws NoPriceException {
		updatePrices();
		return this.items.stream();
	}

	/**
	 * @return hasSize
	 */
	public int getSize() {
		return this.items.size();
	}
	/**
	 * @return true if the basket is empty
	 */
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	
	/**
	 * Removes a unit from a product in the basket. If the quantity drops to zer, thne the item will be
	 * removed from the basket
	 * @param product
	 */
	public void remove(final Product product) {
		remove(product, 1);
	}

	/**
	 * Removes the given quantity from a product in the basket. If the quantity drops to zero, then the 
	 * item will removed from the basket
	 * @param product
	 * @param quantity
	 */
	public void remove(final Product product, int quantity) {
		if (!this.productMap.containsKey(product)) {
			return; //Nothing to do
		}
		final BasketItem item = this.productMap.get(product);
		item.decreaseQuantity(quantity);
		if (item.getQuantity() == 0) {
			this.items.remove(item);
		}
		
		
		
	}

	
}
