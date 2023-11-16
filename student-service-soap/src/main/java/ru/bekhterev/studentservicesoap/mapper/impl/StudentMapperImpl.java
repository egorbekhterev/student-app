package ru.bekhterev.studentservicesoap.mapper.impl;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bekhterev.studentservicesoap.entity.StudentEntity;
import ru.bekhterev.studentservicesoap.mapper.StudentMapper;
import ru.bekhterev.studentservicesoap.service.MinioService;
import ru.bekhterev.studentservicesoap.view.Student;

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
        return Student.builder()
                .withRecordBookNumber(studentEntity.getRecordBookNumber())
                .withFaculty(studentEntity.getFaculty())
                .withLastName(studentEntity.getLastName())
                .withFirstName(studentEntity.getFirstName())
                .withMiddleName(studentEntity.getMiddleName())
                .withFileUrl(fileUrl)
                .build();
    }
}
