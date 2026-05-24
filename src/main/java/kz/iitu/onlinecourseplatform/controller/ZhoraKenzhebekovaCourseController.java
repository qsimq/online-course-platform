package kz.iitu.onlinecourseplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Управление курсами", description = "API для работы с курсами")
public class ZhoraKenzhebekovaCourseController {

    private final ZhoraKenzhebekovaCourseService courseService;

    @GetMapping
    @Operation(summary = "Получить все курсы", description = "Поддерживает пагинацию, сортировку, поиск и фильтрацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры")
    })
    public ResponseEntity<Page<ZhoraKenzhebekovaCourseDto>> getAllCourses(
            @Parameter(description = "Название курса для поиска")
            @RequestParam(required = false) String title,

            @Parameter(description = "Минимальная цена")
            @RequestParam(required = false) BigDecimal minPrice,

            @Parameter(description = "Максимальная цена")
            @RequestParam(required = false) BigDecimal maxPrice,

            @Parameter(description = "Номер страницы (начиная с 0)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Размер страницы")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Поле для сортировки (id, title, price)")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Направление сортировки (asc или desc)")
            @RequestParam(defaultValue = "asc") String direction) {

        log.info("GET /api/courses - поиск: title={}, minPrice={}, maxPrice={}, page={}, size={}",
                title, minPrice, maxPrice, page, size);

        Page<ZhoraKenzhebekovaCourseDto> courses = courseService.searchCourses(
                title, minPrice, maxPrice, page, size, sortBy, direction);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить курс по ID")
    public ResponseEntity<ZhoraKenzhebekovaCourseDto> getCourse(
            @Parameter(description = "ID курса") @PathVariable Long id) {
        log.info("GET /api/courses/{} - получение курса", id);
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новый курс")
    public ResponseEntity<ZhoraKenzhebekovaCourseDto> createCourse(
            @Valid @RequestBody ZhoraKenzhebekovaCourseDto courseDto) {
        log.info("POST /api/courses - создание курса: {}", courseDto.getTitle());
        return ResponseEntity.ok(courseService.create(courseDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить курс")
    public ResponseEntity<ZhoraKenzhebekovaCourseDto> updateCourse(
            @Parameter(description = "ID курса") @PathVariable Long id,
            @Valid @RequestBody ZhoraKenzhebekovaCourseDto courseDto) {
        log.info("PUT /api/courses/{} - обновление курса", id);
        return ResponseEntity.ok(courseService.update(id, courseDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить курс")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "ID курса") @PathVariable Long id) {
        log.info("DELETE /api/courses/{} - удаление курса", id);
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
