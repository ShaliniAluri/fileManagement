package com.springaws.fileManagement.controller;

import com.springaws.fileManagement.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/getfiles")
public class FileRetrievalController {


    private final S3Service s3Service;
    public FileRetrievalController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/getFileName")
    public ResponseEntity<List<String>> uploadFile(@RequestParam(defaultValue = "1h") String time) {
        Duration duration = parseDuration(time);
        try {
            List<String> fileName = s3Service.getFileName(duration);
            return ResponseEntity.ok(Collections.singletonList("File retrieved: " + fileName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonList("Retrieval failed: " + e.getMessage()));
        }
    }

    private Duration parseDuration(String input) {
        if (input.endsWith("h")) {
            return Duration.ofHours(Long.parseLong(input.replace("h", "")));
        } else if (input.endsWith("d")) {
            return Duration.ofDays(Long.parseLong(input.replace("d", "")));
        } else {
            throw new IllegalArgumentException("Unsupported duration format. Use '1h', '2d', etc.");
        }
    }

}
