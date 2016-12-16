package game;

import java.util.Collection;
import java.util.Map;

public class WorldMap implements WorldMapInfo {
	//private Graph<Territory,DefaultEdge> world;
	private Map<String,Continent> continents;
	private Map<String,TerritoryActual> world;
	
	public WorldMap(Map<String,TerritoryActual> world, Map<String,Continent> continents) {
		this.world = world;
		this.continents = continents;
	}
	
	@Override
	public boolean territoryExists(String name) {
		return world.containsKey(name);
	}
	
	@Override
	public Collection<String> allTerritories() {
		return world.keySet();
	}
	
	@Override
	public String ownerOf(String territory) {
		if (world.containsKey(territory)) {
			return world.get(territory).getOwner();
		} else {
			return "nobody";
		}
	}
	
	@Override
	public boolean isOwnerOf(String player, String territory) {
		return player.equals(ownerOf(territory));
	}
	
	@Override
	public boolean borders(String a, String b) {
		if (!territoryExists(a) || !territoryExists(b)) return false;
		return world.get(a).borders(world.get(b));
//		return false;
	}
	
	@Override
	public int calculateReinforcements(String player) {
		int n = world.values().stream().
				filter(t -> player.equals(t.getOwner())).
				mapToInt(e -> 1).sum();
		
		int conts = continents.values().stream().
			filter(c -> player.equals(c.getOwner())).
			mapToInt(c -> c.value()).sum();
		
		//System.out.println("territories="+n+", continents="+conts);
		return conts+Math.max(3, n/3);
	}
	
	@Override
	public TerritoryActual territory(String name) {
		return world.get(name);
	}
	
	public void setOwner(String territory, String player) {
		if (!world.containsKey(territory)) return;
		world.get(territory).setOwner(player);
		
		continents.values().stream().
			filter(c -> c.containsTerritory(territory)).
			forEach(c -> {
				if (c.territories().allMatch(t -> isOwnerOf(player,t)))
					c.setOwner(player);
				else
					c.setOwner("nobody");
			});
		
	}

	
}
