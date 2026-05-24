package kz.iitu.onlinecourseplatform.service;

import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsimaZhorabayevaReportService {

    private final AsimaZhorabayevaEnrollmentRepository enrollmentRepository;

    @Async("taskExecutor")
    public CompletableFuture<String> generateCourseReport(Long courseId) {
        log.info("[ASYNC-REPORT] Starting report for course {}", courseId);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                long count = enrollmentRepository.countByCourseId(courseId);
                String report = String.format(
                        "Course %d: total enrollments = %d", courseId, count);
                log.info("[ASYNC-REPORT] Done: {}", report);
                return report;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Report failed";
            }
        });
    }
}