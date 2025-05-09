package com.springaws.fileManagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    //public String uploadFile(MultipartFile file) throws IOException {
    public String uploadFile(String filename, String content) throws IOException {

       /* String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        */

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(filename)
                        .contentType("text/plain")
                        .build(),
                RequestBody.fromString(content)
        );
        return filename;
    }

    public List<String> getFileName(Duration duration) {
        List<String> fileNames = new ArrayList<>();

        Instant cutoffTime = Instant.now().minus(duration);

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .filter(obj -> obj.lastModified().isAfter(cutoffTime))
                .map(S3Object::key)
                .collect(Collectors.toList());



    }
}
