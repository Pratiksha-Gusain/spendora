package com.example.spendora.event;

import com.example.spendora.dto.JobStatusDto;
import com.example.spendora.exception.InsufficientAccountBalanceException;
import com.example.spendora.mapper.TransactionMapper;
import com.example.spendora.security.TenantContext;
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

import java.util.Map;

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
    public void saveResultAsTxn(AiParsingTaskCompleted event) throws JsonProcessingException, InsufficientAccountBalanceException {
        final var jobId = event.jobId().toString();

        log.info("Ai parsing task completed. Converting and saving data into DB. Job ID: {}", event.jobId());
        String appUserId = null;

        try {
            final var task = aiParseTaskService.getByIdWithAppUser(event.jobId());
            appUserId = task.getAppUser().getId();
            log.info("Processing transaction parsing result for user ID: {}", appUserId);

            // Set the TenantContext for the async thread
            TenantContext.setTenantId(appUserId);

            // Get default Payment mode and account
            final var userConfig = userConfigService.getByUserId(appUserId);

            final var aiParseResult = mapper.readValue(event.task().getContent(), AiParseResult.class);
            log.info("Parsed AI result content: {}", aiParseResult);

            if (aiParseResult.errorMessage() != null && !aiParseResult.errorMessage().isEmpty()) {
                log.warn("AI parsing returned error: {}. Aborting transaction save.", aiParseResult.errorMessage());
                notificationService.send(appUserId, null, NotificationService.NotificationEvent.AI_TASK_FAILED, Map.of(
                        "jobId", jobId,
                        "error", aiParseResult.errorMessage()));
                return;
            }

            final var category = categoryService.getSystemCategoryByName(aiParseResult.category());

            final var requestDto = TransactionMapper.INSTANCE.fromAiParseTask(
                    aiParseResult, // Task --> Source
                    userConfig.getDefaultPaymentMode().getId(), // Payment Mode Id
                    userConfig.getDefaultAccount().getId(), // Account ID
                    category.getId() // System Category Id
            );

            log.info("Saving transaction: {}", requestDto);
            transactionsService.saveTransaction(appUserId, requestDto);
            log.info("Transaction saved successfully for Job ID: {}", event.jobId());

            // Notify client AFTER successful save to prevent false-positive "Success"
            notificationService.send(appUserId, null, NotificationService.NotificationEvent.AI_TASK_COMPLETED, Map.of(
                    "jobId", jobId,
                    "status", "COMPLETED"));

        } catch (Exception e) {
            log.error("Failed to save transaction for completed AI parsing task. Job ID: {}", event.jobId(), e);
            if (appUserId != null) {
                try {
                    notificationService.send(appUserId, null, NotificationService.NotificationEvent.AI_TASK_FAILED, Map.of(
                            "jobId", jobId,
                            "error", e.getMessage() != null ? e.getMessage() : "Unknown error occurred during processing"));
                } catch (Exception notifyEx) {
                    log.error("Failed to send FAILED notification status to client for Job ID: {}", event.jobId(), notifyEx);
                }
            }
        } finally {
            TenantContext.clear();
        }

        log.info("Completed processing Ai Parsing Job ID: {}", jobId);

        // CompletableFuture.runAsync(() -> {

        // }, taskExecutor);
    }

}
