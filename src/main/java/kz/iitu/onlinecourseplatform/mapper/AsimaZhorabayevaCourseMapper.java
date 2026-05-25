package kz.iitu.onlinecourseplatform;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsimaZhorabayevaCourseDto {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @NotBlank(message = "Instructor is required")
    private String instructor;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    private String thumbnailUrl;

    private String status;

    private String categoryName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int lessonCount;
}
