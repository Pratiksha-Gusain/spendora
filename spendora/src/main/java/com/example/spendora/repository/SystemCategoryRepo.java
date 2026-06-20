package com.example.spendora.repository;

import com.example.spendora.model.SystemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemCategoryRepo extends JpaRepository<SystemCategory, Long> {
     Optional<SystemCategory> findByName(String name);
}
