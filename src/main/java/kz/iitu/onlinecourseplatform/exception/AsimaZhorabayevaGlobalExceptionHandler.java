package kz.iitu.onlinecourseplatform.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AsimaZhorabayevaGlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        log.error("Error: {}", ex.getMessage());
        int status = ex.getMessage().contains("не найден") ? 404 : 400;
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Validation: {}", message);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        log.error("Unexpected: ", ex);
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Internal server error"));
    }

    public record ErrorResponse(int status, String message) {
        public LocalDateTime timestamp() { return LocalDateTime.now(); }
    }
}