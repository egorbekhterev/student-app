package ru.bekhterev.rservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentKafkaSender {

    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    @Value("${kafka.topic.students}")
    private String studentsTopicName;

    public String sendRecordBookNumber(String recordBookNumber, String key) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> studentsRecord = new ProducerRecord<>(studentsTopicName, key, recordBookNumber);
        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(studentsRecord);
        SendResult<String, String> sendResult = future.getSendFuture().get();
        log.info("Message sent. Key: '{}', value: '{}'", sendResult.getProducerRecord().value(),
                sendResult.getProducerRecord().key());
        ConsumerRecord<String, String> consumerRecord = future.get();
        log.info("Message received: '{}'", consumerRecord.value());
        return consumerRecord.value();
    }
}
