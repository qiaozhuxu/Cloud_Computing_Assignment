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
 * For program class, there are
 * 1.programId
 * 2.programName 
 * 3. courseId
 * 4. list of enrolled student's ids 
 * And it is rarely write. And read >> write 
 * we need to update Programs table when 
 * 1. enroll new students (update the student's list in Program table  &  create a student record in Students table )
 * 2. offer new courses or delete new courses (update course will use courseAPIs instead of programAPIs)
 *
 */
@DynamoDBTable(tableName = "Programs")
public class Program extends BasicDBObject {
    //set of courseIds
    public Set<String> courseIds;
    //set of studentIds
    public Set<String> studentIds;
    //set of professorIds
    public Set<String> professorIds;
    
    @DynamoDBHashKey(attributeName = "ProgramId")
    public String getId() { return this.id; } 
    public void setId(String programId) { this.id = programId; } 
    
    @DynamoDBAttribute(attributeName = "ProgramName")
    public String getName() { return this.name; }
    public void setName (String name) { this.name = name; }
    
    @DynamoDBAttribute(attributeName = "Courses")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getCourses() {
        if(this.courseIds == null)
            this.courseIds = new HashSet<>();
        return this.courseIds; 
    }
    
    public void setCourses(Set<String> courseIds) { this.courseIds = courseIds; } 
    
    @DynamoDBAttribute(attributeName = "Students")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getStudents() {
        if(this.studentIds == null)
            this.studentIds = new HashSet<>();
        return this.studentIds; 
    }
    
    public void setStudents(Set<String> studentIds) {
        this.studentIds = studentIds; 
    } 
    
    @DynamoDBAttribute(attributeName = "Professors")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getProfessors() {
        if(this.professorIds == null)
            this.professorIds = new HashSet<>();
        return this.professorIds; 
    }
    
    public void setProfessors(Set<String> professorIds) {
        this.professorIds = professorIds; 
    } 
}
