package ru.bekhterev.studentservicesoap.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import ru.bekhterev.studentservicesoap.view.GetStudentResponse;
import ru.bekhterev.studentservicesoap.client.StudentClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentKafkaListener {

    private final StudentClient studentClient;

    @KafkaListener(topics = {"students"}, id = "students")
    @SendTo(value = "users")
    public String consumeStudent(String recordBookNumber) {
        log.info("Record book number received from user-service '{}'", recordBookNumber);
        GetStudentResponse student = studentClient.getStudent(recordBookNumber);
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
