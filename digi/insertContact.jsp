<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<%-- id, name, phoneNumber, email, job, age, dob --%>
	<div class="container">
		<form method="post" action="insertcontact">
			<div class="form-group">
				<label for="name" class="form-label">Name</label> <input type="text" name="name" class="form-control" >
			</div>
			<div class="form-group">
				<label for="phone" class="form-label">Phone number</label> <input type="number" class="form-control" 
					name="phone">
			</div>
			<div class="form-group">
				<label for="email" class="form-label">Email</label> <input type="email" name="email" class="form-control" >
			</div>
			<div class="form-group">
				<label for="job" class="form-label">Job</label> <input type="text" name="job" class="form-control" >
			</div>
			<div class="form-group">
				<label for="age" class="form-label">age</label><input type="number" name="age" class="form-control" >
			</div>
			<div class="form-group">
				<label for="dob" class="form-label">Date Of Birth</label><input type="date" name="dob" class="form-control" >
			</div>
			<div class="form-group text-center mt-3">
				<input type="submit" class="btn btn-primary">
			</div>
		</form>
	</div>
</body>
</html>