package ru.bekhterev.studentservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bekhterev.studentservice.service.StudentService;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Сервис для получения данных студентов")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<StudentView> getStudent(@RequestParam String recordBookNumber) {
        return ResponseEntity.ok(studentService.getStudent(recordBookNumber));
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<StudentView>> getStudentList() {
        return ResponseEntity.ok(studentService.getStudentList());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@ModelAttribute StudentDto studentDto) {
        try {
            studentService.createStudent(studentDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
