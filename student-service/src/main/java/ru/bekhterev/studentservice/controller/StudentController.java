package ru.bekhterev.studentservice.controller;

import io.minio.errors.MinioException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.bekhterev.studentservice.exception.EntityNotExistException;
import ru.bekhterev.studentservice.service.StudentService;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Сервис для получения данных студентов")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<StudentView> getStudent(@RequestParam String recordBookNumber) {
        try {
            return ResponseEntity.ok(studentService.getStudent(recordBookNumber));
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IOException | MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<List<StudentView>> getStudentList() {
        try {
            return ResponseEntity.ok(studentService.getStudentList());
        } catch (IOException | MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@ModelAttribute StudentDto studentDto) {
        try {
            studentService.createStudent(studentDto);
        } catch (IOException | MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
