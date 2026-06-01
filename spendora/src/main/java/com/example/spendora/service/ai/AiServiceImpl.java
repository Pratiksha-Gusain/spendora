package com.example.spendora.service.ai;

import com.example.spendora.event.AiParsingTaskCompleted;
import com.example.spendora.event.AiParsingTaskCreated;
import com.example.spendora.mapper.AiParseTaskMapper;
import com.example.spendora.model.AiParsingTask;
import com.example.spendora.dto.AiInputDto;
import com.example.spendora.dto.AiTaskDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.mapper.TransactionMapper;
import com.example.spendora.model.AppUser;
import com.example.spendora.model.Status;
import com.example.spendora.service.ai.parsetask.AiParseTaskService;
import com.example.spendora.service.category.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.example.spendora.model.Category;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiServiceImpl implements AiService {

    private final AtomicInteger requestCounter;
    private final TransactionMapper transactionMapper;
    private final AiParseTaskService aiParseTaskService;
    private final ApplicationEventPublisher eventPublisher;
    private final CategoryService categoryService;
    private final AiParseTaskMapper aiParseTaskMapper;
    private final ObjectMapper mapper;

    private static final String SYSTEM_PROMPT = """
            Rules:
            1. Your job is to parse the raw text from the user which is either related to expense or income.
                  
            2. Based on the type of text decide the 'type' field of output json. Allowed values for 'type' fields are EXPENSE or INCOME.
                  
            3. Field 'transactionDate' is having date format: dd-mm-yyyy
                  
            4. If the user uses relative dates (e.g., 'today', 'yesterday'). Get the date from following:
                                - Current Year for reference: {year}
                                - If Today then use {today}
                                - If Yesterday then use {yesterday}
                                - Day before yesterday then use {dayBeforeYesterday}
                                - If no date then use {today}
                                - If date mentioned in raw text then pick that date.              
            5.Infer the gategory of responses from the list: {categories}
            6. Extract description from raw text and don't change or add anything to it.
                  
            7. Extract amount from raw text and don't change or add anything to it. Just convert the string to double representation.
                  
            8. Don't answer anything not related to expense or income related raw text. Simply set the 'errorMessage' field of the output json with "NOT_VALID_INPUT"
                  
            9. Raw Text is in English or Hindi language only.
            """;
    private final ChatClient chatClient;

    @Override
    public TransactionRequestDto parse(AiInputDto requestBody) {
       throw new UnsupportedOperationException();
    }
    @Override
    public AiTaskDto save(String appUserId, AiInputDto requestBody) {

        final var aiParsingTask = AiParsingTask.builder()
                .appUser(AppUser.ofId(appUserId))
                .rawInput(requestBody.rawText())
                .status(Status.PENDING)
                .build();
        final var saved = aiParseTaskService.save(aiParsingTask);

        eventPublisher.publishEvent(new AiParsingTaskCreated(aiParsingTask.getId()));
        return aiParseTaskMapper.toDto(saved, "Ai Task Saved!");

}

    @Override
    public void parse(AiParsingTask task) throws JsonProcessingException {
        final var cCount = requestCounter.incrementAndGet();

        log.info("START - parse | Request Counter: {}", cCount);

        final var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final var sysPromptVars = new HashMap<String,Object>();
        final var now = LocalDateTime.now();
        final var today = formatter.format(now);
        final var yesterday = formatter.format(now.minusDays(1));
        final var dayBeforeYesterday = formatter.format(now.minusDays(2));

        final var categories = categoryService.getAllWithoutUserId()
                .stream().map(Category::getName)
                .collect(Collectors.joining(","));


        sysPromptVars.put("today", today);
        sysPromptVars.put("yesterday", yesterday);
        sysPromptVars.put("dayBeforeYesterday", dayBeforeYesterday);
        sysPromptVars.put("year", now.getYear()) ;

        final var sysPrompt = SystemPromptTemplate.builder()
                .template(SYSTEM_PROMPT)
                .variables(sysPromptVars)
                .build();

        final var aiParseresult =  chatClient.prompt(task.getRawInput())
                .system(sysPrompt.render())
                .call()
                .entity(AiParseResult.class);


        task.setStatus(Status.COMPLETED);
        task.setContent(mapper.writeValueAsString(aiParseresult));
        aiParseTaskService.save(task);
        eventPublisher.publishEvent(new AiParsingTaskCompleted(task.getId(), task));

        log.info("END - parse | Request Counter: {}", cCount);
    }
}
