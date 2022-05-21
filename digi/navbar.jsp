<%@ page import="servletclass.ConnectionManager, java.sql.*"%>

	<%
String sqlQuery = "select userid from users where email=?";
		PreparedStatement prepstm = connection.prepareStatement(sqlQuery);
		String tempEmail = request.getRemoteUser();
		prepstm.setString(1, tempEmail);
		System.out.println("Temp email is :"+ tempEmail);
		ResultSet res = prepstm.executeQuery();
		int userid = -1;
		if (res.next()) {
		userid = res.getInt(1);
		session.setAttribute("userid", userid);
		System.out.println("user id is "+ userid);
	}
		prepstm.close();
		res.close();
		
		%>
<nav
	class="navbar navbar-expand-lg  navbar-dark bg-dark mb-5 pt-3">
	<a class="navbar-brand" href="/home/sandy/apache-tomcat-8.5.78/webapps/digi/home.jsp"><h1>Digital Vault</h1></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="/digi/home.jsp">Home
			</a></li>
			<li class="nav-item"><a class="nav-link" href="/digi/index.jsp">Re-Connect
					database </a></li>
			<li class="nav-item"><a class="nav-link" href="/digi/logout">logout</a></li>
		</ul>
	</div>
</nav>