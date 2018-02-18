package com.qiaozhu.rest.RESTExample;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.qiaozhu.rest.RESTModel.Student;

public interface StudentService {
    @GET
    @Path("/students/")
    Response getStudents();
    
    @GET
    @Path("/student/{id}")
    Response getStudentProfile(@PathParam("id")int studentId);
    
    @PUT
    @Path("/student/{id}")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    Response updateStudentProfile(@PathParam("id")int studentId, Student student);
    
    @POST
    @Path("/student/")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    Response createStudentProfile(Student student);
    
    @DELETE
    @Path("/student/{id}")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    Response deleteStudentProfile(@PathParam("id")String studentId);
}
