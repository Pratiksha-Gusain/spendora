package com.example.spendora.service.notification;

import com.example.spendora.dto.JobStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
   private static final int TIMEOUT_IN_MINUTES = 5;
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    @Override
    public void send(JobStatusDto jobStatus) {
        final var emitter = this.get(jobStatus.jobId());
        try {
            emitter.send(jobStatus);
        } catch (IOException e) {
            throw new RuntimeException("Unable to send job status to emitter for jobId: " + jobStatus.jobId(), e);
        }

    }

    @Override
    public void openConnection(String string) {
        final var emitter = new SseEmitter(Duration.ofMinutes(TIMEOUT_IN_MINUTES).toMillis());
    }

    @Override
    public void closeConnection(String jobId) {
        this.get(jobId).complete();
    }

    @Override
    public SseEmitter get(String jobId) {
        final var emitter = emitters.get(jobId);
        if(Objects.isNull(emitter)){
            throw new RuntimeException("No emitter found for jobId: "+jobId);
        }
        return null;
    }
}
