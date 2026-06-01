package com.example.spendora.event;

import com.example.spendora.dto.AiTaskDto;
import com.example.spendora.model.AiParsingTask;
import com.example.spendora.model.AppUser;
import com.example.spendora.service.ai.AiParseResult;

public record AiParsingTaskCompleted(
        Long jobId,
        AiParsingTask task
) {
}
