package ru.bekhterev.userservice.service;

import ru.bekhterev.userservice.view.StudentView;

public interface StudentService {

    StudentView getUserByRecordBookNumber(String recordBookNumber);
}
