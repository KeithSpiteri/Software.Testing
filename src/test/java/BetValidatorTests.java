import static org.junit.Assert.*;


import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import validators.UserValidator;

public class BetValidatorTests {

	private UserValidator validator;
	private Date today;

	@Before
	public void init() {
		validator = new UserValidator();
		today = new Date();
		//DbService.dbService = Mockito.mock(DbService.class);
	}

}
