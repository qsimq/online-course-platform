package kz.iitu.onlinecourseplatform.service;

import kz.iitu.onlinecourseplatform.dto.AsimaZhorabayevaCourseDto;
import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaCourse;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsimaZhorabayevaCourseService {

    @Autowired
    private AsimaZhorabayevaCourseRepository courseRepository;

    // Поиск курсов с пагинацией, сортировкой и фильтрацией
    public Page<AsimaZhorabayevaCourseDto> searchCourses(
            String title,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sortBy,
            String direction) {

        log.info("Поиск курсов: title={}, minPrice={}, maxPrice={}, page={}, size={}",
                title, minPrice, maxPrice, page, size);

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<AsimaZhorabayevaCourse> courses = courseRepository.searchCourses(
                title, minPrice, maxPrice, pageable);

        return courses.map(this::convertToDto);
    }

    // Получить курс по ID
    public AsimaZhorabayevaCourseDto findById(Long id) {
        log.info("Поиск курса по ID: {}", id);
        AsimaZhorabayevaCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс с ID " + id + " не найден"));
        return convertToDto(course);
    }

    // Создать новый курс
    @Transactional
    public AsimaZhorabayevaCourseDto create(AsimaZhorabayevaCourseDto dto) {
        log.info("Создание нового курса: {}", dto.getTitle());

        AsimaZhorabayevaCourse course = new AsimaZhorabayevaCourse();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setThumbnailUrl(dto.getThumbnailUrl());

        AsimaZhorabayevaCourse saved = courseRepository.save(course);
        log.info("Курс успешно создан с ID: {}", saved.getId());

        return convertToDto(saved);
    }

    // Обновить курс
    @Transactional
    public AsimaZhorabayevaCourseDto update(Long id, AsimaZhorabayevaCourseDto dto) {
        log.info("Обновление курса с ID: {}", id);

        AsimaZhorabayevaCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setThumbnailUrl(dto.getThumbnailUrl());

        AsimaZhorabayevaCourse updated = courseRepository.save(course);
        log.info("Курс с ID {} успешно обновлен", id);

        return convertToDto(updated);
    }

    // Удалить курс
    @Transactional
    public void delete(Long id) {
        log.info("Удаление курса с ID: {}", id);

        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Курс с ID " + id + " не найден");
        }
        courseRepository.deleteById(id);
        log.info("Курс с ID {} успешно удален", id);
    }

    // Асинхронная генерация сертификата
    @Async
    public CompletableFuture<String> generateCertificateAsync(Long courseId, String studentEmail) {
        log.info("Асинхронная генерация сертификата для курса {} и студента {}", courseId, studentEmail);

        return CompletableFuture.supplyAsync(() -> {
            try {
                // Симуляция долгого процесса
                Thread.sleep(3000);
                String certificateUrl = "/certificates/course_" + courseId + "_student_" + studentEmail + ".pdf";
                log.info("Сертификат сгенерирован: {}", certificateUrl);
                return certificateUrl;
            } catch (InterruptedException e) {
                log.error("Ошибка генерации сертификата", e);
                return "Ошибка генерации";
            }
        });
    }

    // Получить все курсы (простой метод)
    public Page<AsimaZhorabayevaCourseDto> getAllCourses(Pageable pageable) {
        log.info("Получение всех курсов с пагинацией");
        Page<AsimaZhorabayevaCourse> courses = courseRepository.findAll(pageable);
        return courses.map(this::convertToDto);
    }

    // Конвертация Entity в DTO
    private AsimaZhorabayevaCourseDto convertToDto(AsimaZhorabayevaCourse course) {
        AsimaZhorabayevaCourseDto dto = new AsimaZhorabayevaCourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setThumbnailUrl(course.getThumbnailUrl());

        if (course.getInstructor() != null) {
            dto.setInstructorName(course.getInstructor().getFullName());
        }

        if (course.getLessons() != null) {
            dto.setLessonsCount(course.getLessons().size());
        }

        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());

        return dto;
    }
}
