package ru.bekhterev.userservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.bekhterev.userservice.exception.ParsingException;
import ru.bekhterev.userservice.service.StudentService;
import ru.bekhterev.userservice.view.GetAllStudentsResponse;
import ru.bekhterev.userservice.view.GetStudentResponse;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Сервис для получения данных студентов")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{recordBookNumber}")
    @ResponseBody
    public ResponseEntity<GetStudentResponse> getStudent(@PathVariable String recordBookNumber){
        try {
            GetStudentResponse student = studentService.getStudentByRecordBookNumber(recordBookNumber);
            return ResponseEntity.ok(student);
        } catch (ParsingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while getting answer by kafka from student-service");
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GetAllStudentsResponse> getStudents(){
        try {
            GetAllStudentsResponse students = studentService.getStudents();
            return ResponseEntity.ok(students);
        } catch (ParsingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while getting answer by kafka from student-service");
        }
    }
}
