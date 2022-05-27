package servletclass;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deleteservice")
public class deleteService extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String DELETE = "DELETE FROM account WHERE id=?";
		try {
			int delId = Integer.parseInt(req.getParameter("id"));
			HttpSession session = req.getSession();
			Connection connection = (Connection) session.getAttribute("connection");
			PreparedStatement pstm = connection.prepareStatement(DELETE);
			pstm.setInt(1, delId);
			if (pstm.executeUpdate() == 1) {
			} else {
				res.sendRedirect("error.jsp");
			}
			pstm.close();
		} catch (Exception e) {
			try {

				res.sendRedirect("error.jsp");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
}