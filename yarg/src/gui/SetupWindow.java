package gui;

import javax.swing.JFrame;

public class SetupWindow extends JFrame {
	public SetupWindow () {
		this.setSize(500, 300);
        this.setTitle("Risk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SetupPanel setup = new SetupPanel();
        setup.setOpaque(true);
        setContentPane(setup);
        
        //pack();
        
        // put window in the middle of the screen
        setLocationRelativeTo(null);
        setResizable(true);
        
        setVisible(true);
        
	}
	
	
}
