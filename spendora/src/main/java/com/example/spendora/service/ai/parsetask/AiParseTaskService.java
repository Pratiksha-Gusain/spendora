package com.example.spendora.service.ai.parsetask;

import com.example.spendora.model.AiParsingTask;
import com.example.spendora.model.Status;

import java.util.List;

public interface AiParseTaskService {
    AiParsingTask save(AiParsingTask aiParsingTask);

    List<AiParsingTask> getPendingTasks(Status status);

    AiParsingTask getByIdWithAppUser(Long aLong);
}
