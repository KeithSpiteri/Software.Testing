import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterServlet extends HttpServlet {

	private static Logger LOGGER = LoggerFactory
			.getLogger(RegisterServlet.class);

	private String message;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String user = request.getParameter("user");
		String password = request.getParameter("pass");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String dob = request.getParameter("date");

		out.write("user - " + user + "\n");
		out.write("password - " + password + "\n");
		out.write("name - " + name + "\n");
		out.write("surname - " + surname + "\n");
		out.write("dob - " + dob + "\n");
	}
}
