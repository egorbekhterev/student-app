package ru.bekhterev.studentservicesoap.service;

import io.minio.errors.MinioException;
import ru.bekhterev.studentservicesoap.Student;
import ru.bekhterev.studentservicesoap.exception.EntityNotExistException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface StudentService {

//    void createStudent(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    Student getStudent(String recordBookNumber) throws EntityNotExistException, IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    List<Student> getStudentList() throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
