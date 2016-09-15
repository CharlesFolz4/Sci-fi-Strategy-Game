package Ships;

import util.Faction;

public class WarpShip extends Ship{
	private double speed;
	private double range;
	
	

	public WarpShip(int x, int y, Faction faction, String name, double speed) {
		super(x, y, faction, name);
		this.speed = speed;
	}
	
	public double getSpeed(){
		return speed;
	}
}
