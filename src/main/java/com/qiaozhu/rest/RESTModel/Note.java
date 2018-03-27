package com.qiaozhu.rest.RESTModel;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
@DynamoDBTable(tableName = "Notes")
public class Note extends BasicDBObject {
    public Date postDate;
    

    @DynamoDBHashKey(attributeName = "NoteId")
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    
    @DynamoDBAttribute(attributeName = "NoteName")
    public String getName() { return this.name; } 
    public void setName(String name) { this.name = name; }
    
    @DynamoDBAttribute(attributeName = "noteUrl")
    public String getUrl() { return this.url; } 
    public void setUrl(String url) { this.url = url; }
    
    @DynamoDBAttribute(attributeName = "PostDate")
    public Date getDate() { return this.postDate; } 
    public void setDate(Date postDate) { this.postDate = postDate; }
}
