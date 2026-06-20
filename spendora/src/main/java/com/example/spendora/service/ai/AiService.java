package com.example.spendora.service.ai;

import com.example.spendora.dto.AiActiveTaskDto;
import com.example.spendora.dto.AiInputDto;
import com.example.spendora.dto.AiTaskDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.model.AiParsingTask;
import com.example.spendora.service.ai.parsetask.AiParseTaskService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AiService {
    TransactionRequestDto parse (AiInputDto requestBody);
    void parse(AiParsingTask aiParsingTask) throws JsonProcessingException;
    AiTaskDto save(String appUserId, AiInputDto requestBody);
    List<AiActiveTaskDto> getActiveTasks(String appUserId);
}
