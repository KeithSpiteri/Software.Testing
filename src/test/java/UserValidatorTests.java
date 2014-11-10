import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import validators.UserValidator;

public class UserValidatorTests {

	private UserValidator validator;

	@Before
	public void init() {
		validator = new UserValidator();
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
}
