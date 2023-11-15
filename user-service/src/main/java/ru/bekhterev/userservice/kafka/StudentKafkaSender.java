package ru.bekhterev.userservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentKafkaSender {

    private final KafkaTemplate<Long, String> kafkaTemplate;

    public void sendRecordBookNumber(String recordBookNumber) {
        kafkaTemplate.send("students", recordBookNumber);
    }
}
