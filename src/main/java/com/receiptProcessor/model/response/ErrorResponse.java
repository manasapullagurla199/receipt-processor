package com.receiptProcessor.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorResponse implements Response{
    private String message;
    private Map<String, String> validationErrors;
}
