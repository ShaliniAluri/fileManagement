package com.springaws.fileManagement.controller;


import com.springaws.fileManagement.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final S3Service s3Service;

    public FileUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String fileName, @RequestParam String content) {
        //@RequestParam("file") MultipartFile file
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        fileName = fileName+"_"+formattedDateTime;

        try {
            fileName = s3Service.uploadFile(fileName, content);
            return ResponseEntity.ok("File uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}

