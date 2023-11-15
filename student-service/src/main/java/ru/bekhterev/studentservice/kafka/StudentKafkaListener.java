package ru.bekhterev.studentservice.kafka;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bekhterev.studentservice.service.StudentService;
import ru.bekhterev.studentservice.view.StudentView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentKafkaListener {

    private final StudentService studentService;
    private final StudentKafkaSender kafkaSender;

    @KafkaListener(topics = {"students"}, id = "students", containerFactory = "singleFactory")
    public void consumeStudent(String recordBookNumber) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        StudentView student = studentService.getStudent(recordBookNumber);
        log.info("Record book number received from user-service '{}'", recordBookNumber);
        kafkaSender.sendStudent(student);
    }
}
