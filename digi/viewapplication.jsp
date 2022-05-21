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
	<%@ include file="navbar.jsp"%>
		<%
	
		String sql = "SELECT * FROM account JOIN service ON account.serviceid = service.serviceid where service.userid=" + userid +" and service.servicename='okta' order by account.id";
		Statement statment = connection.createStatement();
		ResultSet rs = statment.executeQuery(sql);
		boolean printed = false;
	
	%>
	<div>
		<TABLE class="table table-responsive table-striped table-bordered">
			
			<%
			while (rs.next()) {
				if (!printed) {
					printed = true;
					%>
			<thead class="thead-dark">
				<th>Service ID</th>
				<th>Service Name</th>
				<th>User Name</th>
				<th>password</th>
				<th>Service URL</th>
				<th>Update</th>
				<th>Delete</th>
			</thead>
			
			<% } %>
			<tr id="row-<%=rs.getInt(1)%>">
				<td><%=rs.getInt(1)%></td>
				<td><%=rs.getString(6)%></td>
				<td><%=rs.getString(3)%></td>
				<td><%=rs.getString(4)%></td>
				<td><%=rs.getString(7)%></td>
				<td><a class="btn btn-warning" href="updateserv.jsp?id=<%=rs.getInt(1)%>&&serviceID=<%=rs.getInt(2)%>">Update</a></td>
				<td>
					<button class="btn btn-danger" onclick="deleteService(<%=rs.getInt(1)%> , <%=rs.getInt(2)%>)">Delete</button>
				</td>
			</tr>
			<%
			}
			if (!printed) {
				out.println("<h1 class='text-center'>No Service accounts to display<h1>");
			}
			%>
	<script type="text/javascript">

		function deleteService(id, serviceId) {
			let http = new XMLHttpRequest();
			console.log(id, serviceId);
			let url = "http://localhost:8085/digi/deleteservice?id="+ id + "&&serviceID=" + serviceId;
			http.open('GET', url, true);

			http.onreadystatechange = function() {
    			if(http.readyState == 4 && http.status == 200) {
        			console.log("success", http.responseText);
    				document.getElementById("row-" + id).remove();
			    } else {
					console.log(http.readyState);
			    	console.log(http.responseText);
			    }
			}
			http.send();
		}
	</script>

</body>
</html>