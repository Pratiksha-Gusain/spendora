package com.example.spendora.service.paymentmode;

import com.example.spendora.model.PaymentMode;

public interface PaymentModeService {
    public boolean existById(Long paymentModeId);

    public PaymentMode get(Long paymentModeId);
}
