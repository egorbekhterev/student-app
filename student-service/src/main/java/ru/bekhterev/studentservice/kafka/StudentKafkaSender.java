package ru.bekhterev.studentservice.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.bekhterev.studentservice.view.StudentView;

@Service
@RequiredArgsConstructor
public class StudentKafkaSender {

    private final KafkaTemplate<Long, String> kafkaTemplate;

    public void sendStudent(StudentView student) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            String studentString = xmlMapper.writeValueAsString(student);
            kafkaTemplate.send("users", studentString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
