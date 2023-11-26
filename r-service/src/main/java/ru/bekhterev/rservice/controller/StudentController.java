package ru.bekhterev.rservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.bekhterev.rservice.exception.EntityNotExistException;
import ru.bekhterev.rservice.exception.ParsingException;
import ru.bekhterev.rservice.service.StudentService;
import ru.bekhterev.rservice.view.GetAllStudentsResponse;
import ru.bekhterev.rservice.view.GetStudentResponse;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Сервис для получения данных студентов")
@RequiredArgsConstructor
public class StudentController {

    private static final String KAFKA_EXCEPTION = "Exception while getting answer by Kafka from student-service";

    private final StudentService studentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{recordBookNumber}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetStudentResponse> getStudent(@PathVariable String recordBookNumber) {
        try {
            GetStudentResponse student = studentService.getStudentByRecordBookNumber(recordBookNumber);
            return ResponseEntity.ok(student);
        } catch (ParsingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, KAFKA_EXCEPTION);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetAllStudentsResponse> getStudents() {
        try {
            GetAllStudentsResponse students = studentService.getStudents();
            return ResponseEntity.ok(students);
        } catch (ParsingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, KAFKA_EXCEPTION);
        }
    }
}
