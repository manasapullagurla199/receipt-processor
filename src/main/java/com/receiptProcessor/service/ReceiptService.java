package com.receiptProcessor.service;

import com.receiptProcessor.model.Item;
import com.receiptProcessor.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReceiptService {
    private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();
    private final Map<String, Integer> pointsStore = new ConcurrentHashMap<>();

    public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptStore.put(id, receipt);
        pointsStore.put(id, calculatePoints(receipt));
        return id;
    }

    public Integer getPoints(String id) {
        return pointsStore.get(id);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point per alphanumeric character in retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if total is a round dollar amount
        BigDecimal total = receipt.getTotal();
        if (total.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            points += 50;
        }

        // Rule 3: 25 points if total is a multiple of 0.25
        BigDecimal divisor = new BigDecimal("0.25");
        if (total.remainder(divisor).compareTo(BigDecimal.ZERO) == 0) points += 25;

        // Rule 4: 5 points for every two items
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: Item description length and price rule
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                BigDecimal price = item.getPrice(); // Directly get the BigDecimal price
                double pointsFromItem = Math.ceil(price.multiply(new BigDecimal("0.2")).doubleValue()); // Multiply and ceil
                points += (int) pointsFromItem; // Convert to int and add to points
            }
        }

        // Rule 6: 6 points if purchase day is odd
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 == 1) points += 6;

        // Rule 7: 10 points if purchase time is between 2pm and 4pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        if ((hour == 14 && minute > 0) || (hour > 14 && hour < 16)) {
            points += 10;
        }

        return points;
    }
}
