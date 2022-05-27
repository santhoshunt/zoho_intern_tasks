<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import = " java.util.Base64" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>
<%
    String samlRequest = "";
    if (request.getParameter("SAMLRequest") != null) {
        samlRequest = request.getParameter("SAMLRequest");
        System.out.println("samlRequest is : \n" + samlRequest);
    } else {
        out.println("No request received");
    }
%>
<script>
    let str = atob("<%=samlRequest%>");
    console.log(str);
</script>

 <%
    byte[] valueDecoded = Base64.getDecoder().decode(samlRequest.replace("\n","").replace("\r",""));
	String samlRequestString = new String(valueDecoded, "UTF-8");
    System.out.println(samlRequestString);
%>
</body>
</html>