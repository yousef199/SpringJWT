<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style type="text/css">
input{
margin-bottom: 40px;
}
</style>
<title>Welcome</title>
 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
	<form action="getStudents" id = "showStudents"></form>
	<div class = "form-group">
	<form action="addStudent"  id = "addStudent" method = "post">
		<div class = "col-md-7">
		<label for ="name" class = "form-label">Enter Name:</label>
		<input type = "text" name = "name" id = "name" class = "form-control">	<br>	
		</div>
		
		<div class = "col-md-7">
		<label for = "age" class = "form-label">Enter Age :</label>
		<input type = "number" name = "age" id = "age"class = "form-control"> <br>
		</div>
		
		<div>
		<input type = "submit" value = "Add Student" form = "addStudent" class = "btn btn-primary">
    	<input type="submit" value="Show Students"  form = "showStudents" class = "btn btn-secondary"/>
		</div>
		</form>
	</div>
</body>
</html>