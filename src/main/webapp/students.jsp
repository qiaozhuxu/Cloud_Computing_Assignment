<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Students</title>
</head>
<body>
    <p> Students informations are as follows:</p>
    
    <table border ="1">
    	    <tr>
    	        <td align="left"> First Name:</td>
    	        <td> @{student.firstName}</td>
    	    </tr>
    	    <tr>
    	        <td align="left"> Last Name:</td>
    	        <td> @{student.lastName}</td>
    	    </tr>
    	    <tr>
    	        <td align="left"> Student Id:</td>
    	        <td> @{student.id}</td>
    	    </tr>
    </table>

</body>
</html>