package com.qiaozhu.rest.RESTApi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.qiaozhu.rest.DynamoDBDelegator.DynamoDB;
import com.qiaozhu.rest.RESTModel.Announcement;
import com.qiaozhu.rest.RESTModel.Course;
import com.qiaozhu.rest.RESTModel.Lecture;
import com.qiaozhu.rest.RESTModel.Professor;
import com.qiaozhu.rest.RESTModel.Student;
/**
 * @author Serena
 * professor and student share some common actions
 * 1. get personal information 
 * 2. update personal information
 * 3. get list of courses registered/teaching
 * 4. get announcements for specific course(courseId)
 * 5. get letures for specific course(courseId)
 * 6. get notes for specific course(courseId)
 * 
 * Other than these, professor can post new announcement , new lectures, new materials..
 * professors can also getStudentsList for specific course
 */
@Singleton
@Path("professor/{professorId}")
public class ProfessorSelfPortal implements SelfPortal<Professor> {
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Professor> getPersonalInformation(@PathParam("professorId") String professorId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        return Optional.of((Professor)dynamoDB.getItem("Professors", professorId));
    }
    
    @Override
    @PUT
    @Path("{professorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updatePersonalInformation(@PathParam("professorId")String professorId, Professor updateEntity) {
        // TODO Auto-generated method stub
    }
    
    @Override
    @GET
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<String>> getMyCourses(@PathParam("professorId")String professorId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem("Professors", professorId));
        if (professor.isPresent()) {
            if(professor.get().getCourses() != null) {
                return Optional.of(professor.get().getCourses());
            }
        }
        return Optional.empty();
    }
    
    @Override
    @GET
    @Path("{courseId}/announcements")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<String>> getAnnouncementsforCourse(@PathParam("professorId")String professorId, @PathParam("courseId")String courseId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem("Professors", professorId));
        if (professor.isPresent()) {
            Optional<Set<String>> courses = Optional.of(professor.get().courseIds);
            if(courses.isPresent() && courses.get().contains(courseId)) {
                Course course = (Course) dynamoDB.getItem("Courses", courseId);
                Optional<Set<String>> announcements = Optional.of(course.announcementIds);
                if(announcements.isPresent() && !announcements.get().isEmpty()) {
                    Set<String> announcementIds = announcements.get();
                    Set<String> anns = new HashSet<>();
                    for(String id: announcementIds) {
                        Announcement ann = (Announcement) dynamoDB.getItem("Announcements", id);
                        anns.add(ann.toString());
                        return Optional.of(anns);
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    @GET
    @Path("{courseId}/lectures")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<String>> getLecturesForCourse(@PathParam("professorId")String professorId, @PathParam("courseId")String courseId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem("Professors", professorId));
        if (professor.isPresent()) {
            Optional<Set<String>> courses = Optional.of(professor.get().courseIds);
            if(courses.isPresent() && courses.get().contains(courseId)) {
                Course course = (Course) dynamoDB.getItem("Courses", courseId);
                Optional<Set<String>> lectures = Optional.of(course.lectureIds);
                if(lectures.isPresent() && !lectures.get().isEmpty()) {
                    Set<String> lectureIds = lectures.get();
                    Set<String> result = new HashSet<>();
                    for(String id: lectureIds) {
                        Lecture lec = (Lecture) dynamoDB.getItem("Lectures", id);
                        result.add(lec.toString());
                        return Optional.of(result);
                    }
                }
            }
        }
        return Optional.empty();
    }
    //professor can post new announcement
    @POST
    @Path("{courseId}/announcement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAnnouncementforCourse(@PathParam("professorId")String professorId, @PathParam("courseId")String courseId, Announcement announcement) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem("Professors", professorId));
        if (professor.isPresent()) {
            Optional<Set<String>> courses = Optional.of(professor.get().courseIds);
            //the professor has the right auth to post announcement
            if(courses.isPresent() && courses.get().contains(courseId)) {
                Course course = (Course) dynamoDB.getItem("Courses", courseId);
                //add the announcement id into Courses table (annoucementIds list)
                Optional<Set<String>> announcementIds = Optional.of(course.announcementIds);
                Set<String> annIds = new HashSet<>();
                if(announcementIds.isPresent()) {
                    annIds = announcementIds.get();
                } 
                annIds.add(announcement.id);
                course.setAnnouncementIds(annIds);
                dynamoDB.addOrUpdateItem(course);
                //add new announcement to Announcements table
                dynamoDB.addOrUpdateItem(announcement);
                return Response.ok().build();
            }
        }
        return Response.serverError().build();
    }
    
    @GET
    @Path("{courseId}/students")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<Student>> getStudentListforCourse(@PathParam("professorId")String professorId, @PathParam("courseId")String courseId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Professor> professor = Optional.of((Professor) dynamoDB.getItem("Professors", professorId));
        if (professor.isPresent()) {
            Optional<Set<String>> courses = Optional.of(professor.get().courseIds);
            //has the right permission to get this list
            if(courses.isPresent() && courses.get().contains(courseId)) {
                Course course = (Course) dynamoDB.getItem("Courses", courseId);
                Optional<Set<String>> studentIds = Optional.of(course.studentIds);
                if(studentIds.isPresent() && !studentIds.get().isEmpty()) {
                    Set<String> ids = studentIds.get();
                    Set<Student> result = new HashSet<>();
                    for(String id: ids) {
                        Student student = (Student) dynamoDB.getItem("Student", id);
                        result.add(student);
                        return Optional.of(result);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
