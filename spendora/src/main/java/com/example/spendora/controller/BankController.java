package com.example.spendora.controller;

import com.example.spendora.model.Bank;
import com.example.spendora.repository.BankRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankRepo bankRepo;

    @GetMapping
    public ResponseEntity<List<Bank>> getSupportedBanks() {
        final var banks = StreamSupport.stream(bankRepo.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(banks);
    }
}
