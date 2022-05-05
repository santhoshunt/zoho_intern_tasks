package servletclass;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/insertservice")
public class InsertIntoService extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String INSERT = "INSERT INTO service (servicename, serviceurl) VALUES (?, ?) ON CONFLICT (servicename) DO NOTHING";
		String INSERTACCOUNT = "INSERT INTO account (serviceid, username, password) VALUES (?, ?, ?)";
		try {
			String serviceName = (String) req.getParameter("servicename").toLowerCase();
			String userName = (String) req.getParameter("username");
			String password = (String) req.getParameter("password");
			String url = (String) req.getParameter("url");
			out.println(serviceName + " " + userName + " " + password +  " " + url);
			int serviceId = -1;

			HttpSession session = req.getSession();
			Connection connection = (Connection) session.getAttribute("connection");
			PreparedStatement pstm = connection.prepareStatement(INSERT);
			pstm.setString(1, serviceName);
			pstm.setString(2, url);
			pstm.executeUpdate();

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select serviceid from service where servicename='" + serviceName + "'");
			if (rs.next()) {
				serviceId = rs.getInt(1);
			} else {
				req.setAttribute("errorMessage", "Connection not establised");
				try {
					req.getRequestDispatcher("/index.jsp").forward(req, res);
				} catch (ServletException | IOException e1) {
					e1.printStackTrace();
				}
			}

			PreparedStatement pstm2 = connection.prepareStatement(INSERTACCOUNT);
			pstm2.setInt(1, serviceId);
			pstm2.setString(2, userName);
			pstm2.setString(3, password);
//			pstm.setInt(5, age);
			if (pstm2.executeUpdate() == 1) {
				res.sendRedirect("viewservices.jsp");
			} else {
				req.setAttribute("errorMessage", "Connection not establised");
				try {
					req.getRequestDispatcher("/index.jsp").forward(req, res);
				} catch (ServletException | IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			out.println(e + " " + e.getMessage() + " " + e.getStackTrace() +  " " + e.getCause());
//			req.setAttribute("errorMessage", "Connection not establised");
//			try {
//				req.getRequestDispatcher("/index.jsp").forward(req, res);
//			} catch (ServletException | IOException e1) {
//				e1.printStackTrace();
//			}
		}

	}
}