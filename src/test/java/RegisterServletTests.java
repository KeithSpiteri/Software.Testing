import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
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

import db.services.DbService;
import persistant.User;
import servlet.RegisterServlet;
import validators.UserValidator;

public class RegisterServletTests {

	private RegisterServlet registerServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

	@Mock
	private PrintWriter out;

	@Mock
	private User user;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		registerServlet = new RegisterServlet();

		doReturn(session).when(request).getSession();

		doReturn("user").when(request).getParameter("user");
		doReturn("password").when(request).getParameter("pass");
		doReturn("Name").when(request).getParameter("name");
		doReturn("Surname").when(request).getParameter("surname");
		doReturn("1994-06-05").when(request).getParameter("date");
		doReturn("378282246310005").when(request).getParameter("CCN");
		doReturn("2020-12-31").when(request).getParameter("expiry");
		doReturn("123").when(request).getParameter("CVV");
		doReturn("premium").when(request).getParameter("subscription");

		doReturn(out).when(response).getWriter();
	}

	@Test
	public void testValidRegistration() throws ServletException, IOException {
		registerServlet.userValidator = Mockito.mock(UserValidator.class);
		doReturn(true).when(registerServlet.userValidator).validate(
				any(User.class));

		registerServlet.dbService = Mockito.mock(DbService.class);
		doReturn(true).when(registerServlet.dbService).addUser(any(User.class));

		registerServlet.doPost(request, response);
		verify(response).sendRedirect("index.jsp");
	}

	@Test
	public void testInvalidRegistration()
			throws ServletException, IOException { // given
		registerServlet.userValidator = Mockito.mock(UserValidator.class);
		doReturn(false).when(registerServlet.userValidator).validate(
				any(User.class));

		registerServlet.dbService = Mockito.mock(DbService.class);
		doReturn(true).when(registerServlet.dbService).addUser(any(User.class));

		registerServlet.doPost(request, response);
		verify(out).write("Invalid user details encountered");
	}
	
	@Test
	public void testErrorInsertingUser()
			throws ServletException, IOException { // given
		registerServlet.userValidator = Mockito.mock(UserValidator.class);
		doReturn(true).when(registerServlet.userValidator).validate(
				any(User.class));

		registerServlet.dbService = Mockito.mock(DbService.class);
		doReturn(false).when(registerServlet.dbService).addUser(any(User.class));

		registerServlet.doPost(request, response);
		verify(out).write("Not inserted");
	}
}