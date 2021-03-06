import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import persistant.User;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import db.services.DbService;
import validators.UserValidator;

public class UserValidatorTests {

	private UserValidator validator;
	private Date today;
	private DbService dbService;
	
	@Before
	public void init() {
		validator = new UserValidator();
		today = new Date();
		
		validator.dbService = Mockito.mock(DbService.class);
		this.dbService = validator.dbService;

	}

	@Test
	public void testValidUsername() {
		doReturn(null).when(dbService).loadUser(anyString());
		assertTrue(validator.validateUsername("user"));
	}

	@Test
	public void testInvalidUsername() {
		doReturn(new User()).when(dbService).loadUser(anyString());
		assertFalse(validator.validateUsername("testUser"));
	}

	@Test
	public void test8CharPassword() {
		assertTrue(validator.validatePassword("abcdefgh"));
	}

	@Test
	public void test7CharPassword() {
		assertFalse(validator.validatePassword("abcdefg"));
	}

	@Test
	public void test3CharPassword() {
		assertFalse(validator.validatePassword("abc"));
	}

	@Test
	public void test12CharPassword() {
		assertTrue(validator.validatePassword("abcdefgh1234"));
	}

	@Test
	public void testEmptyStringPassword() {
		assertFalse(validator.validatePassword(""));
	}

	@Test
	public void testNullPassword() {
		assertFalse(validator.validatePassword(null));
	}

	@Test
	public void testEmptyName() {
		assertFalse(validator.validateNameOrSurname(""));
	}

	@Test
	public void testNullName() {
		assertFalse(validator.validateNameOrSurname(null));
	}

	@Test
	public void testNameWithNums() {
		assertFalse(validator.validateNameOrSurname("Brian9"));
	}

	@Test
	public void testLettersOnlyName() {
		assertTrue(validator.validateNameOrSurname("Jake"));
	}

	@Test
	public void testNameWithWhiteSpaces() {
		assertTrue(validator.validateNameOrSurname("Jake John"));
	}

	@Test
	public void testWhiteSpacesOnlyName() {
		assertFalse(validator.validateNameOrSurname(" "));
	}

	@Test
	public void testDob18YearsOld() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.YEAR, -18);
		Date dob = calendar.getTime();

		assertTrue(validator.validateDob(dob));
	}

	@Test
	public void testDob18YearsOldMinusOneDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.YEAR, -18);
		calendar.add(Calendar.DATE, 1);
		Date dob = calendar.getTime();

		assertFalse(validator.validateDob(dob));
	}

	@Test
	public void testDobFutureDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.YEAR, 1);
		Date dob = calendar.getTime();

		assertFalse(validator.validateDob(dob));
	}

	@Test
	public void testDob20YearsOld() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.YEAR, -20);
		Date dob = calendar.getTime();

		assertTrue(validator.validateDob(dob));
	}

	@Test
	public void testCvv000() {
		assertTrue(validator.validateCvv("000"));
	}

	@Test
	public void testCvv999() {
		assertTrue(validator.validateCvv("999"));
	}

	@Test
	public void testCvv512() {
		assertTrue(validator.validateCvv("512"));
	}

	@Test
	public void test2DigitCvv() {
		assertFalse(validator.validateCvv("12"));
	}

	@Test
	public void test4DigitCvv() {
		assertFalse(validator.validateCvv("1234"));
	}

	@Test
	public void testCvv00000() {
		assertFalse(validator.validateCvv("00000"));
	}

	@Test
	public void testCvvWithLetters() {
		assertFalse(validator.validateCvv("h56"));
	}

	@Test
	public void testCvvWithWhiteSpaces() {
		assertFalse(validator.validateCvv("05 6"));
	}

	@Test
	public void testPremiumNotFree() {
		assertTrue(validator.validateFreeOrPremium(true, false));
	}

	@Test
	public void testFreeNotPremium() {
		assertTrue(validator.validateFreeOrPremium(false, true));
	}

	@Test
	public void testNotFreeAndNotPremium() {
		assertFalse(validator.validateFreeOrPremium(false, false));
	}

	@Test
	public void testFreeAndPremium() {
		assertFalse(validator.validateFreeOrPremium(true, true));
	}

	@Test
	public void testCardExpiresTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, 1);

		Date expiry = calendar.getTime();

		assertTrue(validator.validateCCExpiry(expiry));
	}

	@Test
	public void testCardExpiresToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.SECOND, 2);

		Date expiry = calendar.getTime();

		assertTrue(validator.validateCCExpiry(expiry));
	}

	@Test
	public void testCardExpiredYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, -1);

		Date expiry = calendar.getTime();

		assertFalse(validator.validateCCExpiry(expiry));
	}

	@Test
	public void testAmericanExpressCardInitial37() {
		assertTrue(validator.validateCCNumber("378282246310005"));
	}

	@Test
	public void testAmericanExpressCardInitial34() {
		assertTrue(validator.validateCCNumber("343600267608555"));
	}

	@Test
	public void testMasterCardInitial51() {
		assertTrue(validator.validateCCNumber("5189979879493713"));
	}

	@Test
	public void testMasterCardInitial52() {
		assertTrue(validator.validateCCNumber("5237850415434598"));
	}

	@Test
	public void testMasterCardInitial53() {
		assertTrue(validator.validateCCNumber("5359015256295696"));
	}

	@Test
	public void testMasterCardInitial54() {
		assertTrue(validator.validateCCNumber("5421626287957775"));
	}

	@Test
	public void testMasterCardInitial55() {
		assertTrue(validator.validateCCNumber("5566531231928903"));
	}

	@Test
	public void testVisa16Digit() {
		assertTrue(validator.validateCCNumber("4596274040889084"));
	}

	@Test
	public void testVisa13Digit() {
		assertTrue(validator.validateCCNumber("4929411679018"));
	}
	
	@Test
	public void failAtLuhn()
	{
		assertFalse(validator.validateCCNumber("4929411679038"));
	}

	@Test
	public void testInvalidCCNumber() {
		assertFalse(validator.validateCCNumber("5821626287957775"));
	}
	
	@Test
	public void validUser()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, 1);
		Date expiry = calendar.getTime();
	
		calendar.setTime(today);
		calendar.add(Calendar.YEAR, -18);
		Date dob = calendar.getTime();
		
		String userName = "testUser";
		persistant.User user = new persistant.User(userName,"testtest","test","test",dob,"4929411679018",expiry,"005",true,false);
		dbService = Mockito.mock(DbService.class);
		Mockito.when(dbService.loadUser(userName)).thenReturn(null);
		
		assertTrue(validator.validate(user));
	}
}
