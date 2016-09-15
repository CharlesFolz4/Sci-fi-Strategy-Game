package Ships;

import util.Faction;
import util.Selectable;

public abstract class Ship extends Selectable {
	private Faction faction;
	private String name;
	private String captain;
	
	private int supplies;
	private int sensorRange;
	private int sensorStrength;
	
	private int missiles;
	private int lasers;
	private int projectile;
	
	private int pointDefense;
	private int shields;
	private int armor;
	
	public Ship(int x, int y, Faction faction, String name){
		super(x, y);
		this.faction = faction;
		this.name = name;
	}
	
	public Faction getFaction(){
		return faction;
	}
	
	public String getName(){
		return name;
	}
	
	
	
	
	
}
