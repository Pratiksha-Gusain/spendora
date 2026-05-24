package com.example.spendora.service.ai;

import com.example.spendora.dto.AiInputDto;
import com.example.spendora.dto.TransactionRequestDto;

public interface AiService {
    TransactionRequestDto parse (AiInputDto requestBody);
}
