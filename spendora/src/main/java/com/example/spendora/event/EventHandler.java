package com.example.spendora.event;

import com.example.spendora.dto.JobStatusDto;
import com.example.spendora.mapper.TransactionMapper;
import com.example.spendora.service.ai.AiParseResult;
import com.example.spendora.service.ai.parsetask.AiParseTaskService;
import com.example.spendora.service.category.CategoryService;
import com.example.spendora.service.notification.NotificationService;
import com.example.spendora.service.transaction.TransactionsService;
import com.example.spendora.service.userconfig.UserConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventHandler {
    private final NotificationService notificationService;
    private final TransactionsService transactionsService;
    private final UserConfigService userConfigService;
    private final ObjectMapper mapper;
    private final AiParseTaskService aiParseTaskService;
    private final CategoryService categoryService;

    @Async
    @EventListener(AiParsingTaskCompleted.class)
    public void notifyClient(AiParsingTaskCompleted event){
        log.info("Ai parsing task completed. Notifying client..");
        final var jobStatus = JobStatusDto.of(event.jobId().toString(), event.task().getStatus().name());
        notificationService.send(jobStatus);
        notificationService.closeConnection(event.jobId().toString());
    }
    @Async
    @EventListener(AiParsingTaskCompleted.class)
    public void saveResultAsTxn(AiParsingTaskCompleted event) throws JsonProcessingException {
        log.info("Ai parsing task completed. Converting and saving into db");
        final var task = aiParseTaskService.getByIdWithAppUser(event.jobId());

        final var appUserId = task.getAppUser().getId();

        // Get default Payment mode and account
        final var userConfig = userConfigService.getByUserId(appUserId);

        final var aiParseResult = mapper.readValue(event.task().getContent(), AiParseResult.class);

        final var category = categoryService.getByName(aiParseResult.category());

        final var requestDto = TransactionMapper.INSTANCE.fromAiParseTask(
                aiParseResult, // Task --> Source
                userConfig.getDefaultPaymentMode().getId(), // Payment Mode Id
                userConfig.getDefaultAccount().getId(), // Account ID
                category.getId()  // Category Id

        );

    }

    @EventListener(AiParsingTaskCreated.class)
    public void openConnection(AiParsingTaskCreated event){
        log.info("Ai parsing task created. Opening connection..");
        notificationService.openConnection(event.jobId().toString());
    }
}
