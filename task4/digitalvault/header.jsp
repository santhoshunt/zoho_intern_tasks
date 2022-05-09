<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Digital vault</title>
<%@ page import="servletclass.ConnectionManager, java.sql.*"%>
<% 
	ConnectionManager conMan = new ConnectionManager();
	Connection connection = conMan.getConnection();
%>