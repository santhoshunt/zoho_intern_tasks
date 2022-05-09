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
		String filterContact = "SELECT * FROM contact WHERE lower(" + field + ") LIKE '%"+ key + "%'";
		PreparedStatement pstm = connection.prepareStatement(filterContact);
		ResultSet rs = pstm.executeQuery();
	%>
	<TABLE class="table table-responsive table-striped table-bordered mt-5 p-3">
			<thead class="thead-dark">
				<TH>ID</TH>
				<TH>Name</TH>
				<TH>Phone number</TH>
				<TH>Email</TH>
				<TH>Job</TH>
				<TH>Age</TH>
				<TH>Date of Birth</TH>
				<th>Update</th>
				<th>Delete</th>
			</thead>
			<%
			while (rs.next()) {
			%>
			<TR>
				<TD><%=rs.getInt(1)%></td>
				<TD><%=rs.getString(2)%></TD>
				<TD><%=rs.getString(3)%></TD>
				<TD><%=rs.getString(4)%></TD>
				<TD><%=rs.getString(5)%></TD>
				<TD><%=rs.getInt(6)%></TD>
				<TD><%=rs.getString(7)%></TD>
				<td><a class="btn btn-warning" href="updatecon.jsp?id=<%=rs.getInt(1)%>">Update</a></td>
				<td><a class="btn btn-danger" href="deletecon?id=<%=rs.getInt(1)%>">Delete</a></td>
			</TR>
			<%
			}
			%>
		</TABLE>
</body>