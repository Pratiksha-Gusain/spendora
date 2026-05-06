package com.example.spendora.dto;

public record CreateTransactionDto(
        String type,
        Double amount,
        String description,
        Long paymentModeId,
        Long categoryId,
        Long accountId,
        String transactionDate,
        Long toAccountId

){
}
