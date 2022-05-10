<%@page import="java.net.http.HttpClient.Redirect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="servletclass.ConnectionManager, java.sql.*"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="/header.jsp"%>
</head>
<body>
	<%
	if (connection != null) {
		session.setAttribute("connection", connection);
		session.setAttribute("connected", true);
		
	%>
	<%@ include file="navbar.jsp"%>

	<div class="container text-center p-3">
		<div class="row mt-5 mb-4">
			<div class="col">
				<h1>Contacts</h1>
			</div>
			<div class="col">
				<h1>Services</h1>
			</div>
		</div>
		<div class="row my-4">
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-primary" href="viewdata.jsp">&emsp;View
					all contacts&emsp;</a>
			</div>
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-primary" href="viewservices.jsp">&emsp;View
					All Accounts&emsp;</a>
			</div>
		</div>
		<div class="row my-4">
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-success" href="insertContact.jsp">&emsp;&emsp;Add
					contact&emsp;&emsp;</a>
			</div>
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-success" href="insertService.jsp">&emsp;&emsp;Add
					Account&emsp;&emsp;</a>
			</div>
		</div>
		<div class="row my-4">
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-warning"
					href="filtercontact.jsp">&emsp;&emsp;Filter search&emsp;&emsp;</a>
			</div>
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-warning"
					href="filterservice.jsp">&emsp;&emsp;Filter Search&emsp;&emsp;</a>
			</div>
		</div>
		<!-- <div class="row my-4">
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-outline-info"
					href="importcon.jsp">Import Contacts from<br>CSV file</a>
			</div>
			<div class="col mx-5">
				<a class="btn btn-block btn-lg btn-outline-info"
					href="importserv.jsp">Import Accounts from<br>CSV file</a>
			</div>
		</div> -->
	</div>
	<%
	} else {
		session.setAttribute("connected", false);
		response.sendRedirect("errorPage");
	}
	%>
</body>
</html>