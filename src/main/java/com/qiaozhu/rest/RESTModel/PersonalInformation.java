package com.qiaozhu.rest.RESTModel;

import java.util.Date;
import java.util.HashSet;
import javax.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class PersonalInformation implements Cloneable{

    @XmlElement(name="id")
//  @Setter(AccessLevel.NONE)
    private String id;
    
    @NonNull
    @XmlElement(name="type")
    protected Type type;
    
    //personal information, required and should not be changed after creation
    @NonNull
    @XmlElement(name="firstName")
    String firstName;

    @NonNull
    @XmlElement(name="lastName")
    String lastName;


    
    @XmlElement(name="image")
    String imageUrl;
    
    @XmlElement(name="programName")
    String programName;
    
    @XmlElement(name="coursesRegistered")
    HashSet<String> coursesRegistered;
    
    Date DOB;

    enum Gender {
        FEMALE, MALE;
    }
    Gender gender;
    //personal information, can  be changed later
    String email;

    enum Type {
        STUDENT(Constants.STUDENT),PROFESSOR(Constants.PROFESSOR),STAFF(Constants.STAFF);
        private String type;
        
        Type(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }
    }
    
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}
