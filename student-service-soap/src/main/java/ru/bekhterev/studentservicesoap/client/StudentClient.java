package ru.bekhterev.studentservicesoap.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.bekhterev.studentservicesoap.GetAllStudentsRequest;
import ru.bekhterev.studentservicesoap.view.GetAllStudentsResponse;
import ru.bekhterev.studentservicesoap.GetStudentRequest;
import ru.bekhterev.studentservicesoap.view.GetStudentResponse;

@Slf4j
public class StudentClient extends WebServiceGatewaySupport {

    public GetStudentResponse getStudent(String recordBookNumber) {
        GetStudentRequest request = new GetStudentRequest();
        request.setRecordBookNumber(recordBookNumber);
        log.info("Requesting information for " + recordBookNumber);
        return (GetStudentResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback(getDefaultUri() + "/getStudentRequest"));
    }

    public GetAllStudentsResponse getAllStudents() {
        GetAllStudentsRequest request = new GetAllStudentsRequest();
        return (GetAllStudentsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(getDefaultUri() + "/getAllStudentsRequest"));
    }
}
