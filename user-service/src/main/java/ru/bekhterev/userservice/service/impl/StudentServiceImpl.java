package ru.bekhterev.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bekhterev.userservice.kafka.StudentKafkaListener;
import ru.bekhterev.userservice.kafka.StudentKafkaSender;
import ru.bekhterev.userservice.service.StudentService;
import ru.bekhterev.userservice.view.StudentView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentKafkaSender studentKafkaSender;
    private final StudentKafkaListener studentKafkaListener;

    @Override
    public StudentView getUserByRecordBookNumber(String recordBookNumber) {
        studentKafkaSender.sendRecordBookNumber(recordBookNumber);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return studentKafkaListener.getStudentView();
    }
}
