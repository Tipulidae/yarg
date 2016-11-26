package game;

import java.util.Random;

public class Control {
	private WorldMap wm;
	private GameInfo info;
	
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
		if (wm.territoryExists(t)) {
			wm.territory(t).addTroops(n);
			System.out.println("Adding "+n+" to "+t);
		} else {
			System.out.println("There is no territory by the name "+t+"!");
		}
	}
	
	public GameInfo getInfo() {
		return info;
	}
}


