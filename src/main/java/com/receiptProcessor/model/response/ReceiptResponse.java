package com.receiptProcessor.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptResponse implements Response{
    private String id;
}
