package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistant.Bet;
import persistant.User;
import validators.BetValidator;
import db.services.DbService;

public class BettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BetValidator betValidator = new BetValidator();
	
	public DbService dbService = DbService.getInstance();

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String riskLevel = request.getParameter("risk");
		String amount = request.getParameter("amount");
		String userName = request.getParameter("userName");

		Bet bet = new Bet();
		bet.setRiskLevel(riskLevel);
		bet.setAmount(Float.parseFloat(amount));
		bet.setUserName(userName);
		
		User user = dbService.loadUser(userName);
		
		if(betValidator.validateBet(user, bet))
		{
				dbService.addBet(bet);
				response.sendRedirect("bet.jsp");
		} else
		{
			out.write("Unable to place bet");			
		}
	}
}
