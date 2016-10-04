package Starsystem;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
import util.Faction;
import util.Selectable;

public class Star extends Selectable{
	private Faction faction;
	private String name;
	private Planet planets[];
	
	private int shipyardLevel;
	private double prosperity;
	
	/**
	 * Constructor for uninhabited system
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param name name of the system
	 * @param planets the planets in this system
	 */
	public Star( int x, int y, String name, Planet... planets){
		super(x,y);
		this.name     = name;
		this.planets  = planets;
		prosperity    = 0;
		shipyardLevel = 0;
	}
	
	/**
	 * Constructor for systems controlled by a faction
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param faction the faction controlling this system
	 * @param name name of the system
	 * @param planets the planets in this system
	 */
	public Star( int x, int y, Faction faction, String name, Planet... planets){
		super(x,y);
		this.faction  = faction;
		this.name     = name;
		this.planets  = planets;
		faction.addStar(this);
		prosperity    = 1;
		shipyardLevel = 1;
	}
	
	public Ship buildShip(boolean isJump, int engine, String name, int[] weapons, int[] defenses, int health, int cargoSize){
		int total = weapons[0] + weapons[1] + weapons[2] + defenses[0] + defenses[1] + defenses[2];
		int upkeep = total/4;
		upkeep += engine/5;
		
		Ship ship;
		if(isJump){
			ship = new JumpShip(this.getCoordinates(), this.faction, name, upkeep, health, cargoSize, engine, weapons, defenses);
			
		}else{
			ship = new WarpShip(this.getCoordinates(), this.faction, name, upkeep, health, cargoSize, engine, weapons, defenses);
		}
		
		return ship;
	}
	
	public String getName(){
		return name;
	}

	public Object getFaction() {
		return faction;
	}
	
	public void growPopulation(double rate){
		for(Planet planet : planets){
			planet.growPopulation(rate);
		}
	}

	public int getPopulation() {
		int popCount = 0;
		for(Planet planet : planets){
			popCount += planet.getPopulation();
		}
		return popCount;
	}
	
	public int getPopCap(){
		int popCapCount = 0;
		for(Planet planet : planets){
			popCapCount += planet.getPopCap();
		}
		return popCapCount;
	}
	

	public Planet[] getPlanets() {
		return planets;
	}

	public double getProsperity() {
		return prosperity;
	}
	
	public void setProsperity(double prosperity){
		this.prosperity = prosperity;
	}

	public double getIncome() {
		return getPopulation() * prosperity * faction.getTaxRate();
	}

	public int getShipyardLevel() {
		return shipyardLevel;
	}
}
