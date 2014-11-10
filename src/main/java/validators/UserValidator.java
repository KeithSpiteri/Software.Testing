package validators;

import persistant.User;

public class UserValidator {

	public boolean validateUser(User user) {
		return false;

	}

	public boolean validatePassword(String password) {
		if (password != null && password.length() >= 8)
			return true;
		else
			return false;

	}
}
