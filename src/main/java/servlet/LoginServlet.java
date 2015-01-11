package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistant.User;
import db.services.DbService;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -6924892271690933863L;
	public DbService dbService = DbService.getInstance();

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("user");
		String password = request.getParameter("pass");

		
		User user = dbService.loadUser(username);

		if (user == null)
			user = new User();
		if (user!=null && user.getLockedTill() == null
				|| user.getLockedTill().before(new Date())) {
			if (user != null && password.equals(user.getPassword())) {
				// Create a cookie for this new session

				Cookie loginCookie = new Cookie("user", user.getUsername());
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
