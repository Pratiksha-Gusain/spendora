package com.example.spendora.dto;

public record AccountDto(
        String id,
        String bankName,
        String lastFour,
        String type,
        Double amount
) {}
