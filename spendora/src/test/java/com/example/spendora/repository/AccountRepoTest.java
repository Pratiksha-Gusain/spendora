package com.example.spendora.repository;

import com.example.spendora.model.Account;
import com.example.spendora.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepoTest {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AppUserRepo appUserRepo;
    @Test
    void shouldReturnTrueWhenUserAndAccountsMatched(){
        final var appUser = AppUser.builder()
                .build();
        final var savedAppUser = appUserRepo.save(appUser);
        final var account1 = Account.builder()
                .appUser(savedAppUser)
                .build();

        final var account2 = Account.builder()
                .appUser(savedAppUser)
                .build();
        final var savedAccount1 = accountRepo.save(account1);
        final var savedAccount2 = accountRepo.save(account2);

        final var appUserId = savedAppUser.getId();
        final var accounts = List.of(savedAccount1.getId(), savedAccount2.getId());
        assertTrue(accountRepo.existsByAppUserIdAndAccountId(appUserId, accounts,accounts.size() ));
    }
    @Test
    void shouldReturnFalseWhenUserAndAccountNotMatch(){
        final var appUser = AppUser.builder()
                .build();
        final var savedAppUser = appUserRepo.save(appUser);
        final var account1 = Account.builder()
                .appUser(savedAppUser)
                .build();

        final var account2 = Account.builder()
                .appUser(savedAppUser)
                .build();
        final var savedAccount1 = accountRepo.save(account1);
        final var savedAccount2 = accountRepo.save(account2);

        final var appUserId = UUID.randomUUID().toString();
        final var accounts = List.of(savedAccount1.getId(), savedAccount2.getId(), 3L);
        assertFalse(accountRepo.existsByAppUserIdAndAccountId(appUserId, accounts,accounts.size() ));
    }




}
