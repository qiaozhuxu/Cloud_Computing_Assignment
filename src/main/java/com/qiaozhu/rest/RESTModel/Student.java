package com.qiaozhu.rest.RESTModel;

import java.io.File;
import java.util.Date;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@XmlRootElement
public class Student {
    //personal information, required and should not be changed after creation
    @NonNull
    @XmlElement(name="firstName")
    String firstName;

    @NonNull
    @XmlElement(name="lastName")
    String lastName;

    @XmlElement(name="id")
    int id;
    
    @XmlElement(name="image")
    String imageUrl;
    
    Date DOB;

    enum Gender {
        FEMALE, MALE;
    }
    Gender gender;
    //personal information, can  be changed later
    String email;
    String mailAddress;
    String homeAddress;
    String phoneNumber;

    //school related,can be changed afterwards
    Date EnrollDate;
    Date expectedEndDate;
    String programEnrolled;
    HashSet<Course> courseRegistered;

    enum Status {
        ACTIVE, INACTIVE;
    }
    Status status;

}
