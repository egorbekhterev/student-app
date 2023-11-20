package ru.bekhterev.sservice.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.io.InputStream;

@Component
@Slf4j
public class MinioInitializer implements CommandLineRunner {

    private final MinioClient minioClient;

    public MinioInitializer(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Value("${minio.bucket}")
    private String bucketName;

    @Override
    public void run(String... args) throws Exception {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();
        if (!minioClient.bucketExists(bucketExistsArgs)) {
            minioClient.makeBucket(MakeBucketArgs.builder()
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
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(imageName)
                    .stream(resourceAsStream, -1, DataSize.ofMegabytes(5).toBytes())
                    .build());
            log.info("Image with name '{}' saved to minio image-storage bucket", imageName);
        }
    }
}
