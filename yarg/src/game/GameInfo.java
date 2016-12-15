package game;


// TODO
public class GameInfo {
	// Change to map of Territory?
	private WorldMap wm;
	private String currentPlayer;
	
	public GameInfo(WorldMap wm) {
		this.wm = wm;
	}
	
	public String ownerOf(String territory) {
		return wm.ownerOf(territory);
	}
	
	public int reinforcementsRemaining() {
		return 0;
	}
}
