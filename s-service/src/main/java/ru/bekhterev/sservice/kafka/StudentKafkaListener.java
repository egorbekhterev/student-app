package ru.bekhterev.sservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.SoapFaultClientException;
import ru.bekhterev.sservice.exception.ParsingException;
import ru.bekhterev.sservice.client.StudentClient;
import ru.bekhterev.sservice.xml.GetAllStudentsResponse;
import ru.bekhterev.sservice.xml.GetStudentResponse;


@Service
@Slf4j
@RequiredArgsConstructor
public class StudentKafkaListener {

    private final StudentClient studentClient;

    @KafkaListener(topics = {"students"}, id = "students", containerFactory = "requestReplyListenerContainerFactory")
    @SendTo(value = "users")
    public String consumeStudent(@Payload(required = false) String recordBookNumber,
                                 @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        if (key.equalsIgnoreCase("studentList")) {
            log.info("Sending list of users");
            GetAllStudentsResponse allStudents = studentClient.getAllStudents();
            return this.parseXml(allStudents);
        }
        log.info("Record book number received from user-service '{}'", recordBookNumber);
        try {
            GetStudentResponse student = studentClient.getStudent(recordBookNumber);
            return this.parseXml(student);
        } catch (SoapFaultClientException e) {
            return "Error: " + e.getFaultStringOrReason();
        }
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
