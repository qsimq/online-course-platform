package kz.iitu.onlinecourseplatform.controller;

import jakarta.validation.Valid;
import kz.iitu.onlinecourseplatform.dto.ZhoraKenzhebekovaCourseDto;
import kz.iitu.onlinecourseplatform.service.ZhoraKenzhebekovaCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class ZhoraKenzhebekovaCourseController {

    private final ZhoraKenzhebekovaCourseService courseService;

    @GetMapping
    public ResponseEntity<Page<ZhoraKenzhebekovaCourseDto>> getAllCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        log.info("GET /api/courses - поиск курсов");

        Page<ZhoraKenzhebekovaCourseDto> courses = courseService.searchCourses(
                title, minPrice, maxPrice, page, size, sortBy, direction);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZhoraKenzhebekovaCourseDto> getCourse(@PathVariable Long id) {
        log.info("GET /api/courses/{} - получение курса", id);
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ZhoraKenzhebekovaCourseDto> createCourse(
            @Valid @RequestBody ZhoraKenzhebekovaCourseDto courseDto) {
        log.info("POST /api/courses - создание курса: {}", courseDto.getTitle());
        return ResponseEntity.ok(courseService.create(courseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZhoraKenzhebekovaCourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody ZhoraKenzhebekovaCourseDto courseDto) {
        log.info("PUT /api/courses/{} - обновление курса", id);
        return ResponseEntity.ok(courseService.update(id, courseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.info("DELETE /api/courses/{} - удаление курса", id);
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
