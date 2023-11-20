package ru.bekhterev.sservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bekhterev.sservice.entity.StudentEntity;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByRecordBookNumber(String recordBookNumber);
}
