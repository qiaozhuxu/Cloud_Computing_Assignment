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
 * For Professor class, there are
 * 1.professorId(immutable)
 * 2.professorName (immutable)
 * 3.url with professor personal information(these will be stored as url,url is immutable. But the information may change)
 * 4.email (immutable)
 * 5. programId(immutable)
 * 6.list of teaching courses (may no long teaching or start offering new courses)
 * 
 * we need to update Professors table when 
 * 1. enroll new professor/delete specific professor (update the professors' list in Program table & create a professor record in Professors table)
 * 2. open new courses (create new course item in courses table or update the professorId for existing course & update the courses list in Professors)
 * 3. no longer teaching specific course (update the courses list in Professors table, change Courses table professorId with new ProfessorId)
 * 
 * we do not need to update this table when only update the personal information stored in url
 */
@DynamoDBTable(tableName = "Professors")
public class Professor extends BasicDBObject {
    public String email;
    public Set<String> courseIds;
    public String programId;
    
    @DynamoDBHashKey(attributeName = "ProfessorId")
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    
    @DynamoDBAttribute(attributeName = "ProfessorName")
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
    
    @DynamoDBAttribute(attributeName = "Courses")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getCourses() {
        if(this.courseIds == null)
            this.courseIds = new HashSet<>();
        return this.courseIds;
    } 
    
    public void setCourses(Set<String> courseIds) { 
        this.courseIds = courseIds; 
    }
}
