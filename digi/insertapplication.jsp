<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<%-- servicename, username, password, serviceid--%>

	<div class="container p-5">
		<div class="container text-center mb-5">
            <h1>Enter the UserName and Password for okta.com</h1>
        </div>
        <form method="post" action="insertservice">
			<div class="form-group"> <input
					class="form-control" type="hidden" value="okta" name="servicename">
			</div>
			<div class="form-group">
				<label for="username" class="form-label">User name</label> <input type="text"
					class="form-control" name="username">
			</div>
			<div class="form-group">
				<label for="password" class="form-label">Password</label> <input type="text"
					name="password" class="form-control">
			</div>
			<div class="form-group"> <input type="hidden" value="www.okta.com"
					name="url" class="form-control">
			</div>
			<div class="form-group text-center mt-3">
				<input type="submit" class="btn btn-primary">
			</div>
		</form>
	</div>
</body>
</html>