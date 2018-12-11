package com.retail.bill.discounts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author RPathan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DiscountsApplicationTest {

	private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void contextLoads() {
		LOGGER.info("Application is working fine");
	}
}
