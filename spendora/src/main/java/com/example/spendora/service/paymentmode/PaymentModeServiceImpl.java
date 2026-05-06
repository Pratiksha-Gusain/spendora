package com.example.spendora.service.paymentmode;

import com.example.spendora.exception.PaymentModeNotFoundException;
import com.example.spendora.model.PaymentMode;
import com.example.spendora.repository.PaymentModeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentModeServiceImpl implements PaymentModeService{
    private final PaymentModeRepo paymentModeRepo;
    @Override
    public boolean existById(Long paymentModeId){
        return paymentModeRepo.existsById(paymentModeId);
    }

    @Override
    public PaymentMode get(Long paymentModeId) {
        return paymentModeRepo.findById(paymentModeId).orElseThrow(()-> new PaymentModeNotFoundException(paymentModeId));
    }
}
