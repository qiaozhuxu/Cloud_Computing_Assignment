package com.qiaozhu.rest.RESTApi;

import java.util.Optional;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;

/**
 * @author Serena
 * Student and professor share some common actions
 * 1. get personal information 
 * 2. update personal information
 * 3. get list of courses registered/teaching
 * 4. get announcements for specific course(courseId)
 * 5. get letures for specific course(courseId)
 * 6. get notes for specific course(courseId)
 */
public interface SelfPortal<T> {   
    @GET
    public Optional<T> getPersonalInformation(String key);
    
    @PUT
    public void updatePersonalInformation(String personalId, T updateEntity);
    
    @GET
    public Optional<Set<String>> getMyCourses(String personalId);
    
    @GET
    public Optional<Set<String>> getAnnouncementsforCourse(String personalId, String courseId);
    
    @GET
    public Optional<Set<String>> getLecturesForCourse(String personalId, String courseId);   
}
