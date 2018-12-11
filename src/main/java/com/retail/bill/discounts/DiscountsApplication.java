package com.retail.bill.discounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author RPathan
 *
 */
@SpringBootApplication
public class DiscountsApplication implements WebMvcConfigurer{

  public static void main(String[] args) {
    SpringApplication.run(DiscountsApplication.class, args);
  }
}
