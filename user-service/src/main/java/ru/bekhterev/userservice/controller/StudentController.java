package ru.bekhterev.userservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bekhterev.userservice.service.StudentService;
import ru.bekhterev.userservice.view.StudentView;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Сервис для получения данных студентов")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{recordBookNumber}")
    @ResponseBody
    public ResponseEntity<StudentView> getStudent(@PathVariable String recordBookNumber) {
        return ResponseEntity.ok(studentService.getUserByRecordBookNumber(recordBookNumber));
    }
}
