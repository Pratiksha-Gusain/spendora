package com.example.spendora.exception;

public class BankNotFoundException extends RuntimeException {
    public BankNotFoundException(Long bankId) {
        super("Bank not found with id " + bankId);
    }
}