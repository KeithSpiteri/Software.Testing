import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "helloServlet", urlPatterns = { "/hello" })
public class LoginServlet extends HttpServlet {

	private static Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

	private String message;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String user = request.getParameter("user");
		String password = request.getParameter("pass");

		out.write("user - " + user + "\n");
		out.write("password - " + password + "\n");

	}
}
