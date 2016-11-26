package game;

import java.util.List;
import java.util.Observable;

import javax.swing.SwingUtilities;

public class TerritoryActual extends Observable implements Territory {
	//private String name;
	private String owner = "nobody";
	private int troops = 0;
	/*
	public Territory(String name) {
		this.name = name;
	}
	*/
	public int getTroops() {
		return troops;
	}
	
	public void addTroops(int amount) {
		troops += amount;
		update();
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
		update();
	}
	
	public String getOwner() {
		return owner;
	}
	
	private void update() {
		setChanged();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(new TerritoryInfo(owner,troops));
			}
		});
		//notifyObservers(new TerritoryInfo(owner,troops));
	}
	
	public List<Territory> getNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}
}
