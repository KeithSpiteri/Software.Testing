import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		PrintWriter writer = new PrintWriter("C:\\Users\\Brian\\Desktop\\the-file-name.txt", "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
		
		
		String login = request.getParameter("login");
		String password = request.getParameter("passwordHash");

		//
		// Do what ever you want with login and passwordHash here...
		//

		// Because we are using ajax we need to respond to it stating whether we
		// can redirect or not to new location, see lines below

		// Content type of the response - You could also return application/json
		// for example (which would be better in this case)
		response.setContentType("text/plain"); // Using text/plain for example
		response.setCharacterEncoding("UTF-8");

		// Change this as you like - it can also be url or anything else you
		// want...
		response.getWriter().write("SUCCESS");

	}
}
