package com.qiaozhu.rest.RESTModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasicDBObject {
    public String id;
    public String name;
    public String url;
}
