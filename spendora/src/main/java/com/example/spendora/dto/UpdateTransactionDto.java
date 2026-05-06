package com.example.spendora.dto;

public record UpdateTransactionDto(
        Long transactionId,
        String type,
        Double amount,
        String description,
        Long paymentModeId,
        Long categoryId,
        Long accountId,
        String transactionDate,
        Long toAccountId


) {
}
