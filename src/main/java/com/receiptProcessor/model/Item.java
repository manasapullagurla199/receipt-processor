package com.receiptProcessor.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item {
    @NotBlank(message = "Short description is required.")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description must contain only letters, numbers, spaces, and hyphens.")
    private String shortDescription;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0.00.")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 2 decimal places.")
    private BigDecimal price;
}
