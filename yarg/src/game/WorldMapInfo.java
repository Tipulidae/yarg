package game;

import java.util.Collection;

public interface WorldMapInfo {
	public boolean territoryExists(String name);
	public Collection<String> allTerritories();
	public String ownerOf(String territory);
	public boolean isOwnerOf(String player, String territory);
	public int calculateReinforcements(String player);
	public Territory territory(String t);
	public boolean borders(String a, String b);
}
