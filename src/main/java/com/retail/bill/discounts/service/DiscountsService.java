package com.retail.bill.discounts.service;

import java.math.BigDecimal;

import com.retail.bill.discounts.model.Bill;

/**
 * @author RPathan
 *
 */
public interface DiscountsService {
    
	/**
	 * Calculates the net payable amount on the bill.
	 * 
	 * @param bill
	 * @return
	 */
	BigDecimal calculateNetPayableAmount(Bill bill);
}
