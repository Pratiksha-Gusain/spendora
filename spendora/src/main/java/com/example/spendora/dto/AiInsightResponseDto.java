package com.example.spendora.dto;

public record AiInsightResponseDto(
        Long id,
        String type,
        String insightText,
        Long createdAt,
        String status
) { }
