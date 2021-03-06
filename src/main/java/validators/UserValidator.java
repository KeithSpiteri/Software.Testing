package validators;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;

import db.services.DbService;
import persistant.User;

public class UserValidator {

	public DbService dbService = DbService.getInstance();

	public boolean validate(User user) {
		return validateUsername(user.getUsername())
				&& validatePassword(user.getPassword())
				&& validateNameOrSurname(user.getName())
				&& validateNameOrSurname(user.getSurname())
				&& validateDob(user.getDob()) && validateCvv(user.getCvv())
				&& validateFreeOrPremium(user.isFree(), user.isPremium())
				&& validateCCExpiry(user.getExpiry())
				&& validateCCNumber(user.getCreditCardNumber());
	}

	public boolean validateUsername(String userName) {
		if (dbService.loadUser(userName) == null) {
			return true;
		} else
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

	private boolean validateAmericanExpress(String number) {
		if (number.length() == 15
				&& (number.substring(0, 2).equals("34") || number.substring(0,
						2).equals("37"))) {
			return luhnVerification(number);
		} else
			return false;
	}

	private boolean validateMastercard(String number) {
		if (number.length() == 16
				&& (number.substring(0, 2).equals("51")
						|| number.substring(0, 2).equals("52")
						|| number.substring(0, 2).equals("53")
						|| number.substring(0, 2).equals("54") || number
						.substring(0, 2).equals("55"))) {
			return luhnVerification(number);
		} else
			return false;
	}

	private boolean validateVisa(String number) {
		if (number.charAt(0) == '4'
				&& (number.length() == 13 || number.length() == 16)) {
			return luhnVerification(number);
		} else
			return false;
	}

	private boolean luhnVerification(String number) {

		int check = 0;

		for (int i = number.length() - 2; i >= 0; i -= 2) {
			int num = (int) (Integer.parseInt("" + number.charAt(i)));
			int prod = num * 2;

			check += prod % 10;
			if (prod > 9)
				check++;
		}
		for (int i = number.length() - 1; i >= 0; i -= 2) {
			int num = (int) (Integer.parseInt("" + number.charAt(i)));
			check += num;
		}
		return (check % 10 == 0);
	}
}
