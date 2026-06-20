package com.example.spendora.repository;

import com.example.spendora.model.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserCategoryRepo extends JpaRepository<UserCategory, Long> {

    @Query("SELECT COUNT(c) > 0 " +
            "FROM UserCategory c " +
            "WHERE c.appUser.id = :appUserId " +
            "AND c.id = :categoryId")
    boolean existsByAppUserIdAndCategoryId(String appUserId, Long categoryId);

    List<UserCategory> findAllByAppUserId(String appUserId);

    Optional<UserCategory> findByNameAndAppUserId(String name, String appUserId);

}
