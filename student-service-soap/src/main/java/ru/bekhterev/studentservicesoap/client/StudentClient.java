package ru.bekhterev.studentservicesoap.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import ru.bekhterev.studentservicesoap.GetStudentRequest;
import ru.bekhterev.studentservicesoap.view.GetStudentResponse;

@Slf4j
public class StudentClient extends WebServiceGatewaySupport {

    public GetStudentResponse getStudent(String recordBookNumber) {
        GetStudentRequest request = new GetStudentRequest();
        request.setRecordBookNumber(recordBookNumber);
        log.info("Requesting information for " + recordBookNumber);
        GetStudentResponse response = (GetStudentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }
}
