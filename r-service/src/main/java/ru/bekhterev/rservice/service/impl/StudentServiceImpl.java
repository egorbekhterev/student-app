package ru.bekhterev.rservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bekhterev.rservice.exception.ParsingException;
import ru.bekhterev.rservice.kafka.StudentKafkaSender;
import ru.bekhterev.rservice.service.StudentService;
import ru.bekhterev.rservice.view.GetAllStudentsResponse;
import ru.bekhterev.rservice.view.GetStudentResponse;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentKafkaSender studentKafkaSender;

    @Override
    public GetStudentResponse getStudentByRecordBookNumber(String recordBookNumber) throws ParsingException,
            ExecutionException, InterruptedException {
        String student = studentKafkaSender.sendRecordBookNumber(recordBookNumber);
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(student, GetStudentResponse.class);
        } catch (JsonProcessingException e) {
            throw new ParsingException("Exception while parsing xml string from student-service");
        }
    }

    @Override
    public GetAllStudentsResponse getStudents() throws ParsingException, ExecutionException, InterruptedException {
        String students = studentKafkaSender.sendRecordBookNumber("");
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(students, GetAllStudentsResponse.class);
        } catch (JsonProcessingException e) {
            throw new ParsingException("Exception while parsing xml string from student-service");
        }
    }
}
