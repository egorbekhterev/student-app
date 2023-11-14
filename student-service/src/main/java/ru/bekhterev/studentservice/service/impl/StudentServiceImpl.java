package ru.bekhterev.studentservice.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import ru.bekhterev.studentservice.entity.Student;
import ru.bekhterev.studentservice.exception.EntityNotExistException;
import ru.bekhterev.studentservice.mapper.StudentMapper;
import ru.bekhterev.studentservice.repository.StudentRepository;
import ru.bekhterev.studentservice.service.StudentService;
import ru.bekhterev.studentservice.view.StudentDto;
import ru.bekhterev.studentservice.view.StudentView;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final MinioClient minioClient;

    @Override
    @Transactional
    public void createStudent(StudentDto studentDto) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
                        .bucket("test")
                        .object(studentDto.getImage().getOriginalFilename())
                        .stream(studentDto.getImage().getInputStream(), studentDto.getImage().getSize(), DataSize.ofMegabytes(5).toBytes())
                        .build());
        Student student = studentMapper.map(studentDto);
        studentRepository.save(student);
        log.info("Student with record book number '{}' saved", student.getRecordBookNumber());
    }

    @Override
    public StudentView getStudent(String recordBookNumber) {
        Student student = studentRepository.findByRecordBookNumber(recordBookNumber).orElseThrow(
                () -> new EntityNotExistException("Student with record book number '%s not found", recordBookNumber));
        return studentMapper.map(student);
    }

    @Override
    public List<StudentView> getStudentList() {
        return studentRepository.findAll().stream()
                .map(studentMapper::map)
                .toList();
    }
}
