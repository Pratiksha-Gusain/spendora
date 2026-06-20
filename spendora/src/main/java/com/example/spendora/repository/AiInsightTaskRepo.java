package com.example.spendora.repository;

import com.example.spendora.model.AiInsightTask;
import com.example.spendora.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AiInsightTaskRepo extends JpaRepository<AiInsightTask, Long> {
    List<AiInsightTask> findAllByStatus(Status status, Pageable pageable);
    List<AiInsightTask> findAllByAppUserIdOrderByCreatedAtDesc(String appUserId);
}
