package gui;

import javax.swing.SwingUtilities;

import game.Game;


// Should this be in a separate package?
public class GameFactory {
	public static Game gameWithInterface() {
		MapLoader loader = new MapLoader("maps/world.map");
		loader.loadWithGUI();
		
		Game risk = new Game(loader.getWorldMap());
		risk.addHuman("red");
		risk.addComputer("green");
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindow(new MapPanel(loader.getLands(), risk.getClickMonitor()));
			}
		});
		
		return risk;
	}
	
	public static Game gameWithoutInterface() {
		MapLoader loader = new MapLoader("maps/world.map");
		loader.loadWithoutGUI();
		
		Game risk = new Game(loader.getWorldMap());
		risk.addHuman("red");
		risk.addComputer("green");
		
		return risk;
	}
}
