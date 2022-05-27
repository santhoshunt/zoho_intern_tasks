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
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-5">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Digital Vault</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="/digi/home.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/digi/index.jsp">Re-connect to database</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/digi/logout">logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>