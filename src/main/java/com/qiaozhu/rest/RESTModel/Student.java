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
 * For Student class, there are
 * 1.studentId(immutable)
 * 2.studentName (immutable)
 * 3.url with student personal information(these will be stored as url,url is immutable. But the information may change)
 * 4.email (immutable)
 * 5. programId(immutable)
 * 6.list of registered courses(update the students' list in Courses table & update the courseList in Students table)
 * 
 * we need to update Students table when 
 * 1. enroll new student/delete specific student (update the student's list in Program table & create a student record in Students table)
 * 2. register new courses (update students's list in courses table & update the courses list in students)
 * 
 * we do not need to update this table when only update the personal information stored in url
 */
@DynamoDBTable(tableName = "Students")
public class Student extends BasicDBObject {
    public String email;
    public Set<String> courseIds;
    public String programId;

    
    @DynamoDBHashKey(attributeName = "StudentId")
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id;}
    
    @DynamoDBAttribute(attributeName = "StudentName")
    public String getName() { return this.name; } 
    public void setName(String name) { this.name = name; }
    
    @DynamoDBAttribute(attributeName = "personalInformation")
    public String getUrl() { return this.url; }
    public void setUrl(String url) { this.url = url; }
    
    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    
    @DynamoDBHashKey(attributeName = "programEnrolled")
    public String getProgramId() { return this.programId; }
    public void setprogramId(String programId) { this.programId = programId;}
    
    @DynamoDBAttribute(attributeName = "CourseIds")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getCourses() {
        if(this.courseIds == null)
            this.courseIds = new HashSet<>();
        return this.courseIds;
    }
    
    public void setCourses(Set<String> courseIds) {
        if(courseIds.size() == 0) {
            this.courseIds = null;
            return;
        }
        this.courseIds = courseIds;
    }
    

}
