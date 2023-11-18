package ru.bekhterev.studentservicesoap.configuration;

import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.io.InputStream;

@Configuration
@Slf4j
public class MinioClientConfiguration implements CommandLineRunner {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.bucket}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();
        if (!this.minioClient().bucketExists(bucketExistsArgs)) {
            this.minioClient().makeBucket(MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build());
            log.info("Bucket with name '{}' saved to minio", bucketName);
        } else {
            log.info("Bucket with name '{}' already exists", bucketName);
        }
        this.uploadImageOnStartUp("/jb.png");
        this.uploadImageOnStartUp("/vp.png");
    }

    private void uploadImageOnStartUp(String imageName) throws Exception {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(imageName)) {
            this.minioClient().putObject(PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(imageName)
                            .stream(resourceAsStream, -1, DataSize.ofMegabytes(5).toBytes())
                            .build());
            log.info("Image with name '{}' saved to minio image-storage bucket", imageName);
        }
    }
}
