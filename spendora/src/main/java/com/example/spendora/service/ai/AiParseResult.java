package com.example.spendora.service.ai;

import com.example.spendora.model.TransactionType;

public record AiParseResult (
        TransactionType type,
    String description,
        Double amount,
        String date,
        String errorMessage,
        String category
){
}
