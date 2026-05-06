package com.example.spendora.repository;

import com.example.spendora.model.Account;
import com.example.spendora.model.AppUser;
import com.example.spendora.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepoTest {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private AppUserRepo appUserRepo;

    @Test
    void shouldReturnTrueWhenUserAndCategoryMatch(){
        final var appUser = AppUser.builder()
                .build();

        final var savedAppUser = appUserRepo.save(appUser);

        final var account = Category.builder()
                .appUser(savedAppUser)
                .build();

        final var savedCategory = categoryRepo.save(account);

        final var appUserId = savedAppUser.getId();
        final var categoryId = savedCategory.getId();
        assertTrue(categoryRepo.existsByAppUserIdAndCategoryId(appUserId, categoryId));
    }
    @Test
    void shouldReturnFalseWhenUserAndCategoryNotMatch() {
        final var appUser = AppUser.builder()
                .build();

        final var savedAppUser = appUserRepo.save(appUser);

        final var category = Category.builder()
                .appUser(savedAppUser)
                .build();

        final var savedCategory = categoryRepo.save(category);

        final var appUserId = UUID.randomUUID().toString();
        final var categoryId = savedCategory.getId();

        assertFalse(categoryRepo.existsByAppUserIdAndCategoryId(appUserId, categoryId));
    }
    @Test
    void shouldReturnTrueWhenCategoryMatch(){
        final var account = Category.builder()
                .build();

        final var savedCategory = categoryRepo.save(account);
        final var categoryId = savedCategory.getId();
        assertTrue(categoryRepo.existsById(categoryId));
    }
}
