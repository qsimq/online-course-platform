package kz.iitu.onlinecourseplatform.controller;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaFile;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class AsimaZhorabayevaFileController {

    private final AsimaZhorabayevaFileRepository fileRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file) throws IOException {

        Files.createDirectories(Paths.get(uploadDir));
        String stored = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path target = Paths.get(uploadDir + stored);
        Files.copy(file.getInputStream(), target,
                StandardCopyOption.REPLACE_EXISTING);

        AsimaZhorabayevaFile saved = fileRepository.save(
                AsimaZhorabayevaFile.builder()
                        .originalName(file.getOriginalFilename())
                        .storedName(stored)
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .path(target.toString())
                        .build());

        log.info("File uploaded: id={} name={}", saved.getId(), saved.getOriginalName());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
        AsimaZhorabayevaFile f = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found: " + id));

        Path path = Paths.get(f.getPath());
        Resource resource = new UrlResource(path.toUri());

        log.info("File downloaded: id={} name={}", id, f.getOriginalName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(f.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + f.getOriginalName() + "\"")
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(fileRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        AsimaZhorabayevaFile f = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found: " + id));
        Files.deleteIfExists(Paths.get(f.getPath()));
        fileRepository.deleteById(id);
        log.info("File deleted: id={}", id);
        return ResponseEntity.noContent().build();
    }
}