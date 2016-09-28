package Ships;

import util.Faction;

public class WarpShip extends Ship{
	private int speed;
	private int movementRemaining;

	//default constructor
	public WarpShip(int x, int y, int upkeep, Faction faction, String name, int speed, int health) {
		super(x, y, upkeep, faction, name, health);
		this.speed = speed;
		movementRemaining = speed;
	}
	
	//advanced constructor
	public WarpShip(int[] coordinates, Faction faction, String name, int upkeep, int health, int cargoSpace, int speed, int[] weapons, int[] defenses){
		super(coordinates, faction, name, upkeep, health, cargoSpace, weapons, defenses);
		this.speed = speed;
		movementRemaining = speed;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public int getMovementRemaining(){
		return movementRemaining;
	}
	
	public void decrementMovementRemaining(){
		movementRemaining--;
	}
	
	public void setMovementRemaining(int movementRemaining){
		this.movementRemaining = movementRemaining;
	}
}
