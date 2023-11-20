package ru.bekhterev.sservice.service;

import io.minio.errors.MinioException;
import ru.bekhterev.sservice.exception.EntityNotExistException;
import ru.bekhterev.sservice.xml.Student;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface StudentService {

//    void createStudent(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    Student getStudent(String recordBookNumber) throws EntityNotExistException, IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
    List<Student> getStudentList() throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
}
