package com.example.spendora.controller;

import com.example.spendora.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/subscribe")
    public ResponseEntity<SseEmitter> connect(
            @RequestParam("sessionId") String sessionId,
            @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(notificationService.openConnection(userId, sessionId));
    }
}
