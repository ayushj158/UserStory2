package com.equalexperts.cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.equalexperts.cart.domain.OrderVO;

public class EndToEndTest {

	@Nested
	@DisplayName("Given An empty Shopping Cart And a product, Dove Soap with a unit price of 39.99")
	class AddItemTest {
		AddToCartService cart;

		@BeforeEach
		public void setup() {
			cart = new AddToCartService(1234);
		}

		@Test
		@DisplayName("When_user adds 5 Dove Soaps to shopping cart"
				+"And then adds another 3 Dove Soaps to the shopping cart"
				+ "_Then_Shopping cart should contain 8 Dove Soaps each with a unit price of 39.99"
				+ " And shopping cart’s total price should be 319.92")
		public void test_addItem() {
			final String productUPC = "Dove Soaps";
			final Double unitPrice = 39.99;
			final int  quantity= 5;
			cart.addItem(productUPC, unitPrice, quantity);
			cart.addItem(productUPC, unitPrice, 3);
			
			OrderVO result = cart.calculateOrder(cart.getOrder());
			
			assertEquals(1, cart.getOrder().getOrderLines().size());
			assertEquals(8, cart.getOrder().getOrderLines().get(productUPC).getQuantity());
			assertEquals(unitPrice, cart.getOrder().getOrderLines().get(productUPC).getUnitPrice(),0.001);
			assertEquals(new BigDecimal(319.92).setScale(2, RoundingMode.HALF_DOWN),result.getTotalPrice());
		}
	}
}
