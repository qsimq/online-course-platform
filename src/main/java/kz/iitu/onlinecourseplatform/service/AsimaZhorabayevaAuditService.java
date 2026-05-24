package kz.iitu.onlinecourseplatform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsimaZhorabayevaAuditService {

    @Async("taskExecutor")
    public CompletableFuture<Void> logAction(String username,
                                             String action,
                                             String details) {
        return CompletableFuture.runAsync(() ->
                log.info("[ASYNC-AUDIT] user={} action={} details={} time={}",
                        username, action, details, LocalDateTime.now())
        );
    }
}