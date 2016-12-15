package game;

import java.util.Random;

public class Control {
	private WorldMap wm;
	private GameInfo info;
	private String currentPlayer;
	private Phase phase = Phase.START;
	private int reinforcements;
	
	public Control(WorldMap wm) {
		this.wm = wm;
		info = new GameInfo(wm);
	}
	
	
	// Temporary function
	public void randomizeTerritoryOwners() {
		Random r = new Random();
		for (TerritoryActual t : wm.allTerritories()) {
			t.setOwner("nobody");
			
			switch (r.nextInt(2)) {
			case 0:
				t.setOwner("red");
				break;
			case 1:
				t.setOwner("green");
				break;
			case 2:
				t.setOwner("blue");
				break;
			default:
				break;
			}
		}
	}
	
	public void addReinforcements(String t, int n) {
		if (!wm.territoryExists(t) || n <= 0 || !wm.ownerOf(t).equals(currentPlayer)) return;
		n = n > reinforcements ? reinforcements : n;
		
		wm.territory(t).addTroops(n);
		System.out.println("Adding " + n + " to " + t);
		
		reinforcements -= n;
		if (reinforcements == 0) {
			phase = Phase.next(phase);
			System.out.println("Reinforcement Phase Complete!");
		}
	}
	
	public void attack(String from, String to) {
		// TODO
	}
	
	public GameInfo getInfo() {
		return info;
	}
	
	public Phase phase() {
		return phase;
	}
	
	public void prepareNextTurn(String player) {
		currentPlayer = player;
		phase = Phase.REINFORCE;
		reinforcements = 10;
	}
}


