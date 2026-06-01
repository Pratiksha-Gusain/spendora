package com.example.spendora.exception;

public class UserAlreadyOnboardedException extends RuntimeException {
    public UserAlreadyOnboardedException(String userId) {
        super("User with id " + userId + " is already onboarded.");
    }
}
