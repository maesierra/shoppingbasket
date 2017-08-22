package net.maesierra.shoppingBasket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

/**
 * Test to check the basic functionality
 * 
 *  Add a an item to the basket
 *  Add the same item to a basket
 *  Get the contents of the basket
 *  Get to total cost for the basket (an item should have a unit cost)
 *  
 * @author María-Eugenia Sierra
 *
 */
public class ShoppingBasketTest {

	private Basket basket;
	private Product product1;
	private Product product2;
	private BigDecimal price1;
	private BigDecimal price1MultipledBy2;
	private BigDecimal price2;
	private Pricer pricer;
	
	@Before
	public void setUp() {
		
		this.product1 = new Product(1, "Product#1");
		this.product2 = new Product(2, "Product#2");
		this.price1 = new BigDecimal("5.37");
		this.price1MultipledBy2 = new BigDecimal("5.37").multiply(new BigDecimal(2));
		this.price2 = new BigDecimal("9.37");
		this.pricer = new Pricer().addPrice(product1, price1)
				   				  .addPrice(product2, price2);
		this.basket = new Basket(this.pricer);
	}
	
	@Test
	public void testIsEmpty() {
		assertThat(this.basket.isEmpty(), is(true));
	}
	
	@Test
	public void testEmptyContents() throws Exception {
		//An empty basket must return an empty but valid stream
		assertThat(this.basket.getContents().count(), is(0L));
	}
	
	@Test
	public void testAddItem()  throws Exception {		
		this.basket.add(product1);
		assertThat(this.basket.getSize(), is(1));
		assertThat(this.basket.isEmpty(), is(false));
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
						allOf(hasProperty("product", sameInstance(product1)), hasProperty("quantity", is(1)))
			    )
		);
		
	}
	
	@Test
	public void testAddItemWithQuantity()  throws Exception {		
		this.basket.add(product1, 4);
		assertThat(this.basket.getSize(), is(1));
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
						allOf(hasProperty("product", sameInstance(product1)), hasProperty("quantity", is(4)))
			    )
		);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddItemNegativeQuantity()  throws Exception {		
		this.basket.add(product1, -1);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddItemZeroQuantity()  throws Exception {		
		this.basket.add(product1, 0);
		
	}
	
	@Test
	public void testAddNullItem() {
		this.basket.add(null);
		assertThat(this.basket.isEmpty(), is(true)); //Still empty and no exception
	}
	
	@Test
	public void testSameAddItem()  throws Exception  {		
		this.basket.add(product1);
		this.basket.add(product1);
		assertThat(this.basket.getSize(), is(1));
		//Quantity must be 2
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
						allOf(hasProperty("product", sameInstance(product1)), hasProperty("quantity", is(2)))
				)
		);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPrices() throws Exception  {		
		this.basket.add(product1);
		this.basket.add(product2);
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
					allOf(hasProperty("product", sameInstance(product1)), hasProperty("price", is(price1))),
					allOf(hasProperty("product", sameInstance(product2)), hasProperty("price", is(price2)))
			    )
		);
		
		this.basket.add(product1);
		
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
					allOf(hasProperty("product", sameInstance(product1)), hasProperty("price", is(price1MultipledBy2))),
					allOf(hasProperty("product", sameInstance(product2)), hasProperty("price", is(price2)))
			    )
		);

	}
	
	@Test
	public void removeItem() throws Exception {
		this.basket.add(product1);
		this.basket.add(product2);
		this.basket.remove(product1);
		assertThat(this.basket.getSize(), is(1));
		assertThat(this.basket.getContents().collect(Collectors.toList()), not(hasItem(hasProperty("product", is(product1))))); 
	}
	
	@Test
	public void removeLastItemInBasket() throws Exception {
		this.basket.add(product1);
		this.basket.remove(product1);
		assertThat(this.basket.isEmpty(), is(true));
		assertThat(this.basket.getContents().collect(Collectors.toList()), hasSize(0)); 
	}
	
	@Test
	public void removeItemNotInBasket() throws Exception {
		this.basket.add(product1);
		this.basket.remove(product2);
		assertThat(this.basket.getSize(), is(1)); 
	}
	
	@Test
	public void removeItemDescreasingQuantity() throws Exception {
		this.basket.add(product1, 3);
		this.basket.remove(product1);
		assertThat(this.basket.getSize(), is(1));
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
						allOf(hasProperty("product", sameInstance(product1)), hasProperty("quantity", is(2)))
			    )
		);
 
	}
	
	@Test
	public void removeItemWithQuantity() throws Exception {
		this.basket.add(product1, 3);
		this.basket.remove(product1, 2);
		assertThat(this.basket.getSize(), is(1));
		assertThat(
				this.basket.getContents().collect(Collectors.toList()), 
				contains(
						allOf(hasProperty("product", sameInstance(product1)), hasProperty("quantity", is(1)))
			    )
		);
 
	}

	
	
}
