package com.example.spendora.service.transaction.strategy;

import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.exception.TransactionNotFoundException;
import com.example.spendora.mapper.TransactionMapper;
import com.example.spendora.model.Transaction;
import com.example.spendora.model.TransactionType;
import com.example.spendora.repository.TransactionRepo;
import com.example.spendora.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("ExpenseIncomeStrategy")
@RequiredArgsConstructor
public class ExpenseIncomeStrategy implements TransactionTypeStrategy {
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final TransactionRepo transactionRepo;

    @Transactional
    @Override
    public TransactionDto process(String appUserId, TransactionRequestDto dto, OperationType type) throws InsufficientAccountBalanceException {

        Transaction transaction;

        if(type == OperationType.UPDATE){
            transaction = transactionRepo.findById(dto.transactionId())
                    .orElseThrow(() -> new TransactionNotFoundException(dto.transactionId()));

            accountService.reverseBalance(dto.accountId(), Math.abs(transaction.getAmount()), dto.paymentModeId(), dto.type(), false);

        }else{
            transaction = new Transaction();
        }

        accountService.updateBalance(dto.accountId(), dto.amount(), dto.paymentModeId(), dto.type(), false);

        transactionMapper.transactionFromRequestDto(dto, transaction, appUserId, null, false);

        final var savedTransaction = transactionRepo.save(transaction);

        return transactionMapper.transactionDtoToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.INCOME;
    }
}
