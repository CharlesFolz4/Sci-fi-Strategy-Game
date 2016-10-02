package Ships;

import util.Faction;
import util.Selectable;

public abstract class Ship extends Selectable{
	private Faction faction;
	private String name;
	private String captain;//purely for flavor for now, may add actual officers later
	
	private int[] destination;
	private int upkeep;
	
	private int health;
	private int maxHealth;
	private int supplies;
	private int sensorRange;
	
	private int[] weapons;
	private int[] defenses;
	private int cargoSpace;
	private String cargoType;
	private int cargoSize;
	
	public Ship(int x, int y, int upkeep, Faction faction, String name, int health){
		super(x, y);
		this.upkeep  = upkeep;
		this.faction = faction;
		this.name    = name;
		this.faction.addShip(this);
		this.maxHealth = health;
		this.health    = health;
		destination    = new int[2];
		destination[0] = x;
		destination[1] = y;
	}
	
	public Ship(int[] coordinates, Faction faction, String name, int upkeep, int health, int cargoSpace, int[] weapons, int[] defenses) {
		super(coordinates[0], coordinates[1]);
		this.faction    = faction;
		this.name       = name;
		this.upkeep     = upkeep;
		this.health     = health;
		this.maxHealth  = health;
		this.cargoSpace = cargoSpace;
		this.cargoType  = "None";
		this.cargoSize  = 0;
		
		this.weapons    = weapons;
		this.defenses   = defenses;
		
		destination     = new int[2];
		destination[0]  = coordinates[0];
		destination[1]  = coordinates[1];
		
	}
	
	public void emptyCargo(){
		cargoType = "none";
		cargoSize = 0;
	}
	
	public void addCargo(int amount){
		cargoSize += amount;
	}
	
	public void setCargoType(String cargoType){
		this.cargoType = cargoType;
	}
	
	public int getCargoSize(){
		return cargoSize;
	}
	
	public String getCargoType(){
		return cargoType;
	}
	
	public int getCargoSpace(){
		return cargoSpace;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getCurrentHealth(){
		return health;
	}
	
	public void takeDamage(int damage){
		health -= damage;
	}

	public Faction getFaction(){
		return faction;
	}
	
	public String getName(){
		return name;
	}
	
	public int[] getDestination(){
		return destination;
	}
	
	public void setDestination(int x, int y){
		destination[0] = x;
		destination[1] = y;
	}
	
	public int getUpkeep(){
		return upkeep;
	}

	public int[] getWeapons() {
		return weapons;
	}
	
	public int[] getDefenses(){
		return defenses;
	}
	
}
