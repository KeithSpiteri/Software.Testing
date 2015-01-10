import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
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
import org.mockito.MockitoAnnotations;

import servlet.BettingServlet;

public class BettingServletTests {

	private BettingServlet bettingServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

	@Mock
	private PrintWriter out;

/* */

/*	@Test
	public void testNotLoggedInVerifyRedirection() throws IOException,
			ServletException {
		// given
		doReturn(null).when(session).getAttribute("user");
		// when
		bettingServlet.doPost(request, response);
		// then
		verify(response).sendRedirect("index.jsp");
	}*/

	/*@Test
	public void testValidateBetNoErrors() throws ServletException, IOException {
		// given
		doReturn("user").when(session).getAttribute("user");
		doReturn("Low").when(request).getParameter("betrisk");
		doReturn("1.0").when(request).getParameter("amm");
		doReturn(true).when(bettingImpl).validateBets(anyString(), anyString(),
				anyDouble());
		doReturn("Bet placed successfully").when(bettingImpl).getMessage();
		// when
		bettingServlet.doPost(request, response);
		// then
		verify(out).println(noErrors);
	}*/

	/*@Test
	public void testValidateBetNumberFormatExceptionOccurs()
			throws ServletException, IOException {
		// given
		doReturn("user").when(session).getAttribute("user");
		doReturn("Low").when(request).getParameter("betrisk");
		doReturn("abc").when(request).getParameter("amm");
		doReturn(true).when(bettingImpl).validateBets(anyString(), anyString(),
				anyDouble());
		// when
		bettingServlet.doPost(request, response);
		// then
		verify(out).println(invalidAmountError);
	}*/

	/*@Test
	public void testValidateBetNullPointerExceptionOccurs()
			throws ServletException, IOException {
		// given
		doReturn("user").when(session).getAttribute("user");
		doReturn("Low").when(request).getParameter("betrisk");
		doReturn(null).when(request).getParameter("amm");
		doReturn(true).when(bettingImpl).validateBets(anyString(), anyString(),
				anyDouble());
		// when
		bettingServlet.doPost(request, response);
		// then
		verify(out).println(invalidAmountError);
	}*/

	/*@Test
	public void testValidateBetInvalidBet() throws ServletException,
			IOException {
		// given
		doReturn("usr").when(session).getAttribute("user");
		doReturn("Low").when(request).getParameter("betrisk");
		doReturn("1.0").when(request).getParameter("amm");
		doReturn(false).when(bettingImpl).validateBets(anyString(),
				anyString(), anyDouble());
		doReturn("An error occured. Please try again.").when(bettingImpl)
				.getMessage();
		// when
		bettingServlet.doPost(request, response);
		// then
		verify(out).println(errorOccurs);
	}*/

	/*@Test
	public void testValidateBetRiskIsNull() throws ServletException,
			IOException {
		// given
		doReturn("usr").when(session).getAttribute("user");
		doReturn(null).when(request).getParameter("betrisk");
		doReturn("1.0").when(request).getParameter("amm");
		doReturn(true).when(bettingImpl).validateBets(anyString(), anyString(),
				anyDouble());
		// when
		bettingServlet.doPost(request, response);
		// then
		verify(out).println(invalidRisk);
	}*/
}