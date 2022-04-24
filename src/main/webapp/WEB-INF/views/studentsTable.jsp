<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Students Table</title>
</head>
<body>
 <table class = "table table-bordered">

	<thead> 
		<tr> 
			<th scope = "col">id </th>
			<th scope = "col">name </th>
			<th scope = "col">age </th>
		</tr>
	</thead>

<tbody>
<c:forEach var = "student" items = "${StudentsList}">
<tr> 
	<td>${student.studentID}</td>
	<td>${student.name}</td>
	<td>${student.age}</td>
</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>