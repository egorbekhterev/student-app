package ru.bekhterev.studentservice.service;

import io.minio.errors.MinioException;
import ru.bekhterev.studentservice.exception.EntityNotExistException;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface StudentService {

    void createStudent(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    StudentView getStudent(String recordBookNumber) throws EntityNotExistException, IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    List<StudentView> getStudentList() throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
