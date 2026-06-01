package com.example.spendora.dto;

public record TransactionDto(
        String transactionId,
        String type,
        Double amount,
        String description,
        String category,
        String transactionDate
) {

}
