package com.example.spendora.repository;

import com.example.spendora.model.AiParsingTask;
import com.example.spendora.model.Status;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface AiParsingTaskRepo extends JpaRepository<AiParsingTask, Long> {
    List<AiParsingTask> findAllByStatusOrderByCreatedAtAsc(Status status, Pageable pageable);

    @Query("SELECT apt " +
            "FROM AiParsingTask apt " +
            "JOIN FETCH apt.appUser u " +
            "WHERE apt.id = :jobId")
    Optional<AiParsingTask> findByIdWithAppUser(Long jobId);


}
