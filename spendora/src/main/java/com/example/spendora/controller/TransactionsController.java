package com.example.spendora.controller;

import com.example.spendora.dto.CreateTransactionDto;
import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.UpdateTransactionDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.service.transaction.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private static  final String LOGGED_IN_USER = "dc17b28a-0ae6-4bb2-bbd6-6a910e242a52";
    private final TransactionsService transactionsService;
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody CreateTransactionDto requestBody) throws InsufficientAccountBalanceException {
        final var responseBody= transactionsService.saveTransaction( LOGGED_IN_USER, requestBody);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);

    }
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        final var responseBody = transactionsService.getAllTransactions(LOGGED_IN_USER);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @PatchMapping
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody UpdateTransactionDto requestBody) {
        final var responseBody = transactionsService.updateTransaction(LOGGED_IN_USER, requestBody);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionsService.deleteTransaction(LOGGED_IN_USER, transactionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


}
