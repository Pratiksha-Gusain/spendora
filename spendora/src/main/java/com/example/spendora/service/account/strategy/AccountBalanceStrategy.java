package com.example.spendora.service.account.strategy;

import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.model.Account;
import com.example.spendora.model.TransactionType;
import com.example.spendora.service.transaction.TransactionBehavior;

public interface AccountBalanceStrategy {
    Double calculateBalance(Account account, Double amount, TransactionType transactionType, boolean isSourceAccount) throws InsufficientAccountBalanceException;
    Double reverseBalance(Account account, Double previousAmount, TransactionType transactionType, boolean isSourceAccount);
    void validate(Account account, Double amount) throws InsufficientAccountBalanceException;

    TransactionBehavior getType();

}
