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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import persistant.User;
import db.services.DbService;
import servlet.LoginServlet;

public class LoginServletTests {

	private LoginServlet loginServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

	@Mock
	private PrintWriter out;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		loginServlet = new LoginServlet();

		loginServlet.dbService = Mockito.mock(DbService.class);
		
		doReturn("user").when(request).getParameter("user");
		doReturn("pass").when(request).getParameter("pass");
		doReturn(session).when(request).getSession();
		doReturn(out).when(response).getWriter();
		doNothing().when(session).setAttribute(anyString(), anyString());
	}

	@Test
	public void testValidLogin() throws ServletException,
			IOException {
		User user = new User();
		user.setPassword("pass");
		Mockito.when(loginServlet.dbService.loadUser("user")).thenReturn(user);
		
		loginServlet.doPost(request, response);
		verify(response).sendRedirect("bet.jsp");
	}

	/*@Test
	public void testLoginInvalidPassword() throws ServletException,
			IOException {
		// given
		doReturn(false).when(login).validate(anyString(), anyString());
		// when
		loginServlet.doPost(request, response);
		// then
		verify(out)
				.println(
						(new MessagePageImpl())
								.printMessagePageLoggedOut("Wrong username or password Please Try again"));
	}*/

	/*@Test
	public void testValidLoginUserSetInSession() throws ServletException, IOException {
		// given
		doReturn(true).when(login).validate(anyString(), anyString());
		// when
		loginServlet.doPost(request, response);
		// then
		verify(session).setAttribute("user", "user");
	}*/
}

