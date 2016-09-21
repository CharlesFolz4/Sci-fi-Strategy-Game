package Ships;

import util.Faction;

public class WarpShip extends Ship{
	private int speed;
	//private double range;
	
	private int movementRemaining;
	

	public WarpShip(int x, int y, Faction faction, String name, int speed) {
		super(x, y, faction, name);
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
