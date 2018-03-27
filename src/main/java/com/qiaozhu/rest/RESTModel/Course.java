package com.qiaozhu.rest.RESTModel;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.qiaozhu.rest.DynamoDBDelegator.DynamoDBConverter;

/**
 * @author Serena
 * For Course class, there are
 * 1.courseId(immutable)
 * 2.courseName (immutable)
 * 3.url with course general information(these will be stored as url,url is immutable. But the information may change)
 * 5. programId(immutable)
 * 6. professorId(mutable)
 * 7.list of studentIds (mutable)
 * 8.list of announcementIds(announcement will be stored in Announcements table)
 * 9. list of lectureUrls(these will be stored as url, url is immutable)
 * 
 * we need to update Courses table when 
 * 1. open new courses (create new course item in courses table)
 * 2. create/delete announcement (update the announcements' list in Courses table & create a announcement record in Announcements table)
 * 3. when changing/adding professor
 * 4. register new student(update the students list in Courses table and update courses' list in Students table)
 * 
 * we do not need to update this table when only update the personal information stored in url
 * For the purpose of notifying students when announcement updated, professor need to delete announcement when add a new one(instead of update the original one)
 */
@DynamoDBTable(tableName = "Courses")
public class Course extends BasicDBObject{
    public String professorId;
    public Set<String> announcementIds;
    public Set<String> studentIds;
    public Set<String> lectureIds;
    public String programId;
    
    @DynamoDBHashKey(attributeName = "CourseId")
    public String getId() { return this.id; }
    public void setId(String courseId) { this.id =  courseId; } 
    
    @DynamoDBAttribute(attributeName = "CourseName")
    public String getCourseName() { return this.name; }
    public void setCourseName(String courseName) { this.name = courseName; }
    
    @DynamoDBAttribute(attributeName = "ProfessorId")
    public String getProfessorId() { return this.professorId; }
    public void setProfessorId(String professorId) { this.professorId = professorId; }
    
    @DynamoDBAttribute(attributeName = "announcementIds")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getAnnouncementIds() {
        if(this.announcementIds == null)
            return  new HashSet<>();
        return this.announcementIds;
    } 
    
    public void setAnnouncementIds(Set<String> announcementIds) {
        this.announcementIds = announcementIds;
    }
    
    @DynamoDBAttribute(attributeName = "StudentIds")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getStudentIds() {
        if(this.studentIds == null)
            this.studentIds = new HashSet<>();
        return this.studentIds;
    } 
    
    public void setStudentIds(Set<String> studentIds) {
        this.studentIds = studentIds; 
    }
    
    @DynamoDBAttribute(attributeName = "Lectures")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getLectureUrls() {
        if(this.lectureIds == null)
            return  new HashSet<>();
        return this.lectureIds;
    } 
    
    public void setLectureIds(Set<String> lectureIds) { 
        this.lectureIds = lectureIds; 
    }

}
