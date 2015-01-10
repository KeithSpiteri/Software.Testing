import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import persistant.Bet;
import persistant.User;
import validators.BetValidator;
import db.services.DbService;

public class BetValidatorTests {

	BetValidator validator;
	DbService dbService;

	@Before
	public void init() {
		validator = new BetValidator();

		validator.dbService = Mockito.mock(DbService.class);
		this.dbService = validator.dbService;
	}

	@Test
	public void validateFreeLow() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setRiskLevel("low");

		assertTrue(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void validateFreeMedium() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setRiskLevel("medium");

		assertFalse(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void validateFreeHigh() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setRiskLevel("high");

		assertFalse(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void validatePremiumLow() {
		User user = new User();
		user.setPremium(true);

		Bet bet = new Bet();
		bet.setRiskLevel("low");

		assertTrue(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void validatePremiumMedium() {
		User user = new User();
		user.setPremium(true);

		Bet bet = new Bet();
		bet.setRiskLevel("medium");

		assertTrue(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void validatePremiumHigh() {
		User user = new User();
		user.setPremium(true);

		Bet bet = new Bet();
		bet.setRiskLevel("high");

		assertTrue(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void validateFreeUserBetTooHigh() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setAmount(5.1f);

		assertFalse(validator.validateAmount(user, bet));
	}

	@Test
	public void validateNegativeBet() {
		Bet bet = new Bet();
		bet.setAmount(-0.1f);
		assertFalse(validator.validateAmount(null, bet));
	}

	@Test
	public void validateExceedMaxFreeBets() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setAmount(1);

		doReturn((long) 3).when(dbService).countBets(user);

		assertFalse(validator.validateAmount(user, bet));
	}

	@Test
	public void validateBelowMaxFreeBets() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setAmount(1);

		doReturn((long) 2).when(dbService).countBets(user);

		assertTrue(validator.validateAmount(user, bet));
	}

	@Test
	public void validatePremiumBelowLimit() {
		User user = new User();
		user.setPremium(true);

		List<Bet> bets = new ArrayList<Bet>();
		Bet bet = new Bet();
		bet.setAmount(4999f);
		bets.add(bet);

		doReturn(bets).when(dbService).getUserBets(user);

		Bet newBet = new Bet();
		newBet.setAmount(1f);

		assertTrue(validator.validateAmount(user, newBet));
	}

	@Test
	public void validatePremiumAboveLimit() {
		User user = new User();
		user.setPremium(true);

		List<Bet> bets = new ArrayList<Bet>();
		Bet bet1 = new Bet();
		bet1.setAmount(2500f);
		Bet bet2 = new Bet();
		bet2.setAmount(2500f);

		bets.add(bet1);
		bets.add(bet2);

		doReturn(bets).when(dbService).getUserBets(user);

		Bet newBet = new Bet();
		newBet.setAmount(1f);

		assertFalse(validator.validateAmount(user, newBet));
	}

	@Test
	public void riskLevelDoesNotExist() {
		User user = new User();
		user.setPremium(true);

		Bet bet = new Bet();
		bet.setRiskLevel("InvalidRiskLevel");

		assertFalse(validator.validateRiskLevel(user, bet));
	}

	@Test
	public void invalidRiskLevel() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setRiskLevel("medium");

		assertFalse(validator.validateBet(user, bet));
	}

	@Test
	public void invalidAmount() {
		User user = new User();
		user.setFree(true);

		Bet bet = new Bet();
		bet.setRiskLevel("low");
		bet.setAmount(6f);

		assertFalse(validator.validateBet(user, bet));
	}

	@Test
	public void validBet() {
		User user = new User();
		user.setFree(false);

		Bet bet = new Bet();
		bet.setRiskLevel("low");
		bet.setAmount(1f);

		assertTrue(validator.validateBet(user, bet));
	}
}
