package ru.bekhterev.sservice.endpoint;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.bekhterev.sservice.exception.EntityNotExistException;
import ru.bekhterev.sservice.service.StudentService;
import ru.bekhterev.sservice.xml.GetAllStudentsResponse;
import ru.bekhterev.sservice.xml.GetStudentRequest;
import ru.bekhterev.sservice.xml.GetStudentResponse;
import ru.bekhterev.sservice.xml.Student;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class StudentEndpoint {

    private static final String NAMESPACE_URI = "http://www.bekhterev.ru/sservice/xml";

    private final StudentService studentService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentRequest")
    @ResponsePayload
    public GetStudentResponse getStudent(@RequestPayload GetStudentRequest request) throws EntityNotExistException,
            MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        GetStudentResponse response = new GetStudentResponse();
        response.setStudent(studentService.getStudent(request.getRecordBookNumber()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse getStudents() throws EntityNotExistException,
            MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        GetAllStudentsResponse response = new GetAllStudentsResponse();
        List<Student> student = response.getStudent();
        student.addAll(studentService.getStudentList());
        return response;
    }
}
