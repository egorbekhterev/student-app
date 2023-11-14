package ru.bekhterev.studentservice.mapper.impl;

import org.springframework.stereotype.Component;
import ru.bekhterev.studentservice.entity.Student;
import ru.bekhterev.studentservice.enums.Faculty;
import ru.bekhterev.studentservice.mapper.StudentMapper;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student map(StudentDto studentDto) {
        return Student.builder()
                .withRecordBookNumber(studentDto.getRecordBookNumber())
                .withFaculty(Faculty.valueOf(studentDto.getFaculty()))
                .withLastName(studentDto.getLastName())
                .withFirstName(studentDto.getFirstName())
                .withMiddleName(studentDto.getMiddleName())
                .withFileName(null)
                .build();
    }

    @Override
    public StudentView map(Student student) {
        return StudentView.builder()
                .withRecordBookNumber(student.getRecordBookNumber())
                .withFaculty(student.getFaculty())
                .withLastName(student.getLastName())
                .withFirstName(student.getFirstName())
                .withMiddleName(student.getMiddleName())
                .withFilename(student.getFileName())
                .build();
    }
}
