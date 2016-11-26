package main;

import javax.swing.SwingUtilities;

import game.Game;
import gui.GameFactory;
import gui.SetupWindow;

public class YetAnotherRiskGame {
	public static void main(String[] args) {
		//new YetAnotherRiskGame().gameWithSetup();
		new YetAnotherRiskGame().gameWithoutSetup();
	}
	
	public void gameWithSetup() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SetupWindow();
			}
		});
	}
	
	public void gameWithoutSetup() {
		Game risk = GameFactory.gameWithInterface();
		risk.start();
	}
}
