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
	<%
	String key = request.getParameter("key").toString().toLowerCase();
	String field = request.getParameter("filterby").toString();
	StringBuilder filterContact = new StringBuilder(
			"select * from account join service on account.serviceid=service.serviceid where userid=" + userid + " and ");
	if (field.equals("servicename")) {
		filterContact.append("lower(service.servicename) LIKE '%" + key + "%'");
	} else if (field.equals("username")) {
		filterContact.append("lower(account.username) LIKE '%" + key + "%'");
	} else {
		filterContact.append("lower(service.serviceurl) LIKE '%" + key + "%'");
	}
	PreparedStatement pstm = connection.prepareStatement(filterContact.toString());
	ResultSet rs = pstm.executeQuery();
	%>
	<TABLE class="table table-responsive table-striped table-bordered">
		
		<%
		boolean printed = false;
		while (rs.next()) {
			if (!printed) { %>
				<thead class="thead-dark">
			<th>Service ID</th>
			<th>Service Name</th>
			<th>User Name</th>
			<th>password</th>
			<th>Service URL</th>
			<th>Update</th>
			<th>Delete</th>
		</thead>
		<%
			printed = true;
			}
		%>
		<tr>
			<td><%=rs.getInt(1)%></td>
			<td><%=rs.getString(6)%></td>
			<td><%=rs.getString(3)%></td>
			<td><%=rs.getString(4)%></td>
			<td><%=rs.getString(7)%></td>
			<td><a class="btn btn-warning"
				href="updateserv.jsp?id=<%=rs.getInt(1)%>&&serviceID=<%=rs.getInt(2)%>">Update</a></td>
			<td><a class="btn btn-danger"
				href="deleteservice?id=<%=rs.getInt(1)%>&&serviceID=<%=rs.getInt(2)%>">Delete</a></td>
		</tr>
		<%

		}
		if (!printed) {
			out.println("<h1 class='m-5 text-center'> No result found</h1>");
		}
		%>
	</TABLE>
</body>