package com.example.spendora.exception;

public class InsufficientAccountBalanceException extends Throwable {
    public InsufficientAccountBalanceException(Long accountId) {
        super(String.format("Insufficient account balance for account %s", accountId));
    }
}
