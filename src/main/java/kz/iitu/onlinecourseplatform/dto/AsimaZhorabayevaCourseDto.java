package kz.iitu.onlinecourseplatform.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsimaZhorabayevaCourseDto {

    private Long id;
    private String title;
    private String description;
    private String instructor;
    private Double price;
    private String thumbnailUrl;
    private String status;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int lessonCount;
}