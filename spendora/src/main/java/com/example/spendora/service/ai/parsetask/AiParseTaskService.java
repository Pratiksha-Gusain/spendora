package com.example.spendora.service.ai.parsetask;

import com.example.spendora.model.AiParsingTask;
import com.example.spendora.model.Status;

import java.util.List;

public interface AiParseTaskService {
    AiParsingTask save(AiParsingTask aiParsingTask);

    List<AiParsingTask> getPendingTasks(Status status);

    List<AiParsingTask> getPendingTasksWithAppUser(Status status);

    AiParsingTask getByIdWithAppUser(Long aLong);

    List<AiParsingTask> getActiveTasks(String appUserId);
}
