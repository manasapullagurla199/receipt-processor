package com.receiptProcessor.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Receipt {
    @NotBlank(message = "Retailer name is required.")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer must contain only letters, numbers, spaces, hyphens, or ampersands.")
    private String retailer;

    @NotBlank(message = "Purchase date is required.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Purchase date must be in format yyyy-MM-dd.")
    private String purchaseDate;

    @NotBlank(message = "Purchase time is required.")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Purchase time must be in format HH:mm.")
    private String purchaseTime;

    @NotNull(message = "Items list cannot be null.")
    @Valid // Validate each item in the list
    private List<Item> items;

    @NotNull(message = "Total is required.")
    @DecimalMin(value = "0.00", inclusive = false, message = "Total must be greater than or equal to 0.00.")
    @Digits(integer = 10, fraction = 2, message = "Total must have up to 2 decimal places.")
    private BigDecimal total;
}
