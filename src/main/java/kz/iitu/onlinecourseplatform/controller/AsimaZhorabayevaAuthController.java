package kz.iitu.onlinecourseplatform.controller;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaUser;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaUserRepository;
import kz.iitu.onlinecourseplatform.security.AsimaZhorabayevaJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AsimaZhorabayevaAuthController {

    private final AsimaZhorabayevaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AsimaZhorabayevaJwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String email    = request.get("email");
        String password = request.get("password");

        if (username == null || email == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "username, email and password are required"));
        }
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username already taken"));
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email already registered"));
        }

        AsimaZhorabayevaUser user = AsimaZhorabayevaUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(AsimaZhorabayevaUser.Role.USER)
                .build();

        userRepository.save(user);
        log.info("New user registered: {}", username);

        String token = jwtUtil.generateToken(username,
                AsimaZhorabayevaUser.Role.USER.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "token", token,
                "username", username,
                "role", "USER",
                "message", "Registration successful"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        AsimaZhorabayevaUser user = userRepository
                .findByUsername(username).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }

        log.info("User logged in: {}", username);
        String token = jwtUtil.generateToken(username, user.getRole().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username,
                "role", user.getRole().name(),
                "message", "Login successful"
        ));
    }
}
