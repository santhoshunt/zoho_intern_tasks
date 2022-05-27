
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="servletclass.ConnectionManager, java.sql.*"%>
<%@ page import="java.net.http.HttpClient.Redirect" %>
<%@ page import="java.security.Key" %>
<%@ page import="io.jsonwebtoken.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="javax.crypto.spec.*" %>
<%@ page import="javax.xml.bind.DatatypeConverter" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="./../header.jsp"%>
</head>
<body>
	<%@include file="./../navbar.jsp"%>
    <%
        String key = (String) session.getAttribute("key");
        String enctryptedString = "";

        Claims claims;
        try {
            enctryptedString  = request.getParameter("param");
            claims = Jwts.parser()
                .setSigningKey(DatatypeConverter
                        .parseBase64Binary(key))
                .parseClaimsJws(enctryptedString).getBody();
            String email = claims.get("sub").toString().split(" ")[0];
            String password = claims.get("sub").toString().split(" ")[1];
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO USERS (email, password) VALUES (?, ?)");
            pstm.setString(1, email);
            pstm.setString(2, password);
            int i = pstm.executeUpdate();
            PreparedStatement pstm2 = connection.prepareStatement("INSERT INTO userrole (email, role) VALUES (?, 'admin')");
            pstm2.setString(1, email);
            int j = pstm2.executeUpdate();
            if (i + j == 2) {
                out.println("<h1 class='text-center text-success'>Account creation success, Please go to login page</h1>");
    %>
    <script>
    let webSocket;
    function connect() {
    let wsUrl = "ws://127.0.0.1:8080/validate/validate";
    // request.getParameter("param") %>";
     // open the connection if one does not exist
        if (webSocket !== undefined
            && webSocket.readyState !== WebSocket.CLOSED) {
                console.log("in here")
            return;
        }

        console.log("Trying to establish a WebSocket connection to " + wsUrl + "");

        // Create a websocket
        webSocket = new WebSocket(wsUrl);

        webSocket.onopen = function (event) {
            console.log("Connected!");
            webSocket.send("verified");
        };

        webSocket.onmessage = function (event) {
            console.log(event.data);
        };

        webSocket.onclose = function (event) {
            console.log("Connection Closed");
        };
    }
    connect();
    </script>
    <%
            } else {
                out.println("<h1 class='text-center text-danger'>Account creation Failed, Please try again</h1>");
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            out.println("<h1 class='text-center'>Session Expired</h1>");
        } catch (Exception e) {
            out.println("<h1 class='text-center'>Unknown error. Please try again</h1>");
            out.println("<h1 class='tezt-center'>" + e + "</h1>");
        }
    %>
</body>