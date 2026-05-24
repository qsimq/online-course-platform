package kz.iitu.onlinecourseplatform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsimaZhorabayevaEmailService {

    @Async
    public CompletableFuture<Boolean> sendWelcomeEmail(String email, String name) {
        log.info("Асинхронная отправка email на: {}", email);
        try {
            Thread.sleep(2000);
            log.info("Email успешно отправлен на {}", email);
            return CompletableFuture.completedFuture(true);
        } catch (InterruptedException e) {
            log.error("Ошибка отправки email: {}", e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async
    public CompletableFuture<Void> sendCourseNotification(String courseTitle, String studentEmail) {
        log.info("Асинхронное уведомление о курсе {} для {}", courseTitle, studentEmail);
        try {
            Thread.sleep(1500);
            log.info("Уведомление отправлено");
            return CompletableFuture.completedFuture(null);
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(null);
        }
    }
}
