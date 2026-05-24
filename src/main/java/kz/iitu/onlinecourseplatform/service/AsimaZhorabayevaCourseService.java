package kz.iitu.onlinecourseplatform.service;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaCourse;
import kz.iitu.onlinecourseplatform.dto.AsimaZhorabayevaCourseDto;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaCourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AsimaZhorabayevaCourseService {

    private final AsimaZhorabayevaCourseRepository courseRepository;

    public AsimaZhorabayevaCourseDto create(AsimaZhorabayevaCourseDto dto) {
        log.info("Создание нового курса: {}", dto.getTitle());

        AsimaZhorabayevaCourse course = new AsimaZhorabayevaCourse();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setThumbnailUrl(dto.getThumbnailUrl());
        course.setInstructor(dto.getInstructor());
        course.setStatus(AsimaZhorabayevaCourse.Status.DRAFT);

        AsimaZhorabayevaCourse saved = courseRepository.save(course);
        log.info("Курс создан с id: {}", saved.getId());
        return toDto(saved);
    }

    public AsimaZhorabayevaCourseDto getById(Long id) {
        AsimaZhorabayevaCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс не найден: " + id));
        return toDto(course);
    }

    public Page<AsimaZhorabayevaCourseDto> getAll(
            String search,
            String status,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AsimaZhorabayevaCourse> courses;
        if (search != null && !search.isEmpty() && status != null && !status.isEmpty()) {
            courses = courseRepository
                    .findByTitleContainingIgnoreCaseAndStatus(
                            search,
                            AsimaZhorabayevaCourse.Status.valueOf(status),
                            pageable);
        } else if (search != null && !search.isEmpty()) {
            courses = courseRepository
                    .findByTitleContainingIgnoreCase(search, pageable);
        } else if (status != null && !status.isEmpty()) {
            courses = courseRepository
                    .findByStatus(AsimaZhorabayevaCourse.Status.valueOf(status), pageable);
        } else {
            courses = courseRepository.findAll(pageable);
        }
        return courses.map(this::toDto);
    }

    public AsimaZhorabayevaCourseDto update(Long id, AsimaZhorabayevaCourseDto dto) {
        AsimaZhorabayevaCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс не найден: " + id));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setThumbnailUrl(dto.getThumbnailUrl());
        course.setInstructor(dto.getInstructor());

        return toDto(courseRepository.save(course));
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Курс не найден: " + id);
        }
        courseRepository.deleteById(id);
        log.info("Курс удалён: {}", id);
    }

    private AsimaZhorabayevaCourseDto toDto(AsimaZhorabayevaCourse course) {
        AsimaZhorabayevaCourseDto dto = new AsimaZhorabayevaCourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setThumbnailUrl(course.getThumbnailUrl());
        dto.setInstructor(course.getInstructor());
        dto.setStatus(course.getStatus() != null ? course.getStatus().name() : null);
        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());
        dto.setLessonCount(course.getLessons() != null ? course.getLessons().size() : 0);
        if (course.getCategory() != null) {
            dto.setCategoryName(course.getCategory().getName());
        }
        return dto;
    }
}
