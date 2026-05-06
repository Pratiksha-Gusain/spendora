package com.example.spendora.service.account;

import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.model.Account;

import java.util.List;

public interface AccountService {
    boolean accountExistByUserandAccount(String appUserId, List<Long> accounts);

    void updateBalance(Long accountId, Double amount, Long paymenModeId, String type, boolean isSourceAccount) throws InsufficientAccountBalanceException;

    Account get(Long accountId);

    void update(Account account);
}
