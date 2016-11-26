package game;

public enum Phase {
	START, REINFORCE, ATTACK, MOVE;
	
	public static Phase next(Phase p) {
		switch (p) {
		case START: return REINFORCE;
		case REINFORCE: return ATTACK;
		case ATTACK: return MOVE;
		case MOVE: return REINFORCE;
		default: return START;
		}
	}
}
