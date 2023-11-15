package ru.bekhterev.studentservice.service.impl;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import ru.bekhterev.studentservice.service.MinioService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Transactional
    @Override
    public void saveFile(MultipartFile multipartFile) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(multipartFile.getOriginalFilename())
                .stream(multipartFile.getInputStream(),
                        multipartFile.getSize(), DataSize.ofMegabytes(5).toBytes())
                .build());
        log.info("File with name '{}' saved to minio", objectWriteResponse.object());
    }

    @Override
    public String getFileUrl(String filename) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(filename)
                        .expiry(15, TimeUnit.MINUTES)
                        .build());
    }
}
