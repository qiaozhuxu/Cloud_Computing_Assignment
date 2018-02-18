package com.qiaozhu.rest.RESTModel;

import java.io.File;
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
@XmlRootElement(name="assignment")
public class Assignment {
    String assignmentid;
    File assignment;
    int grade;
}
