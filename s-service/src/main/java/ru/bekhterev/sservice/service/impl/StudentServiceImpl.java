package ru.bekhterev.sservice.service.impl;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bekhterev.sservice.entity.StudentEntity;
import ru.bekhterev.sservice.exception.EntityNotExistException;
import ru.bekhterev.sservice.mapper.StudentMapper;
import ru.bekhterev.sservice.repository.StudentRepository;
import ru.bekhterev.sservice.service.StudentService;
import ru.bekhterev.sservice.xml.Student;

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

//    @Transactional
//    @Override
//    public void createStudent(StudentDto studentDto) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
//        Student student = studentMapper.map(studentDto);
//        studentRepository.save(student);
//        log.info("Student with record book number '{}' saved", student.getRecordBookNumber());
//    }

    @Override
    public Student getStudent(String recordBookNumber) throws EntityNotExistException, IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        StudentEntity student = studentRepository.findByRecordBookNumber(recordBookNumber).orElseThrow(
                () -> new EntityNotExistException("Student with record book number '%s' not found", recordBookNumber));
        return studentMapper.map(student);
    }

    @Override
    public List<Student> getStudentList() throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        List<Student> students = new ArrayList<>();
        for (StudentEntity student : studentRepository.findAll()) {
            Student studentView = studentMapper.map(student);
            students.add(studentView);
        }
        return students;
    }
}
