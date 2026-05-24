package kz.iitu.onlinecourseplatform.controller;

import kz.iitu.onlinecourseplatform.dto.AsimaZhorabayevaCourseDto;
import kz.iitu.onlinecourseplatform.service.AsimaZhorabayevaCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class AsimaZhorabayevaCourseController {

    private final AsimaZhorabayevaCourseService courseService;

    // GET все курсы — пагинация + поиск + фильтр + сортировка
    @GetMapping
    public ResponseEntity<Page<AsimaZhorabayevaCourseDto>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("GET /api/courses search={} status={} page={}", search, status, page);
        return ResponseEntity.ok(
                courseService.getAll(search, status, page, size, sortBy, sortDir));
    }

    // GET по ID — Path параметр
    @GetMapping("/{id}")
    public ResponseEntity<AsimaZhorabayevaCourseDto> getById(@PathVariable Long id) {
        log.info("GET /api/courses/{}", id);
        return ResponseEntity.ok(courseService.getById(id));
    }

    // POST создать
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsimaZhorabayevaCourseDto> create(
            @Valid @RequestBody AsimaZhorabayevaCourseDto dto) {
        log.info("POST /api/courses title={}", dto.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.create(dto));
    }

    // PUT обновить
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsimaZhorabayevaCourseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody AsimaZhorabayevaCourseDto dto) {
        log.info("PUT /api/courses/{}", id);
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    // DELETE удалить
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/courses/{}", id);
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
