package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.services.DbService;
import persistant.User;

public class RegisterServlet extends HttpServlet {

	private static Logger LOGGER = LoggerFactory
			.getLogger(RegisterServlet.class);

	private String message;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String dob = request.getParameter("date");

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		Date date = null;
		try {

			date = formatter.parse(dob);
			System.out.println(date);
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		User user = new User(username, password, name, surname, date);

		DbService service = new DbService();

		if (service.addUser(user)) {
			out.write("user - " + username + "\n");
			out.write("password - " + password + "\n");
			out.write("name - " + name + "\n");
			out.write("surname - " + surname + "\n");
			out.write("dob - " + dob + "\n");
			response.sendRedirect("index.jsp");
		} else
			out.write("Not inserted");
	}
}
