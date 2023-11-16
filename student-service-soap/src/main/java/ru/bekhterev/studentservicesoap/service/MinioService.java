package ru.bekhterev.studentservicesoap.service;

import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinioService {

//    void saveFile(MultipartFile multipartFile) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    String getFileUrl(String filename) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
