package kz.iitu.onlinecourseplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZhoraAuthRequest {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;
}