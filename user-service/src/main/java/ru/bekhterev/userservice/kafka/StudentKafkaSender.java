package ru.bekhterev.userservice.kafka;

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

    private final ReplyingKafkaTemplate<Long, String, String> replyingKafkaTemplate;

    @Value("${kafka.topic.students}")
    private String studentsTopicName;

    public String sendRecordBookNumber(String recordBookNumber) throws ExecutionException, InterruptedException {
        ProducerRecord<Long, String> studentsRecord = new ProducerRecord<>(studentsTopicName, recordBookNumber);
        RequestReplyFuture<Long, String, String> future = replyingKafkaTemplate.sendAndReceive(studentsRecord);
        SendResult<Long, String> sendResult = future.getSendFuture().get();
        log.info("Message sent: '{}'", sendResult.getProducerRecord().value());
        ConsumerRecord<Long, String> consumerRecord = future.get();
        log.info("Message received: '{}'", consumerRecord.value());
        return consumerRecord.value();
    }
}
