package ru.bekhterev.studentservicesoap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bekhterev.studentservicesoap.entity.StudentEntity;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByRecordBookNumber(String recordBookNumber);
}
