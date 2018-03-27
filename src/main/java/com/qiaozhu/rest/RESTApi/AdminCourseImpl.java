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
import com.qiaozhu.rest.RESTModel.Lecture;
import com.qiaozhu.rest.RESTModel.Professor;
import com.qiaozhu.rest.RESTModel.Program;
import com.qiaozhu.rest.RESTModel.Student;
@Singleton
@Path("course")
public class AdminCourseImpl implements AdminGeneralInterface<Course> {
    @Override
    @GET
    @Path("{programId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Set<Course>> getAllForProgram(@PathParam("programId")String programId) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Program program = (Program)dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, programId);
        if(!program.courseIds.isEmpty()) {
            Set<String> ids = program.courseIds;
            Set<Course> courses = new HashSet<>();
            for(String id: ids) {
                Course course = (Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, id);
                courses.add(course);
            }
            return Optional.of(courses);
        }
        return Optional.empty();
    }

    @Override
    @GET
    @Path("{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Course> getOne(@PathParam("courseId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Course> course = Optional.of((Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, id));
        return course;
    }

    @Override
    @PUT
    @PathParam("courseId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Course> update(String id, Course entity) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Course> course = Optional.of((Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, id));
        if(!course.isPresent()) {
            return Optional.empty();
        }
        dynamoDB.addOrUpdateItem(course.get());
        return course;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Course> create(Course newCourse) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Course> course = Optional.of((Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, newCourse.id));
        if(course.isPresent()) {
            return Optional.empty();
        }
        //add new course in Courses table
        dynamoDB.addOrUpdateItem(newCourse);
        //enroll new Courses in certain program, these two steps happen at the same time
        Optional<String> newId = Optional.of(newCourse.id);
        if (newId.isPresent()) {
            Optional<Program> program = Optional.of((Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, newId.get()));
            program.get().courseIds.add(newId.get());
            dynamoDB.addOrUpdateItem(program.get());
            return Optional.of(newCourse);
        }
        return Optional.empty();
    }

    @Override
    @DELETE
    @PathParam("courseId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("courseId")String id) {
        DynamoDB dynamoDB = DynamoDB.getInstance();
        Optional<Course> course = Optional.of((Course) dynamoDB.getItem(Constants.COURSE_TABLE_NAME, id));
        if(course.isPresent()) {

            //delete course from program
            Optional<String> programId = Optional.of(course.get().programId);
            if(programId.isPresent() && programId.get() != "") {
                Program program = (Program) dynamoDB.getItem(Constants.PROGRAM_TABLE_NAME, programId.get());
                program.courseIds.remove(id);
                dynamoDB.addOrUpdateItem(program);
            }
            //delete corresponding Students
            Optional<Set<String>> studentIds = Optional.of(course.get().studentIds);
            if(studentIds.isPresent() && !studentIds.get().isEmpty()) {
                for(String stuId : studentIds.get()) {
                    Student student = (Student) dynamoDB.getItem(Constants.STUDENT_TABLE_NAME, stuId);
                    student.courseIds.remove(id);
                    dynamoDB.addOrUpdateItem(student);
                }
            }
            //delete corresponding Lectures(->Notes)
            Optional<Set<String>> lectureIds = Optional.of(course.get().lectureIds);
            if(lectureIds.isPresent() && !lectureIds.get().isEmpty()) {
                for(String lectureId : lectureIds.get()) {
                    Lecture lecture = (Lecture) dynamoDB.getItem(Constants.LECTURE_TABLE_NAME, lectureId);
                    
                    Optional<Set<String>> notesIds = Optional.of(lecture.noteIds);
                    if(notesIds.isPresent() && !notesIds.get().isEmpty()) {
                        for(String noteId : notesIds.get()) {
                            dynamoDB.deleteItem(Constants.NOTE_TABLE_NAME, noteId);;
                        }
                    }
                    dynamoDB.deleteItem(Constants.LECTURE_TABLE_NAME, lectureId);
                }
            }
            //delete corresponding announcement
            Optional<Set<String>>  announcementIds = Optional.of(course.get().announcementIds);
            if(announcementIds.isPresent() && !announcementIds.get().isEmpty()) {
                for(String announcementId : announcementIds.get()) {
                    dynamoDB.deleteItem(Constants.ANNOUNCEMENT_TABLE_NAME, announcementId);
                }
            }
            //delete from course list for specific professor
            Optional<String> professorId = Optional.of(course.get().professorId);
            if(professorId.isPresent() && professorId.get() != "") {
                Professor professor = (Professor) dynamoDB.getItem(Constants.PROFESSOR_TABLE_NAME, professorId.get());
                professor.courseIds.remove(id);
                dynamoDB.addOrUpdateItem(professor);
            }
        }
    }
}

