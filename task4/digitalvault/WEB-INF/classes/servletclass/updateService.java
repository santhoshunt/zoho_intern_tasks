package servletclass;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updateService")
public class updateService extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String SERVICEUPDATE = "UPDATE service SET servicename=?, serviceurl=? where serviceid=?";
		String ACCOUNTUPDATE = "UPDATE account SET username=?, password=? WHERE id=?";
		PrintWriter out = null;
		boolean redirect = false;
		try {
			out = res.getWriter();
			HttpSession session = req.getSession();
			Connection connection = (Connection) session.getAttribute("connection");
			String serviceName = (String) req.getParameter("servicename");
			String userName = (String) req.getParameter("username");
			String password = (String) req.getParameter("password");
			String url = (String) req.getParameter("url");
			int id = -1, serviceId = -1;
			try {
				id = Integer.parseInt(req.getParameter("id"));
				serviceId = Integer.parseInt(req.getParameter("serviceid"));	
			} catch (Exception e) {
				redirect = true;
			}

			PreparedStatement pstm1 = connection.prepareStatement(SERVICEUPDATE);
			if (!redirect) {
				pstm1.setString(1, serviceName);
				pstm1.setString(2, url);
				pstm1.setInt(3, serviceId);
			}
			out.println(pstm1);
			if (!redirect && pstm1.executeUpdate() == 0) {
				redirect = true;
			}
			
			PreparedStatement pstm2 = connection.prepareStatement(ACCOUNTUPDATE);
			if (!redirect) {
				pstm2.setString(1, userName);
				pstm2.setString(2, password);
				pstm2.setInt(3, id);
			}
			if (!redirect && pstm2.executeUpdate() == 1) {
				res.sendRedirect("viewservices.jsp");
			} else {
				res.sendRedirect("error.jsp");
			}
			
			
		} catch (Exception e) {
			try {
				out.println(e.getMessage());
				res.sendRedirect("error.jsp");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
}