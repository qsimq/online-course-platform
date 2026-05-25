package kz.iitu.onlinecourseplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "Проверка состояния сервера")
public class AsimaZhorabayevaHealthController {

    @GetMapping
    @Operation(summary = "Проверка здоровья приложения")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Application is running");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return response;
    }
}