package kz.iitu.onlinecourseplatform.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ZhoraKenzhebekovaCourseDto {

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
}