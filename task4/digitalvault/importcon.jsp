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
		<form method="post" action="importcontact" enctype="multipart/form-data">
			<h5>The data inside the CSV file should be in the format of :</h5> <h3> Name, phone number, email, job, age and Date of birth</h3>
			<div class="form-group">
				<label class="form-label mt-5" for="file">Choose the CSV file:</label>
				<input type="file" name="file" class="form-control">
			</div>
			<div class="text-center mt-5">
				<input type="submit" name="Upload" value="upload" class="btn btn-primary">
			</div>
		</form>
	</div>
</body>
</html>