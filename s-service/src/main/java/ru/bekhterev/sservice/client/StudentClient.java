package ru.bekhterev.sservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.bekhterev.sservice.xml.GetAllStudentsRequest;
import ru.bekhterev.sservice.xml.GetAllStudentsResponse;
import ru.bekhterev.sservice.xml.GetStudentRequest;
import ru.bekhterev.sservice.xml.GetStudentResponse;

@Slf4j
public class StudentClient extends WebServiceGatewaySupport {

    public GetStudentResponse getStudent(String recordBookNumber) {
        GetStudentRequest request = new GetStudentRequest();
        request.setRecordBookNumber(recordBookNumber);
        log.info("Requesting information for '{}'", recordBookNumber);
        return (GetStudentResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback(getDefaultUri() + "/getStudentRequest"));
    }

    public GetAllStudentsResponse getAllStudents() {
        GetAllStudentsRequest request = new GetAllStudentsRequest();
        log.info("Requesting information about all students");
        return (GetAllStudentsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(getDefaultUri() + "/getAllStudentsRequest"));
    }
}
