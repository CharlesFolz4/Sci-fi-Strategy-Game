package Starsystem;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
import util.BuildQueue;
import util.Faction;
import util.Selectable;

public class Star extends Selectable{
	private Faction faction;
	private String name;
	private Planet planets[];
	
	private int shipyardLevel;
	private double prosperity;
	
	private int soldiers;
	
	private BuildQueue<Ship> shipQueue;
	
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
		shipQueue     = new BuildQueue<Ship>();
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
		shipQueue     = new BuildQueue<Ship>();
		soldiers = 100;
	}
	
	//TODO: Outdated.  Replace using the BuildQueue
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
	
	public void setSoldiers(int soldiers){
		this.soldiers = soldiers;
	}
	
	public void addSoldiers(int amount){
		this.soldiers += amount;
	}
	
	public int getSoldiers(){
		return soldiers;
	}
	
	public String getName(){
		return name;
	}

	public Faction getFaction() {
		return faction;
	}
	
	public void growPopulation(double rate){
		for(Planet planet : planets){
			planet.growPopulation(rate);
		}
	}
	
	public void changePopulation(int totalChange){
		if(totalChange > 0){ //if positive
			while(totalChange > 0){
				boolean popMaxed = true;
				for(Planet planet : planets){
					//add until popCap is reached, 
					//then try to fill extra on other planets in the system, 
					//then spread them evenly
					if(planet.getPopCap() > planet.getPopulation()){//if there's room
						planet.setPopulation(planet.getPopulation() + 1);
						totalChange--;
					} 
					popMaxed &= planet.getPopulation() >= planet.getPopCap();
				}
				if(popMaxed){
					for(Planet planet : planets){
						planet.setPopulation(planet.getPopulation() + 1);
						totalChange--;
					}
				}
			}
		} else if (totalChange < 0){ //if negative
			while(totalChange < 0){
				boolean flag = true;
				for(Planet planet : planets){
					if(planet.getPopulation() > 0){
						planet.setPopulation(planet.getPopulation() - 1);
						totalChange++;
					} 
					flag &= !(planet.getPopulation() > 0);
				}
				if(flag){
					break;
				}
			}
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

	public void setFaction(Faction faction) {
		this.faction = faction;
	}
}
