package com.qiaozhu.rest.RESTApi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.qiaozhu.rest.DynamoDBDelegator.DynamoDB;
import com.qiaozhu.rest.RESTModel.Announcement;
import com.qiaozhu.rest.RESTModel.Course;
import com.qiaozhu.rest.RESTModel.Lecture;
import com.qiaozhu.rest.RESTModel.Program;
import com.qiaozhu.rest.RESTModel.Student;
/**
 * @author Serena
 * student and professor share some common actions
 * 1. get personal information 
 * 2. update personal information
 * 3. get list of courses registered/teaching
 * 4. get announcements for specific course(courseId)
 * 5. get letures for specific course(courseId)
 * 6. get notes for specific course(courseId)
 * 
 * Other than these, student can get courses list in program
 */
@Singleton
@Path("student/{studentId}")
public class StudentSelfPortal implements SelfPortal<Student>{
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Student> getPersonalInformation(@PathParam("studentId") String studentId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        return Optional.of((Student)dynamoDB.getItem("Students", studentId));
    }
    
    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updatePersonalInformation(@PathParam("studentId")String studentId, Student updateEntity) {
        // TODO Auto-generated method stub
    }
    
    @Override
    @GET
    @Path("myCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<String>> getMyCourses(@PathParam("studentId")String studentId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem("Students", studentId));
        if (student.isPresent()) {
            if(student.get().getCourses() != null) {
                return Optional.of(student.get().getCourses());
            }
        }
        return Optional.empty();
    }
    
    @Override
    @GET
    @Path("{courseId}/announcements")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<String>> getAnnouncementsforCourse(@PathParam("studentId")String studentId, @PathParam("courseId")String courseId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem("Students", studentId));
        if (student.isPresent()) {
            Optional<Set<String>> courses = Optional.of(student.get().courseIds);
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
    public Optional<Set<String>> getLecturesForCourse(@PathParam("studentId")String studentId, @PathParam("courseId")String courseId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem("Students", studentId));
        if (student.isPresent()) {
            Optional<Set<String>> courses = Optional.of(student.get().courseIds);
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
    
    @GET
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<String>> getProgramCourses(@PathParam("studentId")String studentId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Student> student = Optional.of((Student) dynamoDB.getItem("Students", studentId));
        if (student.isPresent()) {
            Optional<String> programId = Optional.of(student.get().programId);
            if(programId.isPresent()) {
                Program program = (Program) dynamoDB.getItem("Programs", programId.get());
                Set<String> courseIds = program.courseIds;
                Set<String> result = new HashSet<>();
                for(String id: courseIds) {
                    Course course = (Course) dynamoDB.getItem("Courses", id);
                    result.add(course.name);
                    return Optional.of(result);
                }
            }
        }
        return Optional.empty();
    }
}
