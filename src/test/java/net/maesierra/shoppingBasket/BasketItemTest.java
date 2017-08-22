package net.maesierra.shoppingBasket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Before;
import org.junit.Test;

/**
 * tests for basket item. Focused on quantity handling
 *
 */
public class BasketItemTest {

	private Product product;
	
	@Before
	public void setUp() {
		this.product = new Product(1, "product#1");		
	}
	
	@Test
	public void testConstructorAndGet() {
		final BasketItem item = new BasketItem(product, 1);
		assertThat(item.getProduct(), sameInstance(product));
		assertThat(item.getQuantity(), is(1));
		assertThat(item.getPrice(), nullValue());
	}
	
	@Test
	public void testAddQuantity() {
		final BasketItem item = new BasketItem(product, 3);
		item.addQuantity(10);
		assertThat(item.getQuantity(), is(13));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNegativeQuantity() {
		final BasketItem item = new BasketItem(product, 3);
		item.addQuantity(-10);
		
	}

	@Test
	public void testDecreaseQuantity() {
		final BasketItem item = new BasketItem(product, 3);
		item.decreaseQuantity(2);
		assertThat(item.getQuantity(), is(1));	
	}
	
	@Test
	public void testDecreaseQuantityNotBellowZero() {
		final BasketItem item = new BasketItem(product, 3);
		item.decreaseQuantity(6);
		assertThat(item.getQuantity(), is(0));
		
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testDecreaseNegativeQuantity() {
		final BasketItem item = new BasketItem(product, 3);
		item.decreaseQuantity(-10);
		
	}
	
}
