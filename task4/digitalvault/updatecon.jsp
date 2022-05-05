<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<%
	int id = -1;
	boolean redirect = false;
	try {
		id = Integer.parseInt(request.getParameter("id"));
	} catch (Exception e) {
		redirect = true;
	}
	Connection connection = (Connection) session.getAttribute("connection");
	String sql = "select * from contact where id=" + id;
	Statement statment = connection.createStatement();
	ResultSet rs = statment.executeQuery(sql);
	String nameVar = "", phoneNumberVar = "", emailVar = "", dobVar = "", jobVar = "";
	int ageVar = -1;
	if (rs.next()) {
		nameVar = rs.getString(2);
		phoneNumberVar = rs.getString(3);
		emailVar = rs.getString(4);
		jobVar = rs.getString(5);
		ageVar = rs.getInt(6);
		dobVar = rs.getString(7);
	} else {
		redirect = true;
	}
	if (redirect) {
		response.sendRedirect("error/dontplaywithurl.jsp");
	}
	%>
	<%-- id, name, phoneNumber, email, job, age, dob --%>
	<div class="container p-3">
		<form method="post" action="updateContact">
			<input type="hidden" name="id" value="<%=id%>">
			<div class="form-group">
				<label for="name" class="form-label">Name</label> <input
					class="form-control" type="text" name="name" value=<%=nameVar%>>
			</div>
			<div class="form-group">
				<label for="phone" class="form-label">Phone number</label> <input
					class="form-control" type="text" value=<%=phoneNumberVar%>
					name="phone">
			</div>
			<div class="form-group">
				<label for="email" class="form-label">Email</label> <input
					type="email" name="email" class="form-control" value=<%=emailVar%>>
			</div>
			<div class="form-group">
				<label for="job" class="form-label">Job</label> <input type="text"
					name="job" class="form-control" value="<%out.println(jobVar);%>">
			</div>
			<div class="form-group">
				<label for="age" class="form-label">age</label><input type="number"
					name="age" class="form-control" value=<%=ageVar%>>
			</div>
			<div class="form-group">
				<label for="dob" class="form-label">Date Of Birth</label><input
					type="date" name="dob" class="form-control" value=<%=dobVar%>>
			</div>
			<div class="form-group text-center mt-3">
				<input type="submit" class="btn btn-primary">
			</div>
		</form>
	</div>
</body>
</html>