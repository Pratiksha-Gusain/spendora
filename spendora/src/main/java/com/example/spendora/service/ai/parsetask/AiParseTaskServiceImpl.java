package com.example.spendora.service.ai.parsetask;

import com.example.spendora.model.AiParsingTask;
import com.example.spendora.model.Status;
import com.example.spendora.repository.AiParsingTaskRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AiParseTaskServiceImpl implements AiParseTaskService {
    private final AiParsingTaskRepo repo;

    @Override
    public AiParsingTask save(AiParsingTask aiParsingTask) {
        return repo.save(aiParsingTask);
    }

    @Override
    public List<AiParsingTask> getPendingTasks(Status status) {
        return repo.findAllByStatusOrderByCreatedAtAsc(status, PageRequest.of(0, 13));
    }

    @Override
    public List<AiParsingTask> getPendingTasksWithAppUser(Status status) {
        return repo.findAllByStatusWithAppUserOrderByCreatedAtAsc(status, PageRequest.of(0, 13));
    }

    @Override
    public AiParsingTask getByIdWithAppUser(Long jobId) {
        return repo.findByIdWithAppUser(jobId)
                .orElseThrow(()-> new RuntimeException("Ai Parse Task not found with id: " + jobId));
    }

    @Override
    public List<AiParsingTask> getActiveTasks(String appUserId) {
        return repo.findActiveTasks(appUserId, List.of(Status.PENDING, Status.PROCESSING));
    }
}
