package com.example.spendora.service.transaction;

import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionsService {
    TransactionDto saveTransaction(String appUserId, TransactionRequestDto requestBody) throws InsufficientAccountBalanceException;

    Page<TransactionDto> getAllTransactions(
            String appUserId,
            LocalDate startDate,
            LocalDate endDate,
            Double minAmount,
            Double maxAmount,
            List<TransactionType> types,
            List<Long> categoryIds,
            List<Long> accountIds,
            List<Long> paymentModeIds,
            String search,
            Pageable pageable
    );

    TransactionDto updateTransaction(String appUserId, TransactionRequestDto requestBody) throws InsufficientAccountBalanceException;

    void deleteTransaction(String appUserId, Long transactionId);

    List<TransactionDto> getRecentTransactions(String userId);
}

