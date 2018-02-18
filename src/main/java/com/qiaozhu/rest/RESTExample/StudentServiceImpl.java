package com.qiaozhu.rest.RESTExample;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.qiaozhu.rest.RESTModel.Course;
import com.qiaozhu.rest.RESTModel.Program;
import com.qiaozhu.rest.RESTModel.Student;
@Path("neu")
@Singleton
public class StudentServiceImpl implements StudentService {
    static int currentId = 6031;
    Map<Integer, Student> currentStudents = new HashMap<>();
    HashSet<Course> activeCourse = new HashSet<>();
    Program programIS;
    

    public StudentServiceImpl() {
        init();
    }

    private void init() {
        //initiate 3 course
        Course cloudComputing = Course.builder().courseName("CloudComputing").credit(4).build();
        Course algorithms = Course.builder().courseName("Algorithms").credit(4).build();
        Course webSecurity = Course.builder().courseName("WebSecurity").credit(4).build();
        //initiate 1 program "IS" and add all course to the program "IS"
        programIS = Program.builder().programName("Information System").activeCourse(activeCourse).build();
        programIS.addCourse(webSecurity);
        programIS.addCourse(algorithms);
        programIS.addCourse(cloudComputing);
        //initiate 2 students "serena" and "jack" with programIS, with 3 course
        Student serena =Student.builder()
                .firstName("Serena")
                .lastName("Wei")
                .id(6030)
                .programEnrolled(programIS)
                .imageUrl("fakeURL")
                .courseRegistered(new HashSet<Course>())
                .build();
        currentStudents.put(6030,serena);
        Student jack = Student.builder()
                .firstName("Jack")
                .lastName("London")
                .id(6031)
                .programEnrolled(programIS)
                .imageUrl("fakeURL")
                .courseRegistered(new HashSet<Course>())
                .build();
        currentStudents.put(6031,jack);
        
    }

    @Override
    public Response getStudents() {
        return Response.status(200).entity(currentStudents).build();
    }

    @Override
    public Response getStudentProfile(int studentId) {
        if (studentId < 6030) {
            return Response.serverError().entity("StudentId cannot less than 6030").build();
        }
        if(currentStudents.containsKey(studentId)) {
            Student student = currentStudents.get(studentId);

            ResponseBuilder builder =  Response.status(Response.Status.OK).entity(student);
            return builder.build();
//            return "get student : " + studentId + student.getFirstName();
            
        }
        return Response.status(Response.Status.NOT_FOUND).entity("There is no student with provided id").build();
        
    }

    @Override
    public Response updateStudentProfile(int studentId, Student student) {
        if(currentStudents.containsKey(studentId)) {
            currentStudents.put(studentId, student);
            return Response.status(200).build();
//            return "get student : " + studentId + student.getFirstName();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("There is no student with provided id").build();
        }
    }

    @Override
    public Response createStudentProfile(Student student) {
        currentId++;
        student.setId(currentId);
        currentStudents.put(currentId, student);
        return Response.status(200).entity(currentStudents).build();
    }

    @Override
    public Response deleteStudentProfile(String studentId) {
        currentStudents.remove(Integer.parseInt(studentId));
        return Response.status(200).entity(currentStudents).build();
    }
}
