package com.qiaozhu.rest.RESTApi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.qiaozhu.rest.DynamoDBDelegator.DynamoDB;
import com.qiaozhu.rest.RESTModel.Course;
import com.qiaozhu.rest.RESTModel.Program;
import com.qiaozhu.rest.RESTModel.Student;
@Singleton
@Path("student")
public class AdminStudentImpl implements AdminGeneralInterface<Student> {

    @Override
    @GET
    @Path("{programId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<Student>> getAllForProgram(@PathParam("programId")String programId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Program program = (Program)dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, programId);
        if(!program.studentIds.isEmpty()) {
            Set<String> ids = program.studentIds;
            Set<Student> result = new HashSet<>();
            for(String id: ids) {
                Student student = (Student) dynamoDB.getItem(Constants.STUDENT_TABLE_NAME, id);
                result.add(student);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    @Override
    @GET
    @Path("{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Student> getOne(@PathParam("studentId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem(Constants.STUDENT_TABLE_NAME, id));
        return student;
    }

    @Override
    @PUT
    @PathParam("studentId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Student> update(String id, Student entity) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem(Constants.STUDENT_TABLE_NAME, id));
        if(!student.isPresent()) {
            return Optional.empty();
        }
        dynamoDB.addOrUpdateItem(student.get());
        return student;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Student> create(Student newStudent) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem(Constants.STUDENT_TABLE_NAME, newStudent.id));
        if(student.isPresent()) {
            return Optional.empty();
        }
        //add new student in Students table
        dynamoDB.addOrUpdateItem(newStudent);
        //enroll new Students in certain program, these two steps happen at the same time
        Optional<String> newId = Optional.of(newStudent.id);
        if (newId.isPresent()) {
            Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, newId.get()));
            program.get().studentIds.add(newId.get());
            dynamoDB.addOrUpdateItem(program.get());
            return Optional.of(newStudent);
        }
        return Optional.empty();
    }

    @Override
    @DELETE
    @PathParam("studentId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("studentId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem(Constants.STUDENT_TABLE_NAME, id));
        if(student.isPresent()) {
            //delete student from courses
            Optional<Set<String>> coursesRegistered = Optional.of(student.get().courseIds);
            if(coursesRegistered.isPresent() && !coursesRegistered.get().isEmpty()) {
                for(String courseId : coursesRegistered.get()) {
                    Course course = (Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, courseId);
                    course.studentIds.remove(id);
                    dynamoDB.addOrUpdateItem(course);
                }
            }
            //delete student from program
            Optional<String> programId = Optional.of(student.get().programId);
            if(programId.isPresent() && programId.get() != "") {
                Program program = (Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, programId.get());
                program.studentIds.remove(id);
                dynamoDB.addOrUpdateItem(program);
            }
            dynamoDB.deleteItem(Constants.STUDENT_TABLE_NAME, id);
        }
    }
}
