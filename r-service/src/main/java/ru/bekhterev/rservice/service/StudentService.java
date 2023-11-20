package ru.bekhterev.rservice.service;


import ru.bekhterev.rservice.exception.ParsingException;
import ru.bekhterev.rservice.view.GetAllStudentsResponse;
import ru.bekhterev.rservice.view.GetStudentResponse;

import java.util.concurrent.ExecutionException;

public interface StudentService {

    GetStudentResponse getStudentByRecordBookNumber(String recordBookNumber) throws ParsingException, ExecutionException, InterruptedException;
    GetAllStudentsResponse getStudents() throws ParsingException, ExecutionException, InterruptedException;
}
