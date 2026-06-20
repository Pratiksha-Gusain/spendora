package com.example.spendora.service.paymentmode;

import com.example.spendora.model.PaymentMode;

public interface PaymentModeService {
    public boolean existsById(Long paymentModeId);

    public PaymentMode get(Long paymentModeId);

}
