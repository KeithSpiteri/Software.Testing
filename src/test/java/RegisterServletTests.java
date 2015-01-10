import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import persistant.User;
import servlet.RegisterServlet;
import validators.UserValidator;
import db.services.DbService;

public class RegisterServletTests {

	private RegisterServlet registerServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private PrintWriter out;

	private DbService dbService;

	private UserValidator userValidator;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		registerServlet = new RegisterServlet();

		registerServlet.userValidator = Mockito.mock(UserValidator.class);
		this.userValidator = registerServlet.userValidator;

		registerServlet.dbService = Mockito.mock(DbService.class);
		this.dbService = registerServlet.dbService;

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

		doReturn(true).when(userValidator).validate(any(User.class));

		doReturn(true).when(dbService).addUser(any(User.class));

		registerServlet.doPost(request, response);
		verify(response).sendRedirect("index.jsp");
	}

	@Test
	public void testInvalidRegistration() throws ServletException, IOException {
		doReturn(false).when(userValidator).validate(any(User.class));

		doReturn(true).when(dbService).addUser(any(User.class));

		registerServlet.doPost(request, response);
		verify(out).write("Invalid user details encountered");
	}

	@Test
	public void testErrorInsertingUser() throws ServletException, IOException {
		doReturn(true).when(userValidator).validate(any(User.class));

		doReturn(false).when(dbService).addUser(any(User.class));

		registerServlet.doPost(request, response);
		verify(out).write("Not inserted");
	}
}