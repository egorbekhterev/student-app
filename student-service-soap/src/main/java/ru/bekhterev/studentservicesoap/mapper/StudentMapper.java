package ru.bekhterev.studentservicesoap.mapper;

import io.minio.errors.MinioException;
import ru.bekhterev.studentservicesoap.entity.StudentEntity;
import ru.bekhterev.studentservicesoap.view.Student;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface StudentMapper {

    Student map(StudentEntity studentEntity) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
