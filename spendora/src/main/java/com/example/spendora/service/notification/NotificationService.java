package com.example.spendora.service.notification;

import com.example.spendora.dto.JobStatusDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    void send(JobStatusDto jobStatus);

    void openConnection(String string);

    SseEmitter get(String jobId);

    void closeConnection(String jobId);
}
