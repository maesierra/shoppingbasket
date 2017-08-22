package net.maesierra.shoppingBasket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import net.maesierra.shoppingBasket.Pricer.NoPriceException;

/**
 * Tests for the pricer class
 * @author María-Eugenia Sierra
 *
 */
public class PricerTest {
		
	private Pricer pricer;
	private Product product1;
	private Product product2;
	private BigDecimal price1;
	private BigDecimal price1MultipledBy5;
	private BigDecimal price2;
	
	@Before
	public void setUp() {
		this.pricer = new Pricer();
		this.product1 = new Product(1, "Product#1");
		this.product2 = new Product(2, "Product#2");
		this.price1 = new BigDecimal("5.37");
		this.price1MultipledBy5 = new BigDecimal("5.37").multiply(new BigDecimal(5));
		this.price2 = new BigDecimal("9.37");
	}
	
	@Test(expected = NoPriceException.class)
	public void testNoPrice() throws Exception {
		this.pricer.getPrice(product1, 1);
	}
	
	@Test(expected = NoPriceException.class)
	public void testNoPriceNullValue() throws Exception {
		this.pricer.getPrice(null, 1);
	}
	
	
	@Test
	public void testPrice() throws Exception {		
		this.pricer.addPrice(product1, price1);
		assertThat(this.pricer.getPrice(product1, 1), is(price1));
		assertThat(this.pricer.getPrice(product1, 5), is(price1MultipledBy5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNoPriceNegative() throws Exception {
		this.pricer.addPrice(product1, price1);
		this.pricer.getPrice(product1, -1);
	}
	
	
	@Test
	public void testPriceMultipelProducts() throws Exception {		
		this.pricer.addPrice(product1, price1)
				   .addPrice(product2, price2);
		assertThat(this.pricer.getPrice(product1, 1), is(price1));
		assertThat(this.pricer.getPrice(product2, 1), is(price2));

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPriceZeroQuantity() throws Exception {
		this.pricer.addPrice(product1, price1);
		this.pricer.getPrice(product1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPriceNegativeQuantity() throws Exception {
		this.pricer.addPrice(product1, price1);
		this.pricer.getPrice(product1, -1);
	}

	@Test
	public void testUpdatePrice() throws Exception {		
		this.pricer.addPrice(product1, price1);
		assertThat(this.pricer.getPrice(product1, 1), is(price1));
		this.pricer.addPrice(product1, price2);
		assertThat(this.pricer.getPrice(product1, 1), is(price2));
	}
	
}