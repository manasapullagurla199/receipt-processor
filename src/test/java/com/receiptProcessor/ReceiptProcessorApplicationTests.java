package com.receiptProcessor;

import com.receiptProcessor.model.Item;
import com.receiptProcessor.model.Receipt;
import com.receiptProcessor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptProcessorApplicationTests {

	@Test
	void contextLoads() {
		// This is a placeholder test to ensure the application context loads successfully.

	}
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testProcessReceipt_InValidRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Receipt receipt = new Receipt();
		receipt.setRetailer("Store123");
		receipt.setTotal(new BigDecimal("10.00"));
		receipt.setItems(Arrays.asList(
				new Item("Item1", new BigDecimal("2.50"))
		));

		HttpEntity<Receipt> request = new HttpEntity<>(receipt, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("/receipts/process", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Test
	void testProcessReceipt_ValidRequest() {
		// Arrange: Create a valid Receipt object
		Receipt receipt = new Receipt();
		receipt.setRetailer("Shop123"); // Valid retailer name
		receipt.setPurchaseDate("2023-01-01"); // Valid date
		receipt.setPurchaseTime("14:33"); // Valid time
		receipt.setTotal(new BigDecimal("6.00")); // Valid total

		// Valid items
		receipt.setItems(Arrays.asList(
				new Item("Item1", new BigDecimal("2.50")),
				new Item("Item2", new BigDecimal("3.50"))
		));

		// Create HTTP headers
		HttpHeaders headers = createHeaders();

		// Create an HTTP request entity with the receipt object
		HttpEntity<Receipt> request = new HttpEntity<>(receipt, headers);

		// Act: Send the POST request to /receipts/process
		ResponseEntity<String> response = restTemplate.postForEntity(
				"/receipts/process",
				request,
				String.class
		);

		// Assert: Check that the response status is 200 OK
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP status 200 OK");

		// Assert: Verify the response body contains a valid ID
		assertNotNull(response.getBody(), "Response body should not be null");
		System.out.println("Response: " + response.getBody());
	}

	@Test
	void testGetPoints_NonExistentId() {
		// Arrange: Use a valid UUID that does not exist in the system
		String nonExistentId = "123e4567-e89b-12d3-a456-426614174000";

		// Act: Send GET request with non-existent ID
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/receipts/" + nonExistentId + "/points",
				String.class
		);

		// Assert: Verify that the status is 404 NOT_FOUND
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected 404 NOT_FOUND for non-existent ID.");

		// Assert: Verify response contains error message
		assertNotNull(response.getBody(), "Response body should not be null.");
		System.out.println("Response: " + response.getBody());
	}


}
