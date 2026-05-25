package kz.iitu.onlinecourseplatform.mapper;

import kz.iitu.onlinecourseplatform.dto.AsimaZhorabayevaCourseDto;
import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AsimaZhorabayevaCourseMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "lessonCount", expression = "java(course.getLessons() == null ? 0 : course.getLessons().size())")
    AsimaZhorabayevaCourseDto toDto(AsimaZhorabayevaCourse course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    AsimaZhorabayevaCourse toEntity(AsimaZhorabayevaCourseDto dto);
}