package com.qiaozhu.rest.RESTModel;

import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.qiaozhu.rest.DynamoDBDelegator.DynamoDBConverter;

@DynamoDBTable(tableName = "Lectures")
public class Lecture extends BasicDBObject{
    public Set<String> noteIds;
    public Set<String> materialIds;
    
    @DynamoDBHashKey(attributeName = "LectureId")
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    
    @DynamoDBAttribute(attributeName = "LectureName")
    public String getName() { return this.name; }
    public void setName(String topic) { this.name = topic; }
    
    @DynamoDBAttribute(attributeName = "Notes")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getNotes() { 
        if(this.noteIds == null)
            this.noteIds = new HashSet<>();
        return this.noteIds;
    } 
    public void setNotes(Set<String> noteIds) { this.noteIds = noteIds; }
    
    @DynamoDBAttribute(attributeName = "Materials")
    @DynamoDBTypeConverted(converter = DynamoDBConverter.class)
    public Set<String> getMaterialIds() { 
        if(this.materialIds == null)
            this.materialIds = new HashSet<>();
        return this.materialIds;
    } 
    public void setMaterialIds(Set<String> materialIds) { this.materialIds = materialIds; }
}
