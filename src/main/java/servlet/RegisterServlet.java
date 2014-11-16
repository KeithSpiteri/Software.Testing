package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		String creditCardNumber = request.getParameter("CCN");
		String expiry = request.getParameter("expiry");
		String cvv = request.getParameter("CVV");
		String subscription = request.getParameter("subscription");
		
		LOGGER.info("############################################################################################");
		LOGGER.info("EXPIRY DATE IS: " + expiry);
		LOGGER.info("SUBSCRIPTION IS: " + subscription);
		LOGGER.info("############################################################################################");

		SimpleDateFormat dobFormatter = new SimpleDateFormat("yyyy-MM-dd");

		Date dateOfBirth = null;
		try {

			dateOfBirth = dobFormatter.parse(dob);
			System.out.println(dateOfBirth);
			System.out.println(dobFormatter.format(dateOfBirth));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat expiryFormatter = new SimpleDateFormat("yyyy-MM");

		Date expiryDate = null;
		try {

			expiryDate = expiryFormatter.parse(expiry);
			System.out.println(expiryDate);
			System.out.println(expiryFormatter.format(expiryDate));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		boolean free = true;
		boolean premium = false;

		if (subscription.equals("premium")) {
			free = false;
			premium = true;
		}
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(expiryDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE,1);
		calendar.add(Calendar.DATE, -1);
		
		expiryDate.setDate(calendar.get(Calendar.DATE));
		
		User user = new User(username, password, name, surname, dateOfBirth,
				creditCardNumber, expiryDate, cvv, premium, free);

		DbService service = DbService.getInstance();

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
