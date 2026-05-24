package kz.iitu.onlinecourseplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaUser;

@Data
public class AsimaZhorabayevaRegisterRequest {

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @NotBlank(message = "Имя обязательно")
    private String fullName;

    // Используем полное имя AsimaZhorabayevaUser.Role
    private AsimaZhorabayevaUser.Role role = AsimaZhorabayevaUser.Role.STUDENT;
}