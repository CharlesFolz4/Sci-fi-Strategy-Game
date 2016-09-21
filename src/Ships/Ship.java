package Ships;

import util.Faction;
import util.Selectable;

public abstract class Ship extends Selectable{
	private Faction faction;
	private String name;
	private String captain;//purely for flavor for now, may add actual officers later
	
	private int[] destination;
	
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
		
		destination = new int[2];
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
	
}
