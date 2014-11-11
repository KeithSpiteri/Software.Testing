import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import validators.UserValidator;

public class UserValidatorTests {

	private UserValidator validator;
	private Date today;

	@Before
	public void init() {
		validator = new UserValidator();
		today = new Date();
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
	public void testDob17YearsOld() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.YEAR, -17);
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
	public void testCvv999(){
		assertTrue(validator.validateCvv("999"));
	}
	
	@Test
	public void testCvv512(){
		assertTrue(validator.validateCvv("512"));
	}
	
	@Test
	public void test2DigitCvv(){
		assertFalse(validator.validateCvv("12"));
	}
	
	@Test
	public void test4DigitCvv(){
		assertFalse(validator.validateCvv("1234"));
	}
	
	@Test
	public void testCvv00000(){
		assertFalse(validator.validateCvv("00000"));
	}
	
	@Test
	public void testCvvWithLetters(){
		assertFalse(validator.validateCvv("h56"));
	}
	
	@Test
	public void testCvvWithWhiteSpaces(){
		assertFalse(validator.validateCvv("05 6"));
	}
}
