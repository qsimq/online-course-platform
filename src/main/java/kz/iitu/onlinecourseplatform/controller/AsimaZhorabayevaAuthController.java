package kz.iitu.onlinecourseplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth Controller", description = "API для аутентификации")
public class AsimaZhorabayevaAuthController {

    // Временное хранилище пользователей
    private final Map<String, Map<String, String>> users = new HashMap<>();

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");

        log.info("POST /api/auth/register - username: {}", username);

        if (username == null || email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "username, email and password are required"));
        }

        if (users.containsKey(username)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already taken"));
        }

        Map<String, String> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);
        user.put("role", "USER");
        users.put(username, user);

        String token = generateSimpleToken(username);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username,
                "role", "USER",
                "message", "Registration successful"
        ));
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        log.info("POST /api/auth/login - username: {}", username);

        Map<String, String> user = users.get(username);

        if (user == null || !user.get("password").equals(password)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = generateSimpleToken(username);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username,
                "role", user.get("role"),
                "message", "Login successful"
        ));
    }

    private String generateSimpleToken(String username) {
        return "token_" + username + "_" + System.currentTimeMillis();
    }
}