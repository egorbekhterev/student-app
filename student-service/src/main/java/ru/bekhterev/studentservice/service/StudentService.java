package ru.bekhterev.studentservice.service;

import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.util.List;

public interface StudentService {

    void createStudent(StudentDto studentDto) throws Exception;
    StudentView getStudent(String recordBookNumber);
    List<StudentView> getStudentList();
}
