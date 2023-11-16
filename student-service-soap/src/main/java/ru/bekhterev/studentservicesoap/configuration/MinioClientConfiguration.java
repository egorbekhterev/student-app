package ru.bekhterev.studentservicesoap.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        if (!minioClient().bucketExists(bucketExistsArgs)) {
            minioClient().makeBucket(MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build());
            log.info("Bucket with name '{}' saved to minio", bucketName);
        } else {
            log.info("Bucket with name '{}' already exists", bucketName);
        }
    }
}
