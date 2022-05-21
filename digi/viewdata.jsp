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
	String sql = "select * from contact where userid=" + userid + " order by id";
	Statement statment = connection.createStatement();
	ResultSet rs = statment.executeQuery(sql);
	boolean printed = false;
	%>
	<div class="table-responsive ">
		<TABLE class="table table-striped table-bordered">
	
			<%
			while (rs.next()) {
				if (!printed) {
					printed = true;
			%>
					<thead class="thead-dark">
				<TH>ID</TH>
				<TH>Name</TH>
				<TH>Phone number</TH>
				<TH>Email</TH>
				<TH>Job</TH>
				<TH>Age</TH>
				<TH>Date of Birth</TH>
				<th>Update</th>
				<th>Export</th>
				<th>Delete</th>
			</thead>
			<% } %>
			<TR id="row-<%=rs.getInt(1)%>">
				<TD><%=rs.getInt(1)%></TD>
				<TD><%=rs.getString(2)%></TD>
				<TD><%=rs.getString(3)%></TD>
				<TD><%=rs.getString(4)%></TD>
				<TD><%=rs.getString(5)%></TD>
				<TD><%=rs.getInt(6)%></TD>
				<TD><%=rs.getString(7)%></TD>
				<td><a class="btn btn-warning" href="updatecon.jsp?id=<%=rs.getInt(1)%>">Update</a></td>
				<td><a class="btn btn-info" href="importcontact?id=<%=rs.getInt(1)%>">Export</a></td>
				<td><button class="btn btn-danger" onclick="deleteContactFunction(<%=rs.getInt(1)%>)">Delete</button></td>
			</TR>
			<%
			}
			if (!printed) {
				out.println("<h1 class='text-center'>No contacts to display<h1>");
			}
			%>
			
		</TABLE>
	</div>

	<script>
		function deleteContactFunction(id) {
			let http = new XMLHttpRequest();
			console.log(id);
			let url = "http://localhost:8085/digi/deletecon?id="+ id;
			console.log(url);
			http.open('GET', url, true);

			http.send();
			http.onreadystatechange = function() {
    			if(http.readyState == 4 && http.status == 200) {
        			console.log("success", http.responseText);
    				document.getElementById("row-" + id).remove();
			    } else {
			    	console.log(http.responseText);
			    }
			}
		}
	</script>

</body>
</html>