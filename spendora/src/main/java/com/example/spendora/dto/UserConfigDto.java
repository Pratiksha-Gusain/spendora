package com.example.spendora.dto;

public record UserConfigDto(
        String language,
        Long defaultPaymentModeId
) {}
