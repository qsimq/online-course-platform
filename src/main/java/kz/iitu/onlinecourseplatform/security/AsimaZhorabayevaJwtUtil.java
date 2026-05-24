package kz.iitu.onlinecourseplatform.security;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class AsimaZhorabayevaJwtUtil {

    public String generateToken(String email) {
        // Простой токен для теста
        return UUID.randomUUID().toString() + "-" + email;
    }
}