package kz.iitu.onlinecourseplatform.controller;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaUser;
import kz.iitu.onlinecourseplatform.security.AsimaZhorabayevaJwtUtil;
import kz.iitu.onlinecourseplatform.service.AsimaZhorabayevaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AsimaZhorabayevaAuthController {

    @Autowired
    private AsimaZhorabayevaUserService userService;

    @Autowired
    private AsimaZhorabayevaJwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String fullName = request.get("fullName");

            // Проверка существует ли пользователь
            if (userService.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email уже существует"));
            }

            // Создание пользователя
            AsimaZhorabayevaUser user = new AsimaZhorabayevaUser();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setRole(AsimaZhorabayevaUser.Role.STUDENT);

            AsimaZhorabayevaUser savedUser = userService.register(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Пользователь успешно зарегистрирован");
            response.put("email", savedUser.getEmail());
            response.put("fullName", savedUser.getFullName());
            response.put("id", savedUser.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            AsimaZhorabayevaUser user = userService.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Неверный email или пароль"));
            }

            // Простая проверка пароля (без шифрования для теста)
            if (!user.getPassword().equals(password)) {
                return ResponseEntity.status(401).body(Map.of("error", "Неверный email или пароль"));
            }

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "type", "Bearer",
                    "email", email,
                    "role", user.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}