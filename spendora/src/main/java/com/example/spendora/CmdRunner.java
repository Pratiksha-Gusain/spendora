package com.example.spendora;

import com.example.spendora.model.Account;
import com.example.spendora.model.AppUser;
import com.example.spendora.repository.AccountRepo;
import com.example.spendora.repository.AppUserRepo;
import com.example.spendora.repository.BankRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CmdRunner implements CommandLineRunner {
    private final BankRepo bankRepo;
    private final AppUserRepo appUserRepo;
    private final AccountRepo accountRepo;
    @Override
    public void run(String... args) throws Exception {

        if (appUserRepo.findAll().isEmpty()){
            final var bank = bankRepo.findByName("State Bank of India")
                    .orElseThrow();

            final var appUser = AppUser.builder()
                    .name("Akshay")
                    .email("akshay@mail.com")
                    .password("password")
                    .build();

            final var savedUser = appUserRepo.save(appUser);
            log.info("user created: {}", savedUser.getId());

            final var account = Account.builder()
                    .bank(bank)
                    .appUser(AppUser.ofId(savedUser.getId()))
                    .balance(2500.00)
                    .lastFourDigits("0988")
                    .build();
            accountRepo.save(account);
        }

    }
}
