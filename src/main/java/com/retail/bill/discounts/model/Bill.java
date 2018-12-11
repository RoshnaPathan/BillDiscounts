package com.retail.bill.discounts.model;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author RPathan
 *
 */
@Component
public class Bill {

	private User user;
	private List<Product> products;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
