package com.example.spendora.service.transaction;

import com.example.spendora.dto.CreateTransactionDto;
import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.UpdateTransactionDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface TransactionsService {
    TransactionDto saveTransaction(String appUserId, CreateTransactionDto requestBody) throws InsufficientAccountBalanceException;

    List<TransactionDto> getAllTransactions(String loggedInUser);

    TransactionDto updateTransaction(String loggedInUser, UpdateTransactionDto requestBody);
    void deleteTransaction(String appUserId, Long transactionId);
}

