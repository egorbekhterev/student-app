package ru.bekhterev.userservice.service;


import ru.bekhterev.userservice.exception.ParsingException;
import ru.bekhterev.userservice.view.GetAllStudentsResponse;
import ru.bekhterev.userservice.view.GetStudentResponse;

import java.util.concurrent.ExecutionException;

public interface StudentService {

    GetStudentResponse getStudentByRecordBookNumber(String recordBookNumber) throws ParsingException, ExecutionException, InterruptedException;
    GetAllStudentsResponse getStudents() throws ParsingException, ExecutionException, InterruptedException;
}
