package servletclass;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updateContact")
public class updateContact extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String insert = "UPDATE contact SET name=?, phno=?, email=?, job=?, age=?, dob=? where id=?";
		try {
			PrintWriter out = res.getWriter();
			HttpSession session = req.getSession();
			Connection connection = (Connection) session.getAttribute("connection");
			int id = Integer.parseInt(req.getParameter("id"));
			String name = (String) req.getParameter("name");
			String phoneNumber = (String) req.getParameter("phone");
			String email = (String) req.getParameter("email");
			String job = (String) req.getParameter("job");
			int age = Integer.parseInt(req.getParameter("age"));
			String dob = req.getParameter("dob").toString();
			PreparedStatement pstm = connection.prepareStatement(insert);
			pstm.setString(1, name);
			pstm.setString(2, phoneNumber);
			pstm.setString(3, email);
			pstm.setString(4, job);
			pstm.setInt(5, age);
			pstm.setString(6, dob);
			pstm.setInt(7, id);
			if (pstm.executeUpdate() == 1) {
				res.sendRedirect("viewdata.jsp");
			} else {
//				out.println("not success");
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