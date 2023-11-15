package ru.bekhterev.studentservice.mapper.impl;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bekhterev.studentservice.entity.Student;
import ru.bekhterev.studentservice.mapper.StudentMapper;
import ru.bekhterev.studentservice.service.MinioService;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class StudentMapperImpl implements StudentMapper {

    private final MinioService minioService;

    @Override
    public Student map(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        minioService.saveFile(studentDto.getImage());
        return Student.builder()
                .withFaculty(studentDto.getFaculty())
                .withLastName(studentDto.getLastName())
                .withFirstName(studentDto.getFirstName())
                .withMiddleName(studentDto.getMiddleName())
                .withFileName(studentDto.getImage().getOriginalFilename())
                .build();
    }

    @Override
    public StudentView map(Student student) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        String fileUrl = minioService.getFileUrl(student.getFileName());
        return StudentView.builder()
                .withRecordBookNumber(student.getRecordBookNumber())
                .withFaculty(student.getFaculty())
                .withLastName(student.getLastName())
                .withFirstName(student.getFirstName())
                .withMiddleName(student.getMiddleName())
                .withFileUrl(fileUrl)
                .build();
    }
}
