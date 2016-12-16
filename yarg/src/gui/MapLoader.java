package gui;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.Continent;
import game.Territory;
import game.TerritoryActual;
import game.WorldMap;

// Temporary class just to get things rolling
public class MapLoader {
	private static final String nameR = "[a-zA-Z\\s]+";
	private static final String pointR = "\\s*\\d+\\s+\\d+\\s*";
	private static final String pointsR = "\\s*(?:\\d+\\s+\\d+\\s+)*(?:\\d+\\s+\\d+\\s*)?";
	
	private static final String patchR = "patch-of ("+nameR+") ("+pointsR+")";
	private static final String capitalR = "capital-of ("+nameR+") ("+pointR+")";
	private static final String neighborsR = "neighbors-of ("+nameR+") : ((?:"+nameR+" - )*"+nameR+")?";
	private static final String continentR = "continent ("+nameR+") (\\d+) : ((?:"+nameR+" - )*"+nameR+")?";
	
	private static final String completeRegexp = 
			"(?<patch>"+patchR+
			")|(?<capital>"+capitalR+
			")|(?<neighbors>"+neighborsR+
			")|(?<continent>"+continentR+")";
	

    private Pattern pattern = Pattern.compile(completeRegexp);
    
	private String mapname;
	
    private Map<String,Land> lands = new HashMap<String,Land>();
    private Map<String,TerritoryActual> territories = new HashMap<String,TerritoryActual>();
    private Map<String,Continent> continents = new HashMap<String,Continent>();
    private WorldMap wm;
    
    public MapLoader(String mapname) {
    	this.mapname = mapname;
    }
    
    public Collection<Land> getLands() {
		return lands.values();
	}
    
    public WorldMap getWorldMap() {
    	return wm;
    }
    
    
    
    public void loadWithGUI() {
    	loadWithoutGUI();
    	addObservers();
    }
    
	public void loadWithoutGUI() {
		try (BufferedReader reader = new BufferedReader(new FileReader(mapname))) {
			reader.lines().forEach(str -> parse(str));
			loadWorldMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadWorldMap() {
		/*
		Map<String,TerritoryActual> territories = new HashMap<String,TerritoryActual>();
    	for (Land land : lands.values()) {
    		territories.put(land.toString(), new TerritoryActual());
    	}*/
    	wm = new WorldMap(territories, continents);
	}
	
	private void parse(String line) {
		Matcher m = pattern.matcher(line);
		if (m.matches()) {
			if (m.group("patch") != null) 
				parseLand(m.group(2),m.group(3));
			else if (m.group("capital") != null) 
				parseCapital(m.group(5), m.group(6));
			else if (m.group("neighbors") != null) 
				parseNeighbors(m.group(8), m.group(9));
			else if (m.group("continent") != null)
				parseContinent(m.group(11),m.group(12), m.group(13));
		}
	}
	
	
	private void parseLand(String name, String points) {
		Patch p = parsePatch(points);
		getLand(name).addPatchToLand(p);
	}
	
	private void parseCapital(String name, String point) {
		String[] xy = point.split(" ");
		getLand(name).setCapital(new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
	}
	
	private void parseNeighbors(String name, String neighbors) {
		String[] ns = neighbors.split(" - ");
		Land land = getLand(name);
		TerritoryActual t = territories.get(name);
		for (String n : ns) {
			land.addNeighbor(getLand(n));
			t.addNeighbor(territories.get(n));
		}
	}
	
	private void parseContinent(String name, String value, String lands) {
		String[] ls = lands.split(" - ");
		//Set<Territory> ts = new HashSet<Territory>();
		Continent c = new Continent(name, Integer.parseInt(value), new HashSet<String>(Arrays.asList(ls)));
		continents.put(name, c);
	}
	
	private Patch parsePatch(String str) {
		String[] nums = str.split(" ");
		int npoints = nums.length / 2;
		int[] x = new int[npoints];
		int[] y = new int[npoints];
		
		for (int i = 0; i<npoints; i++) {
			x[i] = Integer.parseInt(nums[2*i]);
			y[i] = Integer.parseInt(nums[2*i+1]);
		}
		return new Patch(x,y,npoints);
	}
	
	private Land getLand(String name) {
		if (lands.containsKey(name)) {
			return lands.get(name);
		} else {
			Land land = new Land(name);
			lands.put(name,land);
			territories.put(name, new TerritoryActual(name));
			return land;
		}
	}
	
	private void addObservers() {
		lands.values().forEach(land -> 
			wm.territory(land.toString()).
				addObserver(land));
    }
}
