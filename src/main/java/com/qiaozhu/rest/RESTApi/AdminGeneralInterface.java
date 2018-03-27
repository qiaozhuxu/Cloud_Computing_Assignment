package com.qiaozhu.rest.RESTApi;

import java.util.Optional;
import java.util.Set;

import javax.ws.rs.Path;
/**
 * @author Serena
 * For administrators, managing student/professor/program/courses share some common actions
 * 1. get all students/professors/programs/courses 
 * 2. get specific student/professor/program/course with id
 * 3. update personal information for specific id
 * 4. create new student/professor/program/course
 * 5. delete specific student/professor/program/course with id
 */
@Path("admin")
public interface AdminGeneralInterface<T> {
    
    public Optional<Set<T>> getAllForProgram(String programId);

    public Optional<T> getOne(String id);
    
    public Optional<T> update(String id, T entity);
    
    public Optional<T> create(T entity);
    
    public void delete(String id);
}
