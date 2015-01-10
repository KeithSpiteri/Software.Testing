import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

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

	@Mock
	User user;
	
	private DbService dbService;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		loginServlet = new LoginServlet();

		loginServlet.dbService = Mockito.mock(DbService.class);
		this.dbService = loginServlet.dbService;

		doReturn("user").when(request).getParameter("user");
		doReturn("pass").when(request).getParameter("pass");
		doReturn(session).when(request).getSession();
		doReturn(out).when(response).getWriter();
		doNothing().when(session).setAttribute(anyString(), anyString());
	}

	@Test
	public void testValidLogin() throws ServletException, IOException {
		User user = new User();
		user.setPassword("pass");

		doReturn(user).when(dbService).loadUser("user");

		loginServlet.doPost(request, response);
		verify(response).sendRedirect("bet.jsp");
	}

	@Test
	public void testInvalidLogin() throws ServletException, IOException {
		doReturn(new User()).when(dbService).loadUser("user");
		loginServlet.doPost(request, response);
		verify(out).write("Invalid username or password");
	}

	@Test
	public void testLockedAccount() throws ServletException,
			IOException { 
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR,1);
		
		doReturn(cal.getTime()).when(user).getLockedTill();
		doReturn(user).when(dbService).loadUser("user");
		loginServlet.doPost(request, response); 
		verify(out).write("Account is locked until "+cal.getTime());
	}
}
