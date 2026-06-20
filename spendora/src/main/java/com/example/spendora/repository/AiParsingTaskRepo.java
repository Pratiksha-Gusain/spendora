package com.example.spendora.repository;

import com.example.spendora.model.AiParsingTask;
import com.example.spendora.model.Status;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AiParsingTaskRepo extends JpaRepository<AiParsingTask, Long> {
    List<AiParsingTask> findAllByStatusOrderByCreatedAtAsc(Status status,Pageable pageable);

    @Query("SELECT apt " +
            "FROM AiParsingTask apt " +
            "JOIN FETCH apt.appUser u " +
            "WHERE apt.status = :status " +
            "ORDER BY apt.createdAt ASC")
    List<AiParsingTask> findAllByStatusWithAppUserOrderByCreatedAtAsc(
            @Param("status") Status status,
            Pageable pageable);

    @Query("SELECT apt " +
            "FROM AiParsingTask apt " +
            "JOIN FETCH apt.appUser u " +
            "WHERE apt.id = :jobId")
    Optional<AiParsingTask> findByIdWithAppUser(@Param("jobId") Long jobId);

    @Query("SELECT apt " +
            "FROM AiParsingTask apt " +
            "WHERE apt.appUser.id = :appUserId AND apt.status IN :statuses " +
            "ORDER BY apt.createdAt ASC")
    List<AiParsingTask> findActiveTasks(
            @Param("appUserId") String appUserId,
            @Param("statuses") List<Status> statuses);
}

