package com.example.spendora.service.ai;

import com.example.spendora.model.TransactionType;

public record AiParseResult (
        TransactionType type,
    Double amount,
    String description,
    String transactionDate,
    String errorMessage

){
}
