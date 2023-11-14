package ru.bekhterev.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bekhterev.studentservice.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRecordBookNumber(String recordBookNumber);
}
