package game;

public class GameInfo {
	public final Phase phase;
	public final String player;
	public final int reinforcements;
	
	public GameInfo(Phase phase, String player, int reinforcements) {
		this.phase = phase;
		this.player = player;
		this.reinforcements = reinforcements;
	}
	
	
	@Override
	public String toString() {
		return "Phase: "+phase+", player: "+player+", reinforcements: "+reinforcements;
	}
}
