
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="servletclass.ConnectionManager, java.sql.*"%>

<%@ page import="java.net.http.HttpClient.Redirect" %>
<%@ page import="java.security.Key" %>
<%@ page import="io.jsonwebtoken.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.util.Random" %>
<%@ page import="javax.crypto.spec.*" %>
<%@ page import="javax.xml.bind.DatatypeConverter" %>
<%@ page import="servletclass.SendMail"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="./../header.jsp"%>
</head>
<body>
	<%@include file="./../navbar.jsp"%>
    <%
        String curEmail = request.getParameter("email");
        String curPassword = request.getParameter("password");
        if (curEmail == null || curPassword == null || curEmail.trim().length() < 1 || curPassword.trim().length() < 1) {
            response.sendRedirect("/digi/login/register.html");
        } 
        String sql = "SELECT email FROM users WHERE users.email=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, curEmail);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            out.println("<h1 class='text-center'>Email " + curEmail + " already exists, Please login<h1>");
            return;
        } 
        out.println(curEmail + " " + curPassword);
        String id = "1";
        String issuer = "DigitalVault";
        String subject = curEmail + " " + curPassword;

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Random random = new Random();
        System.out.println(random.nextLong());
        UUID uuid = UUID.randomUUID();
        String secretKey = uuid.toString() + random.nextLong();
        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary(secretKey);

        session.setAttribute("key", secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + 120000;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        String link = "http://localhost:8085/digi/login/validate.jsp?param=" + builder.compact();
        String message = "Please click on the link below to verify your email address\n" + link + "\nThe email will be invalid after 2 Minutes";
        try {
            SendMail mail = new SendMail();
            mail.send(curEmail, message);
            response.sendRedirect("/digi/login/loading.jsp");
        } catch (Exception e) {
            System.out.println(e);
        }
        %>
</body>