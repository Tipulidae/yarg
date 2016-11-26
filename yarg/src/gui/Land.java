package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import game.TerritoryInfo;

public class Land extends Component implements Observer {
	private List<Patch> areas = new ArrayList<Patch>();
	private Rectangle bounds;
	
	private List<Land> neighbors = new ArrayList<Land>();
	private Map<String,LandColor> playerColors;
	private Point capital = new Point();
	private String name = "Nobody";
	private int troops = 0;
	
	private String owner;
	
	private LandColor color = LandColor.white;
	
	public Land(String name) {
		this.name = name;
	}
	
	public void focus() {
		color.setSelected();
		for (Land n : neighbors) {
			n.color.setNeighbor();
			//n.repaint();
		}
		repaint();
	}
	
	public void unfocus() {
		color.setNormal();
		for (Land n : neighbors) {
			n.color.setNormal();
			//n.repaint();
		}
		repaint();
	}
	
	
	public void addPatchToLand(Patch p) {
		areas.add(p);
		if (bounds == null) {
			bounds = p.getBounds();
		} else {
			bounds = (Rectangle) bounds.createUnion(p.getBounds());
		}
	}
	
	public void setCapital(Point p) {
		capital = p;
	}
	
	public void addNeighbor(Land land) {
		neighbors.add(land);
	}
	
	public void setPlayerColors(Map<String,LandColor> playerColors) {
		this.playerColors = playerColors;
		updateColor();
	}
	
	@Override
	public void paint(Graphics g) {
		for (Patch p : areas) {
    		g.setColor(color.getColor());
            g.fillPolygon(p);
            
    		g.setColor(Color.black);
    		g.drawPolygon(p);
    	}
		
		//g.setColor(Color.BLACK);
        g.drawString("" + troops, (int)capital.getX(), (int)capital.getY());
	}
	
	public boolean contains(Point p) {
		for (Patch patch : areas) {
			if (patch.contains(p)) return true;
		}
		return false;
	}
	
	public boolean closeEnough(Point p) {
		return bounds.contains(p);
	}
	
	// Distance to the capital, -1 if inside the border.
	// If it ever comes to it, there is probably some algorithm to 
	// find the closest distance from the edge to the point, but 
	// it's kind of a lot of work. 
	public double distance(Point p) {
		if (contains(p)) return -1.0;
		else return p.distance(capital);
	}
	
	
	private void updateColor() {
		if (playerColors == null || !playerColors.containsKey(owner)) return;
		LandColor newColor = LandColor.copy(playerColors.get(owner));
		newColor.shareSelectionStatusWith(color);
		color = newColor;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Land) {
			Land l = (Land)o;
			return name.equals(l.name);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
	@Override
	public void update(Observable o, Object obj) {
		//System.out.println("Land update! Thread: " + Thread.currentThread().getName());
		TerritoryInfo info = (TerritoryInfo)obj;
		troops = info.troops;
		owner = info.name;
		updateColor();
		repaint(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public static final Land NULL = new Land("Null") {
		@Override
		public void addPatchToLand(Patch p) {};
		
		@Override
		public void addNeighbor(Land l) {};
	};
}
