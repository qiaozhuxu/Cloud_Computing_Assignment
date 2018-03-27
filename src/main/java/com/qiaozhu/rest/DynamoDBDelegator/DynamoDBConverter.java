package com.qiaozhu.rest.DynamoDBDelegator;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class DynamoDBConverter implements DynamoDBTypeConverter<String, HashMap<Integer, String>> {

    @Override
    public String convert(HashMap<Integer, String> map) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public HashMap<Integer, String> unconvert(String mapAsString) {
        // TODO Auto-generated method stub
        return new HashMap<Integer, String>();
    }

}
