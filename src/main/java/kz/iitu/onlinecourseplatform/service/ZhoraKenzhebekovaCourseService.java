package kz.iitu.onlinecourseplatform.service;

import kz.iitu.onlinecourseplatform.dto.ZhoraKenzhebekovaCourseDto;
import kz.iitu.onlinecourseplatform.entity.ZhoraCourse;
import kz.iitu.onlinecourseplatform.repository.ZhoraCourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZhoraKenzhebekovaCourseService {

    private final ZhoraCourseRepository courseRepository;

    public Page<ZhoraKenzhebekovaCourseDto> searchCourses(String title, BigDecimal minPrice,
                                                          BigDecimal maxPrice, int page, int size,
                                                          String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<ZhoraCourse> courses = courseRepository.searchCourses(title, minPrice, maxPrice, pageable);

        return courses.map(this::convertToDto);
    }

    public ZhoraKenzhebekovaCourseDto findById(Long id) {
        ZhoraCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс с ID " + id + " не найден"));
        return convertToDto(course);
    }

    @Transactional
    public ZhoraKenzhebekovaCourseDto create(ZhoraKenzhebekovaCourseDto dto) {
        ZhoraCourse course = new ZhoraCourse();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setThumbnailUrl(dto.getThumbnailUrl());

        ZhoraCourse saved = courseRepository.save(course);
        log.info("Создан новый курс: {}", saved.getTitle());
        return convertToDto(saved);
    }

    @Transactional
    public ZhoraKenzhebekovaCourseDto update(Long id, ZhoraKenzhebekovaCourseDto dto) {
        ZhoraCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setThumbnailUrl(dto.getThumbnailUrl());

        ZhoraCourse updated = courseRepository.save(course);
        log.info("Обновлен курс: {}", updated.getTitle());
        return convertToDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
        log.info("Удален курс с ID: {}", id);
    }

    private ZhoraKenzhebekovaCourseDto convertToDto(ZhoraCourse course) {
        ZhoraKenzhebekovaCourseDto dto = new ZhoraKenzhebekovaCourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setThumbnailUrl(course.getThumbnailUrl());
        if (course.getInstructor() != null) {
            dto.setInstructorName(course.getInstructor().getFullName());
        }
        dto.setLessonsCount(course.getLessons() != null ? course.getLessons().size() : 0);
        return dto;
    }
}