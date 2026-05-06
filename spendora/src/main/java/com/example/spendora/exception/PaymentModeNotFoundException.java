package com.example.spendora.exception;

public class PaymentModeNotFoundException extends RuntimeException {
    public PaymentModeNotFoundException(Long paymentModeId) {
        super("PayamentMode not found with id" + paymentModeId);
    }
}
