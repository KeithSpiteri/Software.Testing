import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import persistant.User;
import persistant.Bet;
import db.services.DbService;
import servlet.BettingServlet;
import validators.BetValidator;

public class BettingServletTests {

	private BettingServlet bettingServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private PrintWriter out;

	private DbService dbService;

	private BetValidator validator;

	@Before
	public void init() throws IOException {
		MockitoAnnotations.initMocks(this);

		bettingServlet = new BettingServlet();
		bettingServlet.dbService = Mockito.mock(DbService.class);
		bettingServlet.betValidator = Mockito.mock(BetValidator.class);
		
		doReturn("low").when(request).getParameter("risk");
		doReturn("4.5").when(request).getParameter("amount");
		doReturn("user").when(request).getParameter("userName");

		this.dbService = bettingServlet.dbService;
		this.validator = bettingServlet.betValidator;

		doReturn(out).when(response).getWriter();
	}

	@Test
	public void testSuccesfullBet() throws IOException, ServletException {
		doReturn(true).when(validator).validateBet(any(User.class),
				any(Bet.class));

		bettingServlet.doPost(request, response);

		verify(response).sendRedirect("bet.jsp");
	}
	
	@Test
	public void testFailedBet() throws IOException, ServletException {
		doReturn(false).when(validator).validateBet(any(User.class),
				any(Bet.class));

		bettingServlet.doPost(request, response);

		verify(out).write("Unable to place bet");
	}

}