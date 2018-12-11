package com.retail.bill.discounts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.retail.bill.discounts.DiscountsApplication;
import com.retail.bill.discounts.constants.Constants;
import com.retail.bill.discounts.enums.ProductType;
import com.retail.bill.discounts.enums.UserType;
import com.retail.bill.discounts.model.Bill;
import com.retail.bill.discounts.model.Product;
import com.retail.bill.discounts.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = DiscountsApplication.class)
public class DiscountsCotrollerTest {

	private URI uri;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().add("Content-Type", "application/json");
			return execution.execute(request, body);
		}));
		try {
			uri = new URI(Constants.VERSION_ONE);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void healtCheck() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri + Constants.HEALTH_ENDPOINT,
				String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBillNotSpecified() throws Throwable {

		HttpEntity<Bill> entity = new HttpEntity<>(null);
		restTemplate.exchange(uri + Constants.BILL_DISCOUNTS_ENDPOINT, HttpMethod.POST, entity, String.class);
		assertFalse(throwException());
	}

	private boolean throwException() {
		throw new IllegalArgumentException();
	}

	@Test
	public void calculateNetPayableAmountForFashion() throws RestClientException, URISyntaxException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		User user = new User();
		try {
			user.setCustomerSince(dateFormat.parse("01/11/2016"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setUserType(UserType.employee);

		List<Product> products = new ArrayList<>();
		Product productFashion = new Product(ProductType.fashion, new BigDecimal(1450));
		Product productGrocery = new Product(ProductType.groceries, BigDecimal.ZERO);
		products.add(productFashion);
		products.add(productGrocery);

		Bill bill = new Bill();
		bill.setUser(user);
		bill.setProducts(products);

		HttpEntity<Bill> entity = new HttpEntity<>(bill);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri + Constants.BILL_DISCOUNTS_ENDPOINT,
				HttpMethod.POST, entity, String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("964.25", responseEntity.getBody());
	}

	@Test
	public void calculateNetPayableAmountForAffliateGroceries() throws RestClientException, URISyntaxException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		User user = new User();
		try {
			user.setCustomerSince(dateFormat.parse("01/11/2016"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setUserType(UserType.affiliate);

		List<Product> products = new ArrayList<>();
		Product productFashion = new Product(ProductType.fashion, new BigDecimal(1200));
		Product productGrocery = new Product(ProductType.groceries, new BigDecimal(1450));
		products.add(productFashion);
		products.add(productGrocery);

		Bill bill = new Bill();
		bill.setUser(user);
		bill.setProducts(products);

		HttpEntity<Bill> entity = new HttpEntity<>(bill);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri + Constants.BILL_DISCOUNTS_ENDPOINT,
				HttpMethod.POST, entity, String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("2403.50", responseEntity.getBody());
	}

	@Test
	public void calculateNetPayableAmountForGroceries() throws RestClientException, URISyntaxException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		User user = new User();
		try {
			user.setCustomerSince(dateFormat.parse("01/07/2016"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setUserType(UserType.customer);

		List<Product> products = new ArrayList<>();
		Product productFashion = new Product(ProductType.fashion, new BigDecimal(750));
		Product productGrocery = new Product(ProductType.groceries, new BigDecimal(1450));
		products.add(productFashion);
		products.add(productGrocery);

		Bill bill = new Bill();
		bill.setUser(user);
		bill.setProducts(products);

		HttpEntity<Bill> entity = new HttpEntity<>(bill);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri + Constants.BILL_DISCOUNTS_ENDPOINT,
				HttpMethod.POST, entity, String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("2054.35", responseEntity.getBody());
	}
}