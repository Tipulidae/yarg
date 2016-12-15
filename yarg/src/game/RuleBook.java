package game;

public class RuleBook {
	public static boolean isValid(Action action, Info state) {
		switch (state.phase()) {
		case REINFORCE:
			return state.currentPlayerOwnsTerritory(action.from) && 
					state.reinforcementsRemaining() >= action.amount;
		case ATTACK:
		case MOVE:
			return true;
		default:
			break;
		}
		return false;
	}
}
