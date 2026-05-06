package com.example.spendora.service.transaction;

import com.example.spendora.dto.CreateTransactionDto;
import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.UpdateTransactionDto;
import com.example.spendora.exception.*;
import com.example.spendora.mapper.TransactionMapper;
import com.example.spendora.model.*;
import com.example.spendora.repository.TransactionRepo;
import com.example.spendora.service.appuser.AppUserService;
import com.example.spendora.service.category.CategoryService;
import com.example.spendora.service.paymentmode.PaymentModeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.spendora.service.account.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {
    private final AppUserService appUserService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final PaymentModeService paymentModeService;
    private final TransactionRepo transactionRepo;
    private final TransactionMapper transactionMapper;
    @Transactional
    @Override
    public TransactionDto saveTransaction(String appUserId, CreateTransactionDto requestBody) throws InsufficientAccountBalanceException {
        final var accountId = requestBody.accountId();
        final var categoryId = requestBody.categoryId();
        final var paymentModeId = requestBody.paymentModeId();
        final var toAccountId = requestBody.toAccountId();
        final var type = requestBody.type();

        List<Long> accounts = getAccounts(accountId, type, toAccountId);

        validateAccountCategoryAndPaymentMode(appUserId, accounts, categoryId, paymentModeId);

        if(TransactionType.valueOf(type) == TransactionType.TRANSFER){
            return handleTransfer(appUserId, requestBody, accountId, toAccountId, type, categoryId, paymentModeId);
        }

        return handleExpenseOrIncome(appUserId, requestBody, accountId, type, categoryId, paymentModeId);
    }

    private TransactionDto handleTransfer(String appUserId, CreateTransactionDto requestBody, Long accountId, Long toAccountId,String type, Long categoryId, Long paymentModeId) throws InsufficientAccountBalanceException {
        accountService.updateBalance(accountId, requestBody.amount(), requestBody.paymentModeId(),type,true );
        accountService.updateBalance(toAccountId, requestBody.amount(), requestBody.paymentModeId(),type,false);

        final var transferId = UUID.randomUUID().toString();
        final var debitTransaction = Transaction.builder()
                .appUser(AppUser.builder().id(appUserId).build())
                .type(TransactionType.valueOf(type))
                .amount(-requestBody.amount())
                .category(Category.ofId(categoryId))
                .paymentMode(PaymentMode.ofId(paymentModeId))
                .description(requestBody.description())
                .transactionDate(requestBody.transactionDate())
                .transferId(transferId)
                .build();
        final var creditTransaction = Transaction.builder()
                .appUser(AppUser.builder().id(appUserId).build())
                .account(Account.ofId(toAccountId))
                .type(TransactionType.valueOf(type))
                .amount(requestBody.amount())
                .category(Category.ofId(categoryId))
                .paymentMode(PaymentMode.ofId(paymentModeId))
                .description(requestBody.description())
                .transactionDate(requestBody.transactionDate())
                .build();

        transactionRepo.save(debitTransaction);
        final var savedTransaction = transactionRepo.save(creditTransaction);

        return transactionMapper.transactionDtoToTransactionDto(savedTransaction);
    }

    private TransactionDto handleExpenseOrIncome(String appUserId, CreateTransactionDto requestBody, Long accountId, String type, Long categoryId, Long paymentModeId) throws InsufficientAccountBalanceException {
        accountService.updateBalance(accountId, requestBody.amount(), requestBody.paymentModeId(), type,false );

        final var transactionType = TransactionType.valueOf(type);
        final var transaction = Transaction.builder()
                .appUser(AppUser.builder().id(appUserId).build())
                .type(TransactionType.valueOf(type))
                .amount(transactionType ==  TransactionType.EXPENSE? -requestBody.amount(): requestBody.amount())
                .category(Category.ofId(categoryId))
                .paymentMode(PaymentMode.ofId(paymentModeId))
                .description(requestBody.description())
                .transactionDate(requestBody.transactionDate())
                .build();
        final var savedTransaction = transactionRepo.save(transaction);
        return transactionMapper.transactionDtoToTransactionDto(savedTransaction);
    }

    private static List<Long> getAccounts(Long accountId, String type, Long toAccountId) {
        List<Long> accounts = new ArrayList<>();
        accounts.add(accountId);

        if(TransactionType.valueOf(type) == TransactionType.TRANSFER){

            accounts.add(toAccountId);
        }
        return accounts;
    }

    private void validateAccountCategoryAndPaymentMode(String appUserId,  List<Long> accounts, Long categoryId, Long paymentModeId) {
        final var accountExists = accountService.accountExistByUserandAccount(appUserId, accounts);
        if(!accountExists) {
           throw new AccountNotOwnedByUserException(accounts, appUserId);
        }

        final var categoryExists = categoryService.existByUserAndCategory(appUserId, categoryId);
        if(!categoryExists){
            throw new CategoryNotFoundException(categoryId);
        }

        final var paymentModeExists = paymentModeService.existById(paymentModeId);
        if(!paymentModeExists){
            throw new PaymentModeNotFoundException(paymentModeId);
        }
    }

    @Override
    public List<TransactionDto> getAllTransactions(String appUserId){
        final var transactions = transactionRepo.findAllByAppUser(appUserId);
        return transactionMapper.transactionDtosToTransactionDtos(transactions);
    }
    @Override
    public TransactionDto updateTransaction(String appUserId, UpdateTransactionDto requestBody) {
        final var accountId = requestBody.accountId();
        final var categoryId = requestBody.categoryId();
        final var paymentModeId = requestBody.paymentModeId();
        final var toAccountId = requestBody.toAccountId();
        final var type = requestBody.type();

        final var accounts = getAccounts(accountId, type, toAccountId);


        validateAccountCategoryAndPaymentMode(appUserId, accounts, categoryId, paymentModeId);

        final var transaction = transactionRepo.findById(requestBody.transactionId())
                .orElseThrow(()-> new TransactionNotFoundException(requestBody.transactionId()));
        transactionMapper.updateTransactionFromDto(requestBody, transaction, appUserId);
        final var savedTransaction = transactionRepo.save(transaction);
        return transactionMapper.transactionDtoToTransactionDto(savedTransaction);
    }
    @Override
    public void deleteTransaction(String appUserId, Long transactionId) {
        transactionRepo.deleteByIdAndAppUserId(transactionId, appUserId);
    }
}
