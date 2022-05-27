
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
    <h1 class='text-center'>Please wait you'll be redirected to login page on successfull verification </h1>
    <h3 class='text-center text-danger'>Your email link will be valid only for: <span id='timer'></span></h3>
    <script>
    var twoMinutes = 60 * 2,
        display = document.querySelector('#timer');
    startTimer(twoMinutes, display);
    function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = duration;
        }
    }, 1000);
}
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
        };

        webSocket.onmessage = function (event) {
            console.log(event.data);
            if (event.data.toLowerCase() == "verified") {
                console.log(event.data);
                window.location.href = "http://localhost:8085/digi/login/logout.jsp";
            }
        };

        webSocket.onclose = function (event) {
            console.log("Connection Closed");
        };
    }
    connect();
    </script>
    
</body>