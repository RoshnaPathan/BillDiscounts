package com.retail.bill.discounts.controller;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.bill.discounts.constants.Constants;
import com.retail.bill.discounts.model.Bill;
import com.retail.bill.discounts.service.DiscountsService;

@RestController
@RequestMapping(Constants.VERSION_ONE)
public class DiscountsCotroller {

	private final DiscountsService discountsService;

	@Autowired
	public DiscountsCotroller(DiscountsService discountsService) {
		this.discountsService = discountsService;
	}

	/**
	 * @return
	 */
	@RequestMapping(Constants.HEALTH_ENDPOINT)
	public String handShake() {
		return Constants.HEALTH_OK;
	}

	/**
	 * 
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = Constants.BILL_DISCOUNTS_ENDPOINT, consumes = Constants.CONTENT_YPE, produces = Constants.CONTENT_YPE)
	public ResponseEntity<Object> calculateNetPayableAmount(@RequestBody(required = false) Bill bill) throws Exception {
		this.validateBill(bill);
		BigDecimal netPayableAmount = discountsService.calculateNetPayableAmount(bill);
		return new ResponseEntity<Object>(netPayableAmount, HttpStatus.OK);
	}

	/**
	 * @param bill
	 */
	private void validateBill(Bill bill) {
		if (Objects.isNull(bill)) {
			throw new IllegalArgumentException("bill is required");
		}
		this.validateUserDetails(bill);
		this.validateProductDetails(bill);
	}

	/**
	 * @param bill
	 */
	private void validateUserDetails(Bill bill) {
		if (Objects.isNull(bill.getUser())) {
			throw new IllegalArgumentException("user is required");
		}
		if (bill.getUser().getUserType() == null) {
			throw new IllegalArgumentException("userType is required");
		}
		if (bill.getUser().getCustomerSince() == null) {
			throw new IllegalArgumentException("customerSince is required");
		}
	}

	/**
	 * @param bill
	 */
	private void validateProductDetails(Bill bill) {
		if (CollectionUtils.isEmpty(bill.getProducts())) {
			throw new IllegalArgumentException("customerSince is required");
		} else {
			bill.getProducts().forEach(product -> {
				if (product.getProductType() == null) {
					throw new IllegalArgumentException("productType is required");
				}
				if (product.getProductCost() == null) {
					throw new IllegalArgumentException("productCost is required");
				}
			});
		}
		if (bill.getProducts() == null) {
			throw new IllegalArgumentException("product is required");
		}
	}
}