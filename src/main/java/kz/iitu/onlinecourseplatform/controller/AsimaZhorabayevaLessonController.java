package kz.iitu.onlinecourseplatform.controller;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaLesson;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaLessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Slf4j
public class AsimaZhorabayevaLessonController {

    private final AsimaZhorabayevaLessonRepository lessonRepository;

    @GetMapping
    public ResponseEntity<List<AsimaZhorabayevaLesson>> getAll() {
        return ResponseEntity.ok(lessonRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsimaZhorabayevaLesson> getById(@PathVariable Long id) {
        return lessonRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AsimaZhorabayevaLesson>> getByCourse(
            @PathVariable Long courseId) {
        log.info("GET lessons for course {}", courseId);
        return ResponseEntity.ok(
                lessonRepository.findByCourseIdOrderByOrderIndex(courseId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsimaZhorabayevaLesson> create(
            @RequestBody AsimaZhorabayevaLesson lesson) {
        log.info("POST lesson: {}", lesson.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonRepository.save(lesson));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsimaZhorabayevaLesson> update(
            @PathVariable Long id,
            @RequestBody AsimaZhorabayevaLesson lesson) {
        return lessonRepository.findById(id).map(existing -> {
            existing.setTitle(lesson.getTitle());
            existing.setContent(lesson.getContent());
            existing.setOrderIndex(lesson.getOrderIndex());
            existing.setDurationMinutes(lesson.getDurationMinutes());
            log.info("PUT lesson {}", id);
            return ResponseEntity.ok(lessonRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lessonRepository.deleteById(id);
        log.info("DELETE lesson {}", id);
        return ResponseEntity.noContent().build();
    }
}