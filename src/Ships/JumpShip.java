package Ships;


import util.Faction;

public class JumpShip extends Ship{
	
	private int turnsToJump; 
	private boolean hasDecrementedThisTurn;
	private double calcTimeMod;
	//private int range;
	
	public JumpShip(int x, int y, int upkeep, Faction faction, String name, double calcTimeMod, int health) {
		super(x, y, upkeep, faction, name, health);
		this.calcTimeMod = calcTimeMod;
		//this.range = range;
		this.hasDecrementedThisTurn = false;
		turnsToJump = -1;
	}
	
	public JumpShip(int[] coordinates, Faction faction, String name, int upkeep, int health, int cargoSpace, double calcTimeMod, int[] weapons, int[] defenses){
		super(coordinates, faction, name, upkeep, health, cargoSpace, weapons, defenses);
		this.calcTimeMod = calcTimeMod;
		this.hasDecrementedThisTurn = false;
		turnsToJump = -1;
	}
	
	public void setHasDecrementedThisTurn(boolean hasDecrementedThisTurn){
		this.hasDecrementedThisTurn = hasDecrementedThisTurn;
	}
	
	public boolean hasDecrementedThisTurn(){
		return hasDecrementedThisTurn;
	}
	
	public double getCalcTimeMod(){
		return calcTimeMod;
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
