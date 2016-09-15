package Starsystem;

import util.Faction;
import util.Location;
import util.Selectable;

public class Star extends Selectable{
	private Faction faction;
	private String name;
	private Planet planets[];
	
	private boolean hasShipyard;
	
	public Star( int x, int y, String name, String type, Planet... planets){
		super(x,y);
		this.name    = name;
		this.planets = planets;
	}
	
	public Star( int x, int y, Faction faction, String name, Planet... planets){
		super(x,y);
		this.faction = faction;
		this.name    = name;
		this.planets = planets;
	}
	
	public String getName(){
		return name;
	}

	public Object getFaction() {
		return faction;
	}

	public int getPopulation() {
		int popCount = 0;
		for(Planet planet : planets){
			popCount += planet.getPopulation();
		}
		return popCount;
	}

	public Planet[] getPlanets() {
		return planets;
	}
}