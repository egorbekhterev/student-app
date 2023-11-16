package ru.bekhterev.userservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import ru.bekhterev.userservice.view.GetStudentResponse;

import java.util.concurrent.ExecutionException;

public interface StudentService {

    GetStudentResponse getStudentByRecordBookNumber(String recordBookNumber) throws JsonProcessingException, ExecutionException, InterruptedException;
}
