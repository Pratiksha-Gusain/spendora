package com.example.spendora.repository;

import com.example.spendora.model.Account;
import com.example.spendora.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);
}
