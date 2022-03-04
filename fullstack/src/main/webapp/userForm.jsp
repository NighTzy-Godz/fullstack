<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

 <%@ taglib url="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<c:if test="${user != null}">
	<form action="update" method="POST">
</c:if>

<c:if test="${user == null}">
	<form action="insert" method="POST">
</c:if>

	</form>
</body>
</html>