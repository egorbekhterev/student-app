package ru.bekhterev.studentservicesoap.mapper.impl;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bekhterev.studentservicesoap.Student;
import ru.bekhterev.studentservicesoap.entity.StudentEntity;
import ru.bekhterev.studentservicesoap.mapper.StudentMapper;
import ru.bekhterev.studentservicesoap.service.MinioService;

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
