package com.example.spendora.dto;

import com.example.spendora.service.transaction.TransactionBehavior;

public record PaymentModeResponseDto(
        Long id,
        String name,
        TransactionBehavior type
) {
}
