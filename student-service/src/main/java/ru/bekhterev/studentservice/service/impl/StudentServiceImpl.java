package ru.bekhterev.studentservice.service.impl;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bekhterev.studentservice.entity.Student;
import ru.bekhterev.studentservice.exception.EntityNotExistException;
import ru.bekhterev.studentservice.mapper.StudentMapper;
import ru.bekhterev.studentservice.repository.StudentRepository;
import ru.bekhterev.studentservice.service.StudentService;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    @Override
    public void createStudent(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        Student student = studentMapper.map(studentDto);
        studentRepository.save(student);
        log.info("Student with record book number '{}' saved", student.getRecordBookNumber());
    }

    @Override
    public StudentView getStudent(String recordBookNumber) throws EntityNotExistException, IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        Student student = studentRepository.findByRecordBookNumber(recordBookNumber).orElseThrow(
                () -> new EntityNotExistException("Student with record book number '%s' not found", recordBookNumber));
        return studentMapper.map(student);
    }

    @Override
    public List<StudentView> getStudentList() throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        List<StudentView> students = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            StudentView studentView = studentMapper.map(student);
            students.add(studentView);
        }
        return students;
    }
}
