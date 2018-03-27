package com.qiaozhu.rest.RESTApi;

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
import com.qiaozhu.rest.RESTModel.Program;
@Singleton
@Path("program")
public class AdminProgramImpl implements AdminGeneralInterface<Program> {

    @Override
    public Optional<Set<Program>> getAllForProgram(String programId) {
        return Optional.empty();
    }

    @Override
    @GET
    @Path("{programId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Program> getOne(@PathParam("programId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, id));
        return program;
    }

    @Override
    @PUT
    @PathParam("programId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Program> update(String id, Program entity) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, id));
        if(!program.isPresent()) {
            return Optional.empty();
        }
        dynamoDB.addOrUpdateItem(program.get());
        return program;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Program> create(Program newProgram) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<String> programId = Optional.of(newProgram.id);
        //it is clients responsibility to generate programId
        if(programId.isPresent()) {
            Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, newProgram.id));
            //program with same Id has already exist
            if(program.isPresent()) {
                return Optional.empty();
            }
            //add new program in Programs table
            dynamoDB.addOrUpdateItem(newProgram);
        }
        return Optional.empty();
    }

    @Override
    @DELETE
    @PathParam("programId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("programId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, id));
        if(program.isPresent()) {
            //delete should work from the Entity with less dependency 
            //delete courses in this program (first delete all Notes, Announcements, Lectures....)
            //delete professors from program
            //delete students from program
            //program table should be the last table to update...
            dynamoDB.deleteItem(Constants.PROGRAM_TABLE_NAME, id);
        }
    }
}
