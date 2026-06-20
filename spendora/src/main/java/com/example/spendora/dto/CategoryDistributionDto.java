package com.example.spendora.dto;

public record CategoryDistributionDto(
        String label,
        Double amount,
        Double limit
) {}
