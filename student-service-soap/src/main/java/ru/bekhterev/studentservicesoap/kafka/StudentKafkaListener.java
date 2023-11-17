package ru.bekhterev.studentservicesoap.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import ru.bekhterev.studentservicesoap.exception.ParsingException;
import ru.bekhterev.studentservicesoap.view.GetAllStudentsResponse;
import ru.bekhterev.studentservicesoap.view.GetStudentResponse;
import ru.bekhterev.studentservicesoap.client.StudentClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentKafkaListener {

    private final StudentClient studentClient;

    @KafkaListener(topics = {"students"}, id = "students", containerFactory = "requestReplyListenerContainerFactory")
    @SendTo(value = "users")
    public String consumeStudent(String recordBookNumber) {
        if (recordBookNumber.isEmpty()) {
            log.info("Record book number is empty, so sending list of users");
            GetAllStudentsResponse allStudents = studentClient.getAllStudents();
            return this.parseXml(allStudents);
        }
        log.info("Record book number received from user-service '{}'", recordBookNumber);
        GetStudentResponse student = studentClient.getStudent(recordBookNumber);
        return this.parseXml(student);
    }

    private String parseXml(Object object) throws ParsingException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ParsingException("Exception while parsing student xml to string");
        }
    }
}
