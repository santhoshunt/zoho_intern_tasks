<%@page import="java.net.http.HttpClient.Redirect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="servletclass.ConnectionManager, java.sql.*"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<form action="filterServ.jsp" method="post" class="m-3">
	<div class="form-group mb-4">
			<label for="key" class="form-label">Enter the search key word</label> 
			<input name="key" class="form-control" type="text">
		</div>
		<div class="form-group mb-4">
			<label for="filterby">Please select the filter By option</label> <select
				class="form-select" name="filterby">
				<option value="servicename">Service</option>
				<option value="username">User name</option>
				<option value="url">Link / URL</option>
			</select>
		</div>
		<div class="form group text-center">
			<input type="submit" value="search" name="submit" class="btn btn-primary">
		</div>

	</form>

</body>