package ru.bekhterev.studentservice.service;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinioService {

    void saveFile(MultipartFile multipartFile) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    String getFileUrl(String filename) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
