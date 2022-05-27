<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<h1>Error</h1>
	<%
		out.println(request.getUserPrincipal());
		out.println( request.toString());
	%>
</body>
</html>