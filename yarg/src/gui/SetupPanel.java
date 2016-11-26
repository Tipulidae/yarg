package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.Game;
import gui.GameFactory;

public class SetupPanel extends JPanel {
	public SetupPanel() {
		JButton startGameButton = new JButton("Start YARG");
		startGameButton.addActionListener(click);
		add(startGameButton);
	}
	
	private ActionListener click = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Game risk = GameFactory.gameWithInterface();
			risk.start();
		}
	};
}
