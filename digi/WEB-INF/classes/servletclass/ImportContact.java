package servletclass;

import java.io.*;
import java.sql.*;
import java.sql.Connection;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "importcontact", urlPatterns = { "/importcontact" })
@MultipartConfig
public class ImportContact extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) {
		try {
			HttpSession session = req.getSession();
			int id = Integer.parseInt(req.getParameter("id"));
			Connection connection = (Connection) session.getAttribute("connection");
			String sql = "select * from contact where id=" + id;
			Statement statment = connection.createStatement();
			ResultSet rs = statment.executeQuery(sql);
			String name = "", phno = "", email = "", job = "", dob = "";
			if (rs.next()) {
				name = rs.getString(2);
				phno = rs.getString(3);
				email = rs.getString(4);
				job = rs.getString(5);
				dob = rs.getString(7);
			} else {
				res.sendRedirect("error/error.jsp");
			}
			// String fileName = "contact.vcf";
			// String path = "/home/sandy/Downloads/";
			res.setContentType("text/vcf");
			res.setHeader("Content-Disposition", "attachment; filename=\"contact.vcf\"");
			File newContact = new File("contact.vcf");
			FileOutputStream fop = new FileOutputStream(newContact);
			PrintWriter out = res.getWriter();

			if (newContact.exists()) {
				String con = "BEGIN:VCARD\n" +
						"VERSION:3.0\n" +
						"N:;" + name + ";;;\n" +
						"FN:" + name + "\n" +
						"TEL;TYPE=CELL:" + phno + "\n" +
						"EMAIL;TYPE=WORK:" + email + "\n" +
						"TITLE:" + job + "\n" +
						"BDAY:" + dob + "\n" +
						"END:VCARD";
				fop.write(con.getBytes());

				BufferedReader br = null;
				String sCurrentLine;
				br = new BufferedReader(new FileReader("contact.vcf"));
				while ((sCurrentLine = br.readLine()) != null) {
					out.println(sCurrentLine);
				}
				br.close();
				// close the output stream and buffer reader
				fop.flush();
				fop.close();
			} else {
				res.sendRedirect("error/error.jsp");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
