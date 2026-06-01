package com.example.spendora.service.transaction;

import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;

import java.util.List;

public interface TransactionsService {
    TransactionDto saveTransaction(String appUserId, TransactionRequestDto requestBody) throws InsufficientAccountBalanceException;

    List<TransactionDto> getAllTransactions(String loggedInUser);

    TransactionDto updateTransaction(String loggedInUser, TransactionRequestDto requestBody);
    void deleteTransaction(String appUserId, Long transactionId);

    List<TransactionDto> getRecentTransactions(String userId);
}

