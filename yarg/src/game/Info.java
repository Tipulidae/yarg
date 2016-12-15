package game;

public interface Info {
	public Phase phase();
	public String currentPlayer();
	public int reinforcementsRemaining();
	public int previouslyMoved(String from);
	public boolean currentPlayerOwnsTerritory(String t);
}
