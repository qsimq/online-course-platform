package kz.iitu.onlinecourseplatform.controller;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaEnrollment;
import kz.iitu.onlinecourseplatform.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Slf4j
public class AsimaZhorabayevaEnrollmentController {

    private final AsimaZhorabayevaEnrollmentRepository enrollmentRepository;
    private final AsimaZhorabayevaUserRepository userRepository;
    private final AsimaZhorabayevaCourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<AsimaZhorabayevaEnrollment>> getAll() {
        return ResponseEntity.ok(enrollmentRepository.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AsimaZhorabayevaEnrollment>> getByUser(
            @PathVariable Long userId) {
        log.info("GET enrollments for user {}", userId);
        return ResponseEntity.ok(enrollmentRepository.findByUserId(userId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AsimaZhorabayevaEnrollment>> getByCourse(
            @PathVariable Long courseId) {
        log.info("GET enrollments for course {}", courseId);
        return ResponseEntity.ok(enrollmentRepository.findByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<?> enroll(
            @RequestParam Long userId,
            @RequestParam Long courseId) {

        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            return ResponseEntity.badRequest()
                    .body("User already enrolled in this course");
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        AsimaZhorabayevaEnrollment enrollment = AsimaZhorabayevaEnrollment.builder()
                .user(user)
                .course(course)
                .build();

        log.info("POST enroll user={} course={}", userId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentRepository.save(enrollment));
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<?> updateProgress(
            @PathVariable Long id,
            @RequestParam Integer progress) {

        var enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found: " + id));
        enrollment.setProgress(progress);
        if (progress >= 100) {
            enrollment.setStatus(AsimaZhorabayevaEnrollment.EnrollmentStatus.COMPLETED);
        }
        log.info("PUT enrollment {} progress={}", id, progress);
        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentRepository.deleteById(id);
        log.info("DELETE enrollment {}", id);
        return ResponseEntity.noContent().build();
    }
}