package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistant.User;
import db.services.DbService;

@WebServlet(name = "helloServlet", urlPatterns = { "/hello" })
public class LoginServlet extends HttpServlet {

	private static Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

	private String message;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("user");
		String password = request.getParameter("pass");

		DbService dbService = DbService.getInstance();

		User user = dbService.loadUser(username);

		if (user != null && password.equals(user.getPassword()))
			response.sendRedirect("bet.jsp");
		else
			out.write("Invalid username or password");
	}
}
