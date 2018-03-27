package com.qiaozhu.rest.RESTModel;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Announcements")
public class Announcement extends BasicDBObject{
    public Date postDate;
    public String content;
    public String courseId;
    
    @DynamoDBHashKey(attributeName = "AnnouncementId")
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id;}
    
    @DynamoDBHashKey(attributeName = "AnnouncementTitle")
    public String getTitle() { return this.name; }
    public void setTitle(String title) { this.name = title;}

    @DynamoDBHashKey(attributeName = "CourseId")
    public String getCourseId() { return this.courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId;}
    
    @DynamoDBAttribute(attributeName = "postDate")
    public Date getpostDate() { return this.postDate; }
    public void setpostDate(Date postDate) { this.postDate = postDate; }
    
    @DynamoDBAttribute(attributeName = "Content")
    public String getContent() { return this.content; }
    @DynamoDBAttribute(attributeName = "Content")
    public void setContent(String content) { this.content = content; }

}
