package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.service.interf.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceMinIOImpl implements FileService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    public FileServiceMinIOImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void store(MultipartFile file, String fileName) {
        if (file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to store empty file.");
        List<String> filenameTokens = List.of(Objects.requireNonNull(file.getOriginalFilename()).split("\\."));
        if (!filenameTokens.get(filenameTokens.size() - 1).equals("pdf")) throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "PDF file type is required.");

        try(InputStream inputStream = file.getInputStream()) {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName + ".pdf")
                    .headers(Collections.singletonMap("Content-Disposition", "attachment; filename=\"" + file.getOriginalFilename() + "\""))
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error while storing file in Minio.");
        }

    }

}