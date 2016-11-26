package game;

import java.util.Collection;
import java.util.Map;

public class WorldMap {
	//private Graph<Territory,DefaultEdge> world;
	//private Set<Continent> continents;
	private Map<String,TerritoryActual> world;
	
	public WorldMap(Map<String,TerritoryActual> world) {
		this.world = world;
	}
	
	public boolean territoryExists(String name) {
		return world.containsKey(name);
	}
	
	public TerritoryActual territory(String name) {
		return world.get(name);
	}
	
	public Collection<TerritoryActual> allTerritories() {
		return world.values();
	}
	
	public String ownerOf(String territory) {
		if (world.containsKey(territory)) {
			return world.get(territory).getOwner();
		} else {
			return "nobody";
		}
	}
}
