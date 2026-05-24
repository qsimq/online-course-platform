package kz.iitu.onlinecourseplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import kz.iitu.onlinecourseplatform.entity.ZhoraUser;

@Data
public class ZhoraRegisterRequest {

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @NotBlank(message = "Имя обязательно")
    private String fullName;

    // Используем полное имя ZhoraUser.Role
    private ZhoraUser.Role role = ZhoraUser.Role.STUDENT;
}