package ru.bekhterev.sservice.mapper;

import io.minio.errors.MinioException;
import ru.bekhterev.sservice.entity.StudentEntity;
import ru.bekhterev.sservice.xml.Student;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface StudentMapper {

    Student map(StudentEntity studentEntity) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
