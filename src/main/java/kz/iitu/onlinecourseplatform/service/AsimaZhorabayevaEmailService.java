package kz.iitu.onlinecourseplatform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsimaZhorabayevaEmailService {

    @Async("taskExecutor")
    public CompletableFuture<Void> sendWelcomeEmail(String email, String username) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("[ASYNC-EMAIL] Sending welcome email to {} for {}", email, username);
                Thread.sleep(1000);
                log.info("[ASYNC-EMAIL] Email sent successfully to {}", email);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("[ASYNC-EMAIL] Failed to send email to {}", email);
            }
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> sendEnrollmentConfirmation(String email, String courseTitle) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("[ASYNC-EMAIL] Sending enrollment confirmation to {} for course '{}'",
                        email, courseTitle);
                Thread.sleep(800);
                log.info("[ASYNC-EMAIL] Enrollment email sent to {}", email);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
