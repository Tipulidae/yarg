package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.JPanel;

import game.ClickMonitor;

public class MapPanel extends JPanel {
    private Collection<Land> lands;
    private Map<String,LandColor> playerColors = new HashMap<String,LandColor>();
    
    public MapPanel(Collection<Land> lands, ClickMonitor cm) {
        setLayout(null);
        setPreferredSize(new Dimension(1250,650));
        setBackground(Color.CYAN);
        
        
        playerColors.put("red", new LandColor(new Color(255, 36, 36), new Color(152, 0, 0), new Color(105, 0, 0)));
        playerColors.put("green", new LandColor(new Color(9, 255, 9), new Color(0, 176, 0), new Color(0, 98, 0)));
        playerColors.put("blue", new LandColor(new Color(9, 9, 255), new Color(0, 0, 98), new Color(0, 0, 176)));
        playerColors.put("yellow", new LandColor(new Color(255, 255, 128), new Color(255, 255, 51), new Color(255, 255, 0)));
        playerColors.put("magenta", new LandColor(new Color(153, 0, 150), new Color(102, 0, 100), new Color(77, 0, 75)));
        playerColors.put("cyan", new LandColor(new Color(51, 255, 204), new Color(0, 128, 96), new Color(0, 179, 134)));
        playerColors.put("nobody", new LandColor(Color.white, Color.lightGray, Color.gray));
        
        this.lands = lands;
        for (Land land : lands) {
        	land.setBounds(0,0,1250,750);
    		land.setPlayerColors(playerColors);
    		add(land);
    	}
    	//repaint();
    	
    	addMouseListener(new MouseAdapter() {
    		private Land selected = Land.NULL;
    		
    		@Override
    		public void mousePressed(MouseEvent e) {
    			requestFocusInWindow();
    			Land clicked = whereIs(e.getPoint());
    			
				selected.unfocus();
				clicked.focus();
				selected = clicked;
				cm.click(clicked.toString());
    		}
    	});
    	
    	addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			cm.keyDown(e.getKeyCode());
    		}
    		
    		@Override
    		public void keyReleased(KeyEvent e) {
    			cm.keyUp(e.getKeyCode());
    		}
		});
    }
    
    
    private Land whereIs(Point p) {
    	try {
	    	return lands.stream().
	    		filter(land -> land.closeEnough(p)).
	    		sorted(new Comparator<Land>() {
					@Override
					public int compare(Land o1, Land o2) {
						return (int)Math.signum(o1.distance(p)-o2.distance(p));
					}}).
	    		findFirst().get();
    	} catch (NoSuchElementException e) {
    		return Land.NULL;
    	}
    }
}
