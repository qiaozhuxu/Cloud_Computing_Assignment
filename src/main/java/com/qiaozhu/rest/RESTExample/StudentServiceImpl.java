package com.qiaozhu.rest.RESTExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.qiaozhu.rest.RESTModel.Student;
@Path("neu")
public class StudentServiceImpl implements StudentService {
    Map<Integer, Student> currentStudents = new HashMap<>();
    static int currentId = 6031;
    
    public StudentServiceImpl() {
        Student serena =Student.builder().firstName("Serena").lastName("Wei").build();
        
        serena.setId(6030);
        currentStudents.put(6030,serena);
        Student jack = Student.builder().firstName("Jack").lastName("London").build();
        jack.setId(6031);
        currentStudents.put(6031,jack);
    }

    @Override
    public String getStudents() {
//    List<Student> currentSts = new ArrayList<>(currentStudents.values());
    String result = "";
    for(Student student: currentStudents.values()) {
        result = result + student.getId() + student.getFirstName() + " "+student.getLastName()+";";
    }
        return result;
    }

    @Override
    public String getStudentProfile(String studentId) {
        int id = Integer.parseInt(studentId);
        String result = "";
        if(currentStudents.containsKey(id)) {
            Student student = currentStudents.get(id);
            result = student.getFirstName() + " "+student.getLastName()+";";
        } else {
            result = "Could not find the student with id: " + id;
        }
        return result;
    }

    @Override
    public Response updateStudentProfile(Student student) {
        int studentId = student.getId();
        currentStudents.put(studentId, student);
        return Response.status(200).entity(currentStudents).build();
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
