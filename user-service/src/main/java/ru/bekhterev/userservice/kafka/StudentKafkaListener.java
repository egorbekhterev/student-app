package ru.bekhterev.userservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bekhterev.userservice.view.StudentView;

@Service
@Slf4j
public class StudentKafkaListener {

    private StudentView studentView;

    @KafkaListener(topics = {"users"}, id = "users", containerFactory = "singleFactory")
    public void consumeStudent(String student) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            studentView = xmlMapper.readValue(student, StudentView.class);
            log.info("Student received from student-service {}", studentView);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentView getStudentView() {
        return studentView;
    }
}
