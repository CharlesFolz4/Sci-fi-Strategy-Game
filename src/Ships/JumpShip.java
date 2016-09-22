package Ships;


import util.Faction;

public class JumpShip extends Ship{
	
	private int turnsToJump;
	private boolean hasDecrementedThisTurn;

	private double baseCalcTime;
	//private int range;
	
	public JumpShip(int x, int y, Faction faction, String name, double baseCalcTime) {
		super(x, y, faction, name);
		this.baseCalcTime = baseCalcTime;
		//this.range = range;
		this.hasDecrementedThisTurn = false;
		turnsToJump = -1;
	}
	
	public void setHasDecrementedThisTurn(boolean hasDecrementedThisTurn){
		this.hasDecrementedThisTurn = hasDecrementedThisTurn;
	}
	
	public boolean hasDecrementedThisTurn(){
		return hasDecrementedThisTurn;
	}
	
	public double getBaseCalcTime(){
		return baseCalcTime;
	}
	
	public int getTurnsToJump(){
		return turnsToJump;
	}
	
	public void setTurnsToJump(int turnsToJump){
		this.turnsToJump = turnsToJump;
	}
	
	public void decrementTurnsToJump(){
		if(!hasDecrementedThisTurn){
			turnsToJump--;
			hasDecrementedThisTurn = true;
		}
	}
	
}
