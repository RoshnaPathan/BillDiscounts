package com.retail.bill.discounts.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.retail.bill.discounts.enums.UserType;

/**
 * @author RPathan
 *
 */
@Component
public class User {
	private UserType userType;
	private Date customerSince;

	public User() {

	}

	public User(UserType userType, Date customerSince) {
		super();
		this.userType = userType;
		this.customerSince = customerSince;
	}

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the customerSince
	 */
	public Date getCustomerSince() {
		return customerSince;
	}

	/**
	 * @param customerSince
	 *            the customerSince to set
	 */
	public void setCustomerSince(Date customerSince) {
		this.customerSince = customerSince;
	}

}
