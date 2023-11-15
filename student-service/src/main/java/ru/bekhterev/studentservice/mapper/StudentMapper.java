package ru.bekhterev.studentservice.mapper;

import io.minio.errors.MinioException;
import ru.bekhterev.studentservice.entity.Student;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface StudentMapper {

    Student map(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    StudentView map(Student student) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
