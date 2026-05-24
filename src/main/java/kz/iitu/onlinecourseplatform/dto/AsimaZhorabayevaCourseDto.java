package kz.iitu.onlinecourseplatform.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AsimaZhorabayevaCourseDto {

    private Long id;

    @NotBlank(message = "Название курса обязательно")
    @Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
    private String title;

    @Size(max = 2000, message = "Описание не более 2000 символов")
    private String description;

    @PositiveOrZero(message = "Цена не может быть отрицательной")
    private BigDecimal price;

    private String thumbnailUrl;
    private String instructorName;
    private Integer lessonsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public AsimaZhorabayevaCourseDto() {}

    public AsimaZhorabayevaCourseDto(Long id, String title, String description, BigDecimal price,
                                     String thumbnailUrl, String instructorName, Integer lessonsCount,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.instructorName = instructorName;
        this.lessonsCount = lessonsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public Integer getLessonsCount() { return lessonsCount; }
    public void setLessonsCount(Integer lessonsCount) { this.lessonsCount = lessonsCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}