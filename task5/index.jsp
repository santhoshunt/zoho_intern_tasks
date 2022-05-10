<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-5 pt-3">
		<a class="navbar-brand" href="home.jsp"><h1>Digital Vault</h1></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="home.jsp">
					Home
				</a>
			</li>
				<li class="nav-item"><a class="nav-link" href="index.jsp">
					Connect	to database </a></li>
			</ul>

		</div>
	</nav>
	<div class="align-items-center">
		<div class="container py-5">
			<form class="my-5 text-center align-middle" action="home.jsp">
				<input type="submit" class="btn btn-primary align-middle"
					value="connect to database" name="connectDB">
			</form>
		</div>
	</div>
</body>
</html>