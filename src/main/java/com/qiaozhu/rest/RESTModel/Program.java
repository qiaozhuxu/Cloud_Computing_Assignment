package com.qiaozhu.rest.RESTModel;

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
@XmlRootElement
public class Program {
    @XmlElement(name="programName")
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
    @XmlElement(name="activeCourse")
    Set<Course> activeCourse;
    int currentId;
    
    public void addCourse(Course courseToAdd) {
        activeCourse.add(courseToAdd);
    }
}
