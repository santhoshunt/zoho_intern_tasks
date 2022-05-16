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
	int id = -1, serviceID = -1;
	try {

		id = Integer.parseInt(request.getParameter("id"));
		serviceID = Integer.parseInt(request.getParameter("serviceID"));	
	} catch (Exception e) {
		response.sendRedirect("error/dontplaywithurl.jsp");
	}
	String sql = "select * from service join account on service.serviceid = account.serviceid where account.id=" + id;
	Statement statment = connection.createStatement();
	ResultSet rs = statment.executeQuery(sql);
	String usernameVar = "", passwordVar = "", servicenameVar = "", urlVar = "";
	int serviceidVar = -1;
	if (rs.next()) {
		serviceidVar = rs.getInt(1);
		servicenameVar = rs.getString(2);
		urlVar = rs.getString(3);
		usernameVar = rs.getString(6);
		passwordVar = rs.getString(7);
	}
	%>
	<%-- id, name, phoneNumber, email, job, age, dob --%>
	<div class="container p-5">
		<form method="post" action="updateService">
			<div class="form-group mb-3">
				<label for="servicename" class="form-label">Name of the service</label> <input
					class="form-control" type="text" name="servicename"
					value="<%=servicenameVar%>">
			</div>
			<div class="form-group mb-3">
				<label for="username" class="form-label">User name</label> <input type="text"
					class="form-control" name="username" value="<%=usernameVar%>">
			</div>
			<div class="form-group mb-3">
				<label for="password"  class="form-label">Password</label> <input type="text"
					name="password" class="form-control" value="<%=passwordVar%>">
			</div>
			<div class="form-group mb-3">
				<label for="url" class="form-label">URL / Link of service</label> <input type="url"
					name="url" class="form-control" value="<%=urlVar%>">
			</div>
			<div class="form-group">
				<input type="hidden" name="serviceid" class="form-control"
					value="<%=serviceID%>">
			</div>
			<div class="form-group">
				<input type="hidden" name="id" class="form-control" value="<%=id%>">
			</div>
			<div class="form-group text-center mt-3">
				<input type="submit" class="btn btn-primary">
			</div>
		</form>
	</div>
</body>
</html>