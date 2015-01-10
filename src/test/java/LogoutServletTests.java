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
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import db.services.DbService;
import persistant.User;
import servlet.LogoutServlet;
import servlet.RegisterServlet;
import validators.UserValidator;

import javax.servlet.http.Cookie;

public class LogoutServletTests {

	private LogoutServlet logoutServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	private Cookie[] cookies = { new Cookie("user", "pass") };

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);

		logoutServlet = new LogoutServlet();

	}

	@Test
	public void testLogout() throws ServletException, IOException {
		doReturn(cookies).when(request).getCookies();
		logoutServlet.doPost(request, response);
		verify(response).sendRedirect("index.jsp");
	}

	@Test
	public void testLogoutCookiesAreNull() throws ServletException, IOException {
		doReturn(null).when(request).getCookies();
		logoutServlet.doPost(request, response);
		verify(response).sendRedirect("index.jsp");
	}

}