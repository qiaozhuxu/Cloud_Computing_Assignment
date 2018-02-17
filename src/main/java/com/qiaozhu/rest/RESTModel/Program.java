package com.qiaozhu.rest.RESTModel;

import java.util.Set;

import lombok.Data;

@Data
public class Program {
    String programName;
    int creditNeedToGraduate;
    Set<Course> requiredCourses;
    Set <Student> currentStudents;
    Set<Student> alumni;

    Set<Teacher> currentTeacher;
    //some teacher may left;
    Set<Teacher> totalTeacher;

    Set<Course> totalCourse;
    //some course may no longer available
    Set<Course> activeCourse;
    int currentId;
}
