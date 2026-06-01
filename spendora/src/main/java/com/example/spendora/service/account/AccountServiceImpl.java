package com.example.spendora.service.account;

import com.example.spendora.dto.AccountDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.exception.AccountNotFoundException;

import com.example.spendora.model.Account;
import com.example.spendora.model.TransactionType;
import com.example.spendora.repository.AccountRepo;
import com.example.spendora.service.paymentmode.PaymentModeService;
import com.example.spendora.service.account.strategy.AccountBalanceStrategyFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepo accountRepo;
    private final PaymentModeService paymentModeService;
    private final AccountBalanceStrategyFactory accountBalanceStrategyFactory;
    @Override
    public boolean accountExistByUserandAccount(String appUserId, List<Long> accounts){
        return accountRepo.existsByAppUserIdAndAccountId(appUserId, accounts,accounts.size() );

    }
    @Transactional
    @Override
    public void updateBalance(Long accountId, Double amount, Long paymentModeId, String type, boolean isSourceAccount) throws InsufficientAccountBalanceException {
        final var paymentMode = paymentModeService.get(paymentModeId);

        final var accountBalanceStrategy = accountBalanceStrategyFactory.getBalanceStrategy(paymentMode.getType());
        final var account = this.get(accountId);
        final var updatedBalance = accountBalanceStrategy.calculateBalance(account, amount, TransactionType.valueOf(type),isSourceAccount);
        account.setBalance(updatedBalance);
        this.update(account);
    }

    @Override
    public Account get(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(()-> new AccountNotFoundException(accountId));
    }

    @Override
    public void update(Account account) {
        accountRepo.save(account);
    }

    @Override
    public void reverseBalance(Long accountId, Double amount, Long paymentModeId, String type, boolean isSourceAccount) {
        final var paymentMode = paymentModeService.get(paymentModeId);

        final var accountBalanceStrategy = accountBalanceStrategyFactory.getBalanceStrategy(paymentMode.getType());

        final var account = this.get(accountId);

        final var updatedBalance = accountBalanceStrategy.reverseBalance(account, amount, TransactionType.valueOf(type),
                isSourceAccount);

        account.setBalance(updatedBalance);

        this.update(account);
    }

    @Override
    public List<AccountDto> getAllAccounts(String userId) {
        return accountRepo.findAllByAppUserId(userId)
                .stream()
                .map(a -> new AccountDto(a.getId(), "%s (****%s)".formatted(a.getBank().getName(), a.getLastFourDigits())))
                .toList();
    }
}
