package gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import game.GameInfo;

public class MainWindow extends JFrame implements Observer {
	// region constructor

	/**
	 * creates the main window with a size of 1250 x 750 pixels with the map and
	 * a console.
	 */

	public MainWindow(MapPanel mp) {
		this.setSize(1250, 750);
		this.setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(mp, BorderLayout.CENTER);
		setVisible(true);
		
		// put window in the middle of the screen
		setLocationRelativeTo(null);
		
		// windows has a fixed size
		setResizable(true);

		// create console which logs all messages from System.out
		// Console console = new Console(1250, 100);
		// add console to window
		// add(console.getScrollPane(), BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object info) {
		GameInfo gi = (GameInfo)info;
		System.out.println(Thread.currentThread().getName()+": "+gi.toString());
		
	}
}