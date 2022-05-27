<%@page import="java.net.http.HttpClient.Redirect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="servletclass.ConnectionManager, java.sql.*"%>

<%@ page import = "java.io.*"%>
<%@ page import = " java.util.Base64" %>
<%-- <%@ page import ="org.apache.commons.codec.binary.Base64" %> --%>
<%@ page import = "org.opensaml.messaging.context.MessageContext, org.opensaml.messaging.decoder.MessageDecodingException, org.opensaml.saml.saml2.binding.decoding.impl.HTTPPostDecoder, org.opensaml.saml.saml2.core.AuthnRequest, org.opensaml.core.config.InitializationService, org.opensaml.core.config.Initializer" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/header.jsp"%>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<%--
	<div>
		<TABLE class="table table-responsive table-striped table-bordered">
			
			<%
			while (rs.next()) {
				if (!printed) {
					printed = true;
					%>
			<thead class="thead-dark">
				<th>Service ID</th>
				<th>Service Name</th>
				<th>User Name</th>
				<th>password</th>
				<th>Service URL</th>
				<th>Update</th>
				<th>Delete</th>
			</thead>
			
			<% } %>
			<tr id="row-<%=rs.getInt(1)%>">
				<td><%=rs.getInt(1)%></td>
				<td><%=rs.getString(6)%></td>
				<td><%=rs.getString(3)%></td>
				<td><%=rs.getString(4)%></td>
				<td><%=rs.getString(7)%></td>
				<td><a class="btn btn-warning" href="updateserv.jsp?id=<%=rs.getInt(1)%>&&serviceID=<%=rs.getInt(2)%>">Update</a></td>
				<td>
					<button class="btn btn-danger" onclick="deleteService(<%=rs.getInt(1)%> , <%=rs.getInt(2)%>)">Delete</button>
				</td>
			</tr>
			<%
			}
			if (!printed) {
				out.println("<h1 class='text-center'>No Service accounts to display<h1>");
			}
			%>
	<script type="text/javascript">

		function deleteService(id, serviceId) {
			let http = new XMLHttpRequest();
			console.log(id, serviceId);
			let url = "http://localhost:8085/digi/deleteservice?id="+ id + "&&serviceID=" + serviceId;
			http.open('GET', url, true);

			http.onreadystatechange = function() {
    			if(http.readyState == 4 && http.status == 200) {
        			console.log("success", http.responseText);
    				document.getElementById("row-" + id).remove();
			    } else {
					console.log(http.readyState);
			    	console.log(http.responseText);
			    }
			}
			http.send();
		}
	</script>
	String samlRequest = request.getParameter("SAMLRequest");
	if (samlRequest == null) {
		samlRequest = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48c2FtbDJwOkF1dGhuUmVxdWVzdCBBc3NlcnRpb25Db25zdW1lclNlcnZpY2VVUkw9Imh0dHBzOi8vZGV2LTkyNDAyODQub2t0YS5jb20vc3NvL3NhbWwyLzBvYTUzZDFwdmhjaUdzQnI4NWQ3IiBEZXN0aW5hdGlvbj0iaHR0cHM6Ly9kZXYtOTI0MDI4NC5va3RhLmNvbS9sb2dpbi9sb2dpbi5odG0iIEZvcmNlQXV0aG49ImZhbHNlIiBJRD0iaWQ1ODEzOTE5OTM3MDQ4Mzc3NzI3NDk1OTkyIiBJc3N1ZUluc3RhbnQ9IjIwMjItMDUtMjNUMTE6NTI6NDMuMDY4WiIgVmVyc2lvbj0iMi4wIiB4bWxuczpzYW1sMnA9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDpwcm90b2NvbCI+PHNhbWwyOklzc3VlciB4bWxuczpzYW1sMj0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFzc2VydGlvbiI+aHR0cHM6Ly93d3cub2t0YS5jb20vc2FtbDIvc2VydmljZS1wcm92aWRlci9zcGFkbXdlYmZraG1kYm1jbm5jejwvc2FtbDI6SXNzdWVyPjxzYW1sMnA6TmFtZUlEUG9saWN5IEZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6MS4xOm5hbWVpZC1mb3JtYXQ6dW5zcGVjaWZpZWQiLz48L3NhbWwycDpBdXRoblJlcXVlc3Q+";
	} 
	
    if (samlRequest != null) {
		DefaultBootstrap.bootstrap();
		byte[] base64DecodedResponse = Base64.decode(samlRequest);
		ByteArrayInputStream is = new ByteArrayInputStream(base64DecodedResponse);

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

	 	try {
			Document document = docBuilder.parse(is);
			Element element = document.getDocumentElement();
			UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
			Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
			XMLObject requestXmlObj = unmarshaller.unmarshall(element);
			AuthnRequest req = (AuthnRequest) requestXmlObj;
			out.println(req);
		
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			try {
				transformer = tf.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(element), new StreamResult(writer));
		
				String xmlString = writer.getBuffer().toString(); 
				out.println(xmlString);            
			} catch (Exception e) {
				out.println(e);
			}


		} catch (Exception e) {
			out.println(e);
		}

    } else {
        out.println("No request received");
    }

	--%>
	<%
	try {
		String samlRequest = request.getParameter("SAMLRequest");
		if (samlRequest == null) {
			out.println("SAML request is empty");
			samlRequest = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48c2FtbDJwOkF1dGhuUmVxdWVzdCBBc3NlcnRpb25Db25zdW1lclNlcnZpY2VVUkw9Imh0dHBzOi8vZGV2LTkyNDAyODQub2t0YS5jb20vc3NvL3NhbWwyLzBvYTUzZDFwdmhjaUdzQnI4NWQ3IiBEZXN0aW5hdGlvbj0iaHR0cHM6Ly9kZXYtOTI0MDI4NC5va3RhLmNvbS9sb2dpbi9sb2dpbi5odG0iIEZvcmNlQXV0aG49ImZhbHNlIiBJRD0iaWQ1ODg5MTQzODc1MDIwMTU1NTgwMzY0NDc3IiBJc3N1ZUluc3RhbnQ9IjIwMjItMDUtMjRUMDk6MTY6MjQuMDA4WiIgVmVyc2lvbj0iMi4wIiB4bWxuczpzYW1sMnA9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDpwcm90b2NvbCI+PHNhbWwyOklzc3VlciB4bWxuczpzYW1sMj0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFzc2VydGlvbiI+aHR0cHM6Ly93d3cub2t0YS5jb20vc2FtbDIvc2VydmljZS1wcm92aWRlci9zcGFkbXdlYmZraG1kYm1jbm5jejwvc2FtbDI6SXNzdWVyPjxzYW1sMnA6TmFtZUlEUG9saWN5IEZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6MS4xOm5hbWVpZC1mb3JtYXQ6dW5zcGVjaWZpZWQiLz48L3NhbWwycDpBdXRoblJlcXVlc3Q+";
		}
		%>
		<script>
		console.log("SAML Request is ", "<%=samlRequest%>");
		console.log("Decoded string is ", atob("<%=samlRequest%>"));
		</script>
		<%
		// byte[] encodedBytes = samlRequest.getBytes("UTF-8");
		// out.println("encoded size " + encodedBytes.length + "<br>");
		// out.println(new String(encodedBytes));
		// byte[] decoded = Base64.decodeBase64(encodedBytes);
		// out.println("decoded size " + decoded.length + "<br>");
		// Base64.Decoder decoder = Base64.getDecoder(); 
		// String decodedStr = new String(decoder.decode(samlRequest));
		// out.println(decodedStr);
		byte[] valueDecoded = Base64.getDecoder().decode(samlRequest.replace("\n","").replace("\r",""));

		out.println(new String(valueDecoded, "UTF-8"));
	} catch (Exception e) {
		out.println(e);
	}
	%>

</body>
</html>