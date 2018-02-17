package com.qiaozhu.rest.RESTModel;

import java.util.HashMap;
import java.util.Set;

import lombok.Data;

@Data
public class Course {
    //16Fall
    String semesterName;
    //CRN CYSN62225
    String CRN;
    //CloudComputing
    String courseName;
    //Jami
    Teacher teacher;
    //maybe null
    Student ta;
    //4 by default
    int credit;

    Set<Student> enrolledStudents;
    Set<Student> waitingList;

    int spotTotal;

    HashMap<String, Lecture> lectures;
    HashMap<String , Assignment> assignments;

}
