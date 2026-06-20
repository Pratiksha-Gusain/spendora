package com.example.spendora.dto;

import com.example.spendora.model.Status;

public record AiActiveTaskDto(
        String jobId,
        Status status
) {
}
