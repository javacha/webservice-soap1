package com.javacha.demos.students;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.javacha.demos.students.GetStudentDetailsRequest;
import com.javacha.demos.students.GetStudentDetailsResponse;
import com.javacha.demos.students.StudentDetails;

@Endpoint
public class StudentDetailsEndpoint {

  @PayloadRoot(namespace = "http://com.javacha.demos/students", localPart = "GetStudentDetailsRequest")
  @ResponsePayload
  public GetStudentDetailsResponse processCourseDetailsRequest(@RequestPayload GetStudentDetailsRequest request) {
    GetStudentDetailsResponse response = new GetStudentDetailsResponse();
    
    StudentDetails studentDetails = new StudentDetails();
    studentDetails.setId(request.getId());
    studentDetails.setName("Adam");
    studentDetails.setPassportNumber("E1234567");
    
    response.setStudentDetails(studentDetails);
    
    return response;
  }

}
