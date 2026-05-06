package com.example.spendora.dto;

public record TransactionDto(
        String id,
        String type,
        Double amount,
        String description,
        String category,
        String transactionDate
) {

}
