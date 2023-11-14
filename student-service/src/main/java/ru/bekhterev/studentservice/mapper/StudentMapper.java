package ru.bekhterev.studentservice.mapper;

import ru.bekhterev.studentservice.entity.Student;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

public interface StudentMapper {

    Student map(StudentDto studentDto);
    StudentView map(Student student);
}
