package game;

import java.util.List;

public interface Territory {
	public int getTroops();
	public String getOwner();
	public List<Territory> getNeighbors();
}
