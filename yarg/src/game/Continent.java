package game;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class Continent {
	private Set<String> territories;
	private int value;
	private String name;
	
	private String owner;
	
	public Continent(String name, int value, Set<String> territories) {
		this.name = name;
		this.value = value;
		this.territories = territories;
		owner = "nobody";
	}
	
	public void setOwner(String owner) {
		System.out.println("owner of "+name+" = "+owner);
		this.owner = owner; 
	}
	
	public String getOwner() {
		return owner;
	}
	
	public int value() {
		return value;
	}
	
	public Stream<String> territories() {
		return territories.stream();
	}
	
	public boolean containsTerritory(String territory) {
		return territories.contains(territory);
	}
}
