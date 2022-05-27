<%
request.getSession().invalidate();

    response.sendRedirect("/digi/home.jsp");
    %>