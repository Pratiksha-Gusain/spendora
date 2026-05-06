package com.example.spendora.repository;

import com.example.spendora.model.AppUser;
import com.example.spendora.model.Bank;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BankRepo extends CrudRepository<Bank, Long> {
    Optional<Bank> findByName(String stateBankOfIndia);
}
