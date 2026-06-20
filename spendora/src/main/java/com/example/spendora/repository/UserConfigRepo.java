package com.example.spendora.repository;

import com.example.spendora.model.UserConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserConfigRepo extends CrudRepository<UserConfig, Long> {
    Optional<UserConfig> findByAppUserId(String appUserId);
}