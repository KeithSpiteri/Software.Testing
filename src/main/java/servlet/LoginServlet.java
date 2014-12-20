package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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

		if (user.getLockedTill() == null
				|| user.getLockedTill().before(new Date())) {
			if (user != null && password.equals(user.getPassword())) {
				// Create a cookie for this new session

				Cookie loginCookie = new Cookie("user", user.getName());
				// setting cookie to expiry in 15 mins
				loginCookie.setMaxAge(15 * 60);
				response.addCookie(loginCookie);

				user.setFailedLogins(0);
				response.sendRedirect("bet.jsp");			
			} else {
				out.write("Invalid username or password");
				response.sendRedirect(request.getHeader("Referer"));
				if (user != null) {
					user.setFailedLogins(user.getFailedLogins() + 1);
				}
			}
		} else
			out.write("Account is locked until " + user.getLockedTill());
	}
}
