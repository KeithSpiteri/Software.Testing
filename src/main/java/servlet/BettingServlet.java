package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistant.Bet;
import persistant.User;
import db.services.DbService;

public class BettingServlet extends HttpServlet {

	private static Logger LOGGER = LoggerFactory.getLogger(BettingServlet.class);

	private String message;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String riskLevel = request.getParameter("risk");
		String amount = request.getParameter("amount");
		String userName = request.getParameter("userName");

		Bet bet = new Bet();
		bet.setRiskLevel(riskLevel);
		bet.setAmount(Float.parseFloat(amount));
		bet.setUserName(userName);
		DbService dbService = DbService.getInstance();
		dbService.addBet(bet);
		
		response.sendRedirect("bet.jsp");
	}
}
