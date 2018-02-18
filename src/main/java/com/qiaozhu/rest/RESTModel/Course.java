package com.qiaozhu.rest.RESTModel;

import java.util.HashMap;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@XmlRootElement(name="course")
public class Course {
    @XmlElement(name="courseName")
    String courseName;
    //Jami
    @XmlElement(name="teacher")
    Teacher teacher;
    //maybe null
    Student ta;
    //4 by default
    @XmlElement(name="credit")
    int credit;

    Set<Student> enrolledStudents;
    Set<Student> waitingList;

    int spotTotal;

    HashMap<String, Lecture> lectures;
    HashMap<String , Assignment> assignments;

}
