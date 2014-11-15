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

	public boolean validateCvv(String cvv) {
		if (cvv.length() == 3 && StringUtils.isNumeric(cvv)) {
			return true;
		} else
			return false;
	}

	public boolean validateFreeOrPremium(boolean free, boolean premium) {
		return free ^ premium;
	}

	public boolean validateCCExpiry(Date expiry) {
		Date today = new Date();
		return !today.after(expiry);
	}

	public boolean validateCCNumber(String number) {
		return validateAmericanExpress(number) | validateMastercard(number)
				| validateVisa(number);
	}

	public boolean validateAmericanExpress(String number) {
		if (number.length() == 15
				&& (number.substring(0, 3).equals("34") || number.substring(0,
						3).equals("37"))) {
			return luhnVerification(number);
		} else
			return false;
	}

	private boolean validateMastercard(String number) {
		if (number.length() == 16
				&& (number.substring(0, 3).equals("1")
						|| number.substring(0, 3).equals("32")
						|| number.substring(0, 3).equals("33")
						|| number.substring(0, 3).equals("34") || number
						.substring(0, 3).equals("35"))) {
			return luhnVerification(number);
		} else
			return false;
	}

	private boolean validateVisa(String number) {
		if (number.charAt(0) == '4'
				&& (number.length() == 14 || number.length() == 16)) {
			return luhnVerification(number);
		} else
			return false;
	}

	private boolean luhnVerification(String number) {

		int check = 0;

		for (int i = 0; i < number.length(); i += 2) {
			int num = (int) (Integer.parseInt("" + number.charAt(i)));
			int prod = num * 2;

			check += prod % 10;
			if (prod > 9)
				check += prod - (prod % 10);
		}
		for (int i = 1; i < number.length(); i += 2) {
			int num = (int) (Integer.parseInt("" + number.charAt(i)));
			check += num;
		}

		return (check % 10 == 0);
	}
}
