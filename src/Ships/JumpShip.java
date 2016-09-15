package Ships;

import util.Faction;

public class JumpShip extends Ship{

	private double baseCalcTime;
	private int range;
	
	public JumpShip(int x, int y, Faction faction, String name, double baseCalcTime) {
		super(x, y, faction, name);
		this.baseCalcTime = baseCalcTime;
		//this.range = range;
	}
	
	public double getBaseCalcTime(){
		return baseCalcTime;
	}
	
	
}
