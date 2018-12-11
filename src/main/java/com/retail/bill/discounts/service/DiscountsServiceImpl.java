package com.retail.bill.discounts.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.retail.bill.discounts.enums.ProductType;
import com.retail.bill.discounts.enums.UserType;
import com.retail.bill.discounts.model.Bill;
import com.retail.bill.discounts.model.Product;
import com.retail.bill.discounts.model.User;

/**
 * @author RPathan
 *
 */
@Service
public class DiscountsServiceImpl implements DiscountsService {

	public long TWO_YEARS_DAYS = 730;// 2*365

	/**
	 * @see com.retail.bill.discounts.service.DiscountsService#calculateNetPayableAmount(com.retail.bill.discounts.model.Bill)
	 */
	@Override
	public BigDecimal calculateNetPayableAmount(Bill bill) {

		final User user = bill.getUser();
		BigDecimal userDiscount = getDiscountByUser(user);
		BigDecimal discountApplicableAmount = getProductDiscountAmount(bill.getProducts());
		if (userDiscount.compareTo(BigDecimal.ZERO) > 0) {
			discountApplicableAmount = discountApplicableAmount.subtract(userDiscount
					.divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP).multiply(discountApplicableAmount))
					.add(getProductNonDiscountAmount(bill.getProducts()));
		} else {
			discountApplicableAmount = discountApplicableAmount.add(getProductNonDiscountAmount(bill.getProducts()));
		}

		return discountApplicableAmount.subtract(discountApplicableAmount
				.divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(5)))
				.setScale(2, RoundingMode.HALF_UP);

	}

	/**
	 * @param products
	 * @return
	 */
	private BigDecimal getProductDiscountAmount(List<Product> products) {
		return products.stream().filter(Objects::nonNull)
				.filter(product -> product.getProductType().compareTo(ProductType.groceries) != 0)
				.map(Product::getProductCost).reduce(BigDecimal.ZERO, BigDecimal::add);

	}

	/**
	 * @param products
	 * @return
	 */
	private BigDecimal getProductNonDiscountAmount(List<Product> products) {
		return products.stream().filter(Objects::nonNull)
				.filter(product -> product.getProductType().compareTo(ProductType.groceries) == 0)
				.map(Product::getProductCost).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * @param user
	 * @return
	 */
	private BigDecimal getDiscountByUser(final User user) {
		if (user.getUserType().compareTo(UserType.employee) == 0) {
			return new BigDecimal(30);
		} else if (user.getUserType().compareTo(UserType.affiliate) == 0) {
			return new BigDecimal(10);
		} else {
			if (TWO_YEARS_DAYS <= getDifferenceDays(user.getCustomerSince(), new Date())) {
				return new BigDecimal(5);
			} else {
				return BigDecimal.ZERO;
			}
		}
	}

	/**
	 * @param startDate
	 * @param today
	 * @return
	 */
	private long getDifferenceDays(final Date startDate, final Date today) {
		long diff = today.getTime() - startDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
}