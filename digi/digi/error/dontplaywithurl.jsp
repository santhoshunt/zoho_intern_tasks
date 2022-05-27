<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../header.jsp"%>
</head>
<body>
<%@ include file="../navbar.jsp"%>
	<div class="container text-center mt-5">
		<h1>Please Don't play with the url &#128530;</h1>
		<% response.setHeader("Refresh", "5;url=/digi/home.jsp"); %>
		<h3 class="mt-5">You will be automatically redirected to home page in 5 seconds!</h3>
	</div>
</body>
</html>