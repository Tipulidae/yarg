package gui;

import java.awt.Color;

public class LandColor {
	private Color[] color;
	
	private static final int NORMAL = 0;
	private static final int NEIGHBOR = 1;
	private static final int SELECTED = 2;
	
	private int current;
	
	public LandColor(Color normal, Color neighbor, Color selected) {
		color = new Color[3];
		color[NORMAL] = normal;
		color[NEIGHBOR] = neighbor;
		color[SELECTED] = selected;
		
		current = NORMAL;
	}
	
	public Color getColor() {
		return color[current];
	}
	
	public void setSelected() {
		current = SELECTED;
	}
	
	public void setNeighbor() {
		current = NEIGHBOR;
	}
	
	public void setNormal() {
		current = NORMAL;
	}

	public static LandColor copy(LandColor lc) {
		LandColor col = new LandColor(lc.color[0], lc.color[1], lc.color[2]);
		col.current = lc.current;
		return col;
	}
	
	public void shareSelectionStatusWith(LandColor lc) {
		current = lc.current;
	}
	
	public static final LandColor white = new LandColor(Color.white, Color.lightGray, Color.gray);
}
