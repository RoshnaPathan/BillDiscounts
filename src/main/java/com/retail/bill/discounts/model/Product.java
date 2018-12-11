package com.retail.bill.discounts.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.retail.bill.discounts.enums.ProductType;

/**
 * @author RPathan
 *
 */
@Component
public class Product {

	private ProductType productType;
	private BigDecimal productCost;

	public Product() {

	}

	public Product(ProductType productType, BigDecimal productCost) {
		super();
		this.productType = productType;
		this.productCost = productCost;
	}

	/**
	 * @return the productType
	 */
	public ProductType getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	/**
	 * @return the productCost
	 */
	public BigDecimal getProductCost() {
		return productCost;
	}

	/**
	 * @param productCost
	 *            the productCost to set
	 */
	public void setProductCost(BigDecimal productCost) {
		this.productCost = productCost;
	}

}
