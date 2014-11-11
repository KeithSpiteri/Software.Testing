package validators;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;

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
		if (StringUtils.isBlank(name) || !StringUtils.isAlphaSpace(name))
			return false;
		else
			return true;
	}

	public boolean validateDob(Date dob) {
		Date today = new Date();

		Period age = new Period(new DateTime(dob), new DateTime(today));

		if (age.getYears() >= 18)
			return true;
		else
			return false;

	}
}
