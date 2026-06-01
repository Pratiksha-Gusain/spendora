package com.example.spendora.service.transaction.strategy;

import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.model.TransactionType;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionTypeStrategy {

    @Transactional
    TransactionDto process(String appUserId, TransactionRequestDto dto, OperationType type) throws InsufficientAccountBalanceException;

    TransactionType getType();
}
