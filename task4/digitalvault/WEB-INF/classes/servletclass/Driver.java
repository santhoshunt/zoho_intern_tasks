package servletclass;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.*;
import javax.security.auth.login.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth")
public class Driver extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("In auth page");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		out.println(name + " " + password);
		if (name != null && password != null) {
			LoginCallbackHandler callbackHandler = new LoginCallbackHandler(name, password);
			boolean authenticated = false;
			try {
				LoginContext loginContext = new LoginContext("digitalvault", callbackHandler);
				loginContext.login();
				System.out.println("In next to login context login");
				authenticated = true;
			} catch (LoginException e) {
				out.println(e);
				e.printStackTrace();
				authenticated = false;
			}
			
			if (authenticated) {
				out.println("Authentication success!");
			} else {
				out.println("Invalid authentication");
			}
 
		} else {
			out.println("Invalid auth");
		}
	}
}