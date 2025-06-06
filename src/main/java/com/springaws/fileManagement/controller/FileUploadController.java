package com.springaws.fileManagement.controller;


import com.springaws.fileManagement.model.ServiceRequest;
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
    //public ResponseEntity<String> uploadFile(@RequestParam String fileName, @RequestParam String content) {
    //public ResponseEntity<String> uploadFile(@RequestBody ServiceRequest request) {
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        //@RequestParam("file") MultipartFile file
       /* String fileName = request.getFileName();
        String content = request.getFileName();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        fileName = fileName+"_"+formattedDateTime;*/

        try {
            String fileName = s3Service.uploadFile(file);
            return ResponseEntity.ok("File uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}

