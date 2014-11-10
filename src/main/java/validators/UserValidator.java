package validators;

import org.apache.commons.lang3.StringUtils;

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

	public boolean validateNameOrSurname(String name) {
		if (StringUtils.isBlank(name) || ! StringUtils.isAlphaSpace(name))
			return false;
		else
			return true;
	}
}
