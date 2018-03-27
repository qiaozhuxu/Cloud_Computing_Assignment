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
import com.qiaozhu.rest.RESTModel.Professor;

@Singleton
@Path("professor")
public class AdminProfessorImpl implements AdminGeneralInterface<Professor> {
    @Override
    @GET
    @Path("{programId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<Professor>> getAllForProgram(@PathParam("programId")String programId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Program program = (Program)dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, programId);
        if(!program.professorIds.isEmpty()) {
            Set<String> ids = program.professorIds;
            Set<Professor> professors = new HashSet<>();
            for(String id: ids) {
                Professor professor = (Professor) dynamoDB.getItem(Constants.PROFESSOR_TABLE_NAME, id);
                professors.add(professor);
            }
            return Optional.of(professors);
        }
        return Optional.empty();
    }

    @Override
    @GET
    @Path("{professorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Professor> getOne(@PathParam("professorId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem(Constants.PROFESSOR_TABLE_NAME, id));
        return professor;
    }

    @Override
    @PUT
    @PathParam("professorId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Professor> update(String id, Professor entity) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem(Constants.PROFESSOR_TABLE_NAME, id));
        if(!professor.isPresent()) {
            return Optional.empty();
        }
        dynamoDB.addOrUpdateItem(professor.get());
        return professor;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Professor> create(Professor newProfessor) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem(Constants.PROFESSOR_TABLE_NAME, newProfessor.id));
        if(professor.isPresent()) {
            return Optional.empty();
        }
        //add new professor in Professors table
        dynamoDB.addOrUpdateItem(newProfessor);
        //enroll new Professors in certain program, these two steps happen at the same time
        Optional<String> newId = Optional.of(newProfessor.id);
        if (newId.isPresent()) {
            Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, newId.get()));
            program.get().professorIds.add(newId.get());
            dynamoDB.addOrUpdateItem(program.get());
            return Optional.of(newProfessor);
        }
        return Optional.empty();
    }

    @Override
    @DELETE
    @PathParam("professorId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("professorId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem(Constants.PROFESSOR_TABLE_NAME, id));
        if(professor.isPresent()) {
            //delete professor from courses
            Optional<Set<String>> coursesRegistered = Optional.of(professor.get().courseIds);
            if(coursesRegistered.isPresent() && !coursesRegistered.get().isEmpty()) {
                for(String courseId : coursesRegistered.get()) {
                    Course course = (Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, courseId);
                    course.setProfessorId("");;
                    dynamoDB.addOrUpdateItem(course);
                }
            }
            //delete professor from program
            Optional<String> programId = Optional.of(professor.get().programId);
            if(programId.isPresent() && programId.get() != "") {
                Program program = (Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, programId.get());
                program.professorIds.remove(id);
                dynamoDB.addOrUpdateItem(program);
            }
            dynamoDB.deleteItem(Constants.PROFESSOR_TABLE_NAME, id);
        }
    }
}
