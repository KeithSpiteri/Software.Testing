import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import db.services.DbService;
import persistant.Bet;
import persistant.User;
import validators.BetValidator;
import validators.UserValidator;

public class BetValidatorTests {

	BetValidator validator;
	
	@Before
	public void init() {
		validator = new BetValidator();
		validator.dbService = Mockito.mock(DbService.class);
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
	
	@Test
	public void validateFreeUserBetTooHigh()
	{
		User user = new User();
		user.setFree(true);
		
		Bet bet = new Bet();
		bet.setAmount(5.1f);
		
		assertFalse(validator.validateAmount(user, bet));
	}
	
	@Test
	public void validateNegativeBet()
	{
		Bet bet = new Bet();
		bet.setAmount(-0.1f);
		assertFalse(validator.validateAmount(null, bet));
	}
	
	@Test
	public void validateExceedMaxFreeBets(){
		User user = new User();
		user.setFree(true);
		
		Bet bet = new Bet();
		bet.setAmount(1);
		
		Mockito.when(validator.dbService.countBets(user)).thenReturn((long) 3);
		
		assertFalse(validator.validateAmount(user, bet));
	}
	
	@Test
	public void validateBelowMaxFreeBets(){
		User user = new User();
		user.setFree(true);
		
		Bet bet = new Bet();
		bet.setAmount(1);
						
		Mockito.when(validator.dbService.countBets(user)).thenReturn((long) 2);
		
		assertTrue(validator.validateAmount(user, bet));
	}
}
