package com.example.spendora.controller;

import com.example.spendora.dto.AiActiveTaskDto;
import com.example.spendora.dto.AiInputDto;
import com.example.spendora.dto.AiTaskDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.service.ai.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai-input")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping
    public ResponseEntity<AiTaskDto> parseRawText(@RequestBody AiInputDto requestBody,
                                                  @AuthenticationPrincipal String userId) {
        final var response = aiService.save(userId, requestBody);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/active")
    public ResponseEntity<List<AiActiveTaskDto>> getActiveTasks(@AuthenticationPrincipal String userId) {
        final var activeTasks = aiService.getActiveTasks(userId);
        return ResponseEntity.ok(activeTasks);
    }
}
