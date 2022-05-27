package servletclass;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/insertcontact")
public class InsertIntoContact extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String insert = "INSERT INTO contact (name, phno, email, job, age, dob, userid) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			HttpSession session = req.getSession();
			Connection connection  = (Connection) session.getAttribute("connection");
			String name = (String) req.getParameter("name");
			String phoneNumber = (String) req.getParameter("phone");
			String email = (String) req.getParameter("email");
			String job = (String) req.getParameter("job");
			int age = Integer.parseInt(req.getParameter("age"));
			String dob = req.getParameter("dob").toString();
			int userid = Integer.parseInt(session.getAttribute("userid").toString());
			PreparedStatement pstm = connection.prepareStatement(insert);
			pstm.setString(1, name);
			pstm.setString(2, phoneNumber);
			pstm.setString(3, email);
			pstm.setString(4, job);
			pstm.setInt(5, age);
			pstm.setString(6, dob);
			pstm.setInt(7, userid);
			System.out.println(pstm);
			if (pstm.executeUpdate() == 1) {
				res.sendRedirect("home.jsp");
			} else  {
				res.sendRedirect("error.jsp");
			}
			pstm.close();
		} catch (Exception e) {
			try {
				res.sendRedirect("error.jsp");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
}