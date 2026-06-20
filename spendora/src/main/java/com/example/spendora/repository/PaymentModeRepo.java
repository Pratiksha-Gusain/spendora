package com.example.spendora.repository;

import com.example.spendora.model.Account;
import com.example.spendora.model.PaymentMode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentModeRepo extends CrudRepository<PaymentMode, Long> {
    Optional<PaymentMode> findByName(String name);
}
