package validators;

import java.util.List;

import db.services.DbService;
import persistant.Bet;
import persistant.User;

public class BetValidator {

	public boolean validateBet(User user, Bet bet) {
		return validateRiskLevel(user, bet) && validateAmount(user, bet);
	}

	public boolean validateRiskLevel(User user, Bet bet) {
		if (!bet.getRiskLevel().equals("low")
				&& !bet.getRiskLevel().equals("medium")
				&& !bet.getRiskLevel().equals("high"))
			return false;
		if (user.isFree() && !bet.getRiskLevel().equals("low"))

			return false;
		else
			return true;
	}

	public boolean validateAmount(User user, Bet bet) {
		DbService dbService = DbService.getInstance();
		if (bet.getAmount() > 0) {
			if (user.isFree()) {
				if (bet.getAmount() > 5)
					return false;

				if (dbService.countBets(user) >= 3)
					return false;
			}
			// Test for Premium User bets
			else {
				List<Bet> userBets = (List<Bet>) dbService.getUserBets(user);

				double total = bet.getAmount();
				for (Object o : userBets) {
					Bet prevBet = (Bet) o;
					total += prevBet.getAmount();
				}

				if (total > 5000)
					return false;
			}
		} else
			return false;

		return true;
	}
}
