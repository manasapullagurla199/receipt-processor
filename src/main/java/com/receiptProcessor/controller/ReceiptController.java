package com.receiptProcessor.controller;

import com.receiptProcessor.model.response.ErrorResponse;
import com.receiptProcessor.model.response.PointsResponse;
import com.receiptProcessor.model.Receipt;
import com.receiptProcessor.model.response.ReceiptResponse;
import com.receiptProcessor.model.response.Response;
import com.receiptProcessor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService service;

    @PostMapping(value = "/process", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReceiptResponse> processReceipt(@Valid @RequestBody Receipt receipt) {

        String id = service.processReceipt(receipt);
        return ResponseEntity.ok(new ReceiptResponse(id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Response> getPoints(@PathVariable String id) {

        try {
            UUID.fromString(id); // Validate UUID format
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Invalid ID format.", null));
        }
        Integer points = service.getPoints(id);
        if (points == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No receipt found for that ID.", null));
        }
        return ResponseEntity.ok(new PointsResponse(points));
    }
}
