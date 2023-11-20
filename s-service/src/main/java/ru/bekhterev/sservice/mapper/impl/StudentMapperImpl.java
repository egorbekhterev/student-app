package ru.bekhterev.sservice.mapper.impl;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bekhterev.sservice.entity.StudentEntity;
import ru.bekhterev.sservice.mapper.StudentMapper;
import ru.bekhterev.sservice.service.MinioService;
import ru.bekhterev.sservice.xml.Student;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class StudentMapperImpl implements StudentMapper {

    private final MinioService minioService;

    @Override
    public Student map(StudentEntity studentEntity) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        String fileUrl = minioService.getFileUrl(studentEntity.getFileName());
        Student student = new Student();
        student.setFaculty(studentEntity.getFaculty());
        student.setLastName(studentEntity.getLastName());
        student.setRecordBookNumber(studentEntity.getRecordBookNumber());
        student.setFirstName(studentEntity.getFirstName());
        student.setMiddleName(studentEntity.getMiddleName());
        student.setFileUrl(fileUrl);
        return student;
    }
}
