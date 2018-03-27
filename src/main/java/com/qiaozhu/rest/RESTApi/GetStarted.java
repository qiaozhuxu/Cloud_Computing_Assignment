package com.qiaozhu.rest.RESTApi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class GetStarted {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAPIs() {
        return "// StudentSelfPortal\n" +
                "GET /student/{studentId}\n" + 
                "GET /student/{studentId}/myCourses\n" +
                "GET /student/{studentId}/courses\n" +
                "GET /student/{studentId}/{courseId}/announcements\n" +
                "GET /student/{studentId}/{courseId}/lectures\n" +
                "PUT /student/{studentId}\n" +
                "\n" +
                
                "// ProfessorSelfPortal\n" +
                "GET /professor/{professorId}\n" + 
                "PUT /professor/{professorId}\n" +
                "GET /professor/{professorId}/myCourses\n" +
                "GET /professor/{professorId}/{courseId}/announcements\n" +
                "GET /professor/{professorId}/{courseId}/lectures\n" +
                "GET /professor/{professorId}/{courseId}/students\n" +
                "POST /professor/{professorId}/{courseId}/announcement\n" +

                "\n" +
                
                "// AdminPortal + Student Management\n"+
                "GET /admin/student/students\n" +
                "GET /admin/student/{studentId}\n" + 
                "PUT /admin/student/{studentId}\n" +
                "POST /admin/student\n" +
                "DELETE /admin/student/{studentId}\n" +
                "\n" +
                
                "// AdminPortal + Professor Management\n"+
                "GET /admin/professor/professor\n" +
                "GET /admin/professor/{professorId}\n" + 
                "PUT /admin/professor/{professorId}\n" +
                "POST /admin/professor\n" +
                "DELETE /admin/professor/{professorId}\n" +
                "\n" +
                
                "// AdminPortal + Program Management\n" +
                "GET /admin/program/programs\n" + 
                "GET /admin/program/{programId}\n" + 
                "POST /admin/program\n" +
                "PUT /admin/program\n" +
                "DELETE /admin/program/{programId}\n" +
                "\n" +
                
                "// AdminPortal + Course Management\n" +
                "GET /admin/course/courses\n" + 
                "GET /admin/course/{courseId}\n" + 
                "POST /admin/course/{courseId}\n" +
                "PUT /admin/course\n" +
                "DELETE /admin/course/{courseId}\n" +
                "\n" +
                
                "// AdminPortal + Register/unregister courses for student/professor\n" +
                "PUT /admin/register/st/stid={studentId}&coid={coursesId}\n" +
                "PUT /admin/register/pr/prid={professorId}&coid={coursesId}\n" +
                "\n";
    } 
}

