package com.example.spendora;

import com.example.spendora.dto.CreateTransactionDto;
import com.example.spendora.model.Account;
import com.example.spendora.model.AppUser;
import com.example.spendora.repository.AccountRepo;
import com.example.spendora.repository.AppUserRepo;
import com.example.spendora.repository.BankRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class CmdRunner implements CommandLineRunner {
    private final BankRepo bankRepo;
    private final AppUserRepo appUserRepo;
    private final AccountRepo accountRepo;
    @Override
    public void run(String... args) throws Exception {
      /*  final var dto = new CreateTransactionDto(
                "Expense",
                200.0,
                "200 rs petrol",
                1L,
                1L,
                1L,
                "10-04-2026"
        );
        System.out.println(mapper.writeValueAsString(dto));
        */
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
                    .lastFourDigit("0988")
                    .updatedAt(System.currentTimeMillis())
                    .build();
            accountRepo.save(account);
        }

    }
}
