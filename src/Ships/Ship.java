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
	private int sensorRange;
	
	private int[] weapons;
	private int[] defenses;
	private int cargoSpace;
	private double supplies;
	private int cargoPeople; //1 population unit takes 2 cargo spaces?
	private int soldiers;
	
	public Ship(int x, int y, int upkeep, Faction faction, String name, int health){
		super(x, y);
		this.upkeep  = upkeep;
		this.faction = faction;
		this.name    = name;
		this.faction.addShip(this);
		this.maxHealth = health;
		this.health    = health;
		this.weapons   = new int[3];
		this.defenses  = new int[3];
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
		this.supplies   = cargoSpace;
		
		this.weapons    = weapons;
		this.defenses   = defenses;
		
		destination     = new int[2];
		destination[0]  = coordinates[0];
		destination[1]  = coordinates[1];
		
		
		this.faction.addShip(this);
		this.cargoPeople = 0;
	}
	
	
	
	public int getCargoSpaceRemaining(){
		return cargoSpace - (int)(supplies + 2*cargoPeople + 4*soldiers);
	}
	
	public int getSoldiers(){
		return soldiers;
	}
	
	public void setSoldiers(int soldiers){
		this.soldiers = soldiers;
	}
	
	public int getCargoPeople(){
		return cargoPeople;
	}
	
	public void setCargoPeople(int amount){
		cargoPeople = amount;
	}
	
	public void setSupplies(double amount){
		supplies = amount;
	}
	
	public void useSupplies(double amountUsed){
		supplies -= amountUsed;
	}
	
	public double getSupplies(){
		return supplies;
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
		System.out.println("health: " + health);
		System.out.println("damage: " + damage);
		health -= damage;
		System.out.println("remaining: " + health);
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

	public void repair() {
		health = maxHealth;
		
	}
	
}
