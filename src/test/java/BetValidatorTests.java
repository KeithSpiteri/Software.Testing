import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import persistant.Bet;
import persistant.User;
import validators.BetValidator;
import validators.UserValidator;

public class BetValidatorTests {

	BetValidator validator;
	
	@Before
	public void init() {
		validator = new BetValidator();
		}

	@Test
	public void validateFreeLow()
	{
		User user = new User();
		user.setFree(true);
		
		Bet bet = new Bet();
		bet.setRiskLevel("low");
		
		assertTrue(validator.validateRiskLevel(user,bet));
	}
	
	@Test
	public void validateFreeMedium()
	{
		User user = new User();
		user.setFree(true);
		
		Bet bet = new Bet();
		bet.setRiskLevel("medium");
		
		assertFalse(validator.validateRiskLevel(user,bet));
	}
	
	@Test
	public void validateFreeHigh()
	{
		User user = new User();
		user.setFree(true);
		
		Bet bet = new Bet();
		bet.setRiskLevel("high");
		
		assertFalse(validator.validateRiskLevel(user,bet));
	}
	
	@Test
	public void validatePremiumLow()
	{
		User user = new User();
		user.setPremium(true);
		
		Bet bet = new Bet();
		bet.setRiskLevel("low");
		
		assertTrue(validator.validateRiskLevel(user,bet));
	}
	
	@Test
	public void validatePremiumMedium()
	{
		User user = new User();
		user.setPremium(true);
		
		Bet bet = new Bet();
		bet.setRiskLevel("medium");
		
		assertTrue(validator.validateRiskLevel(user,bet));
	}
	
	@Test
	public void validatePremiumHigh()
	{
		User user = new User();
		user.setPremium(true);
		
		Bet bet = new Bet();
		bet.setRiskLevel("high");
		
		assertTrue(validator.validateRiskLevel(user,bet));
	}
}
