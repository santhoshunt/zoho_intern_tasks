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
	Connection connection = (Connection) session.getAttribute("connection");
	String sql = "SELECT * FROM account JOIN service ON account.serviceid = service.serviceid order by account.id";
	Statement statment = connection.createStatement();
	ResultSet rs = statment.executeQuery(sql);
	%>
	<div>
		<TABLE class="table table-responsive table-striped table-bordered">
			<thead class="thead-dark">
				<th>Service ID</th>
				<th>Service Name</th>
				<th>User Name</th>
				<th>password</th>
				<th>Service URL</th>
				<th>Update</th>
				<th>Delete</th>
				<th>Delete</th>
			</thead>
			<%
			while (rs.next()) {
			%>
			<tr id="row-<%=rs.getInt(1)%>">
				<td><%=rs.getInt(1)%></td>
				<td><%=rs.getString(6)%></td>
				<td><%=rs.getString(3)%></td>
				<td><%=rs.getString(4)%></td>
				<td><%=rs.getString(7)%></td>
				<td><a class="btn btn-warning" href="updateserv.jsp?id=<%=rs.getInt(1)%>&&serviceID=<%=rs.getInt(2)%>">Update</a></td>
				<td><a class="btn btn-danger" href="deleteservice?id=<%=rs.getInt(1)%>&&serviceID=<%=rs.getInt(2)%>">Delete</a></td>
				<td>
					<button class="btn btn-danger" onclick="deleteService(<%=rs.getInt(1)%> , <%=rs.getInt(2)%>)">Delete</button>
				</td>
			</tr>
			<%
			}
			%>
		</TABLE>
	</div>
	<script type="text/javascript">

		function deleteService(id, serviceId) {
			let http = new XMLHttpRequest();
			console.log(id, serviceId);
			let url = "http://localhost:8080/digitalvault/deleteservice?id="+ id + "&&serviceID=" + serviceId;
			http.open('GET', url, true);

			http.onreadystatechange = function() {
    			if(http.readyState == 4 && http.status == 200) {
        			console.log("success", http.responseText);
    				document.getElementById("row-" + id).remove();
			    } else {
			    	console.log(http.responseText);
			    }
			}
			http.send();
		}
	</script>

</body>
</html>