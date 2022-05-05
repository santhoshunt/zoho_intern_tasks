<%@page import="java.sql.Connection"%>
<nav
	class="navbar navbar-expand-lg  navbar-dark bg-dark mb-5 pt-3">
	<a class="navbar-brand" href="home.jsp"><h1>Digital Vault</h1></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="home.jsp">Home
			</a></li>
			<li class="nav-item"><a class="nav-link" href="index.jsp">Re-Connect
					database </a></li>
		</ul>

	</div>
</nav>
		<%
		Connection connn = null;
		try {
			connn = (Connection) session.getAttribute("connection");
		} catch (Exception e) {
			try {
				request.setAttribute("errorMessage", "Connection not establised");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			} catch (Exception e1) {
				out.println(e.getStackTrace() + " " + e1.getStackTrace());
			}
		}
		if (connn == null || connn.isClosed()) {
			try {
				request.setAttribute("errorMessage", "Connection not establised");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			} catch (Exception e1) {
				out.println(e1.getStackTrace());
			}
		}
		%>