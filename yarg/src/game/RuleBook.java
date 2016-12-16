package game;

public class RuleBook {
	public static boolean isValid(Action action, Info state, WorldMapInfo wm) {
		switch (state.phase()) {
		case REINFORCE:
			return state.currentPlayerOwnsTerritory(action.from) && 
					state.reinforcementsRemaining() >= action.amount;
		case ATTACK:
			return state.currentPlayerOwnsTerritory(action.from) &&
					!state.currentPlayerOwnsTerritory(action.to) &&
					wm.territory(action.from).getTroops()-1 >= action.amount &&
					wm.borders(action.from, action.to);

				
		case MOVE:
			return true;
		default:
			break;
		}
		return false;
	}
}
