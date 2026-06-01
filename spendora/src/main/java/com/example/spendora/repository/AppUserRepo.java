package com.example.spendora.repository;

import com.example.spendora.model.Account;
import com.example.spendora.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String> {
    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);
}
