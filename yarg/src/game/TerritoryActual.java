package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.SwingUtilities;

public class TerritoryActual extends Observable implements Territory {
	private final String name;
	private String owner = "nobody";
	private int troops = 0;
	private List<Territory> neighbors = new ArrayList<Territory>();
	
	public TerritoryActual(String name) {
		this.name = name;
	}
	
	
	@Override
	public int getTroops() {
		return troops;
	}
	
	@Override
	public String getOwner() {
		return owner;
	}
	
	@Override
	public List<Territory> getNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean borders(Territory t) {
		return neighbors.contains(t);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof TerritoryActual) {
			return ((TerritoryActual)o).name.equals(name);
		}
		return false;
	}
	
	public void addTroops(int amount) {
		troops += amount;
		update();
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
		update();
	}
	
	public void addNeighbor(TerritoryActual t) {
		neighbors.add(t);
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
	
	
}
