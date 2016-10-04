package Ships;


import util.Faction;

public class JumpShip extends Ship{
	private boolean hasJumpedThisTurn;
	private int jumpRadius;
	
	public JumpShip(int x, int y, int upkeep, Faction faction, String name, int jumpRadius, int health) {
		super(x, y, upkeep, faction, name, health);
		this.jumpRadius = jumpRadius;
		//this.range = range;
		this.hasJumpedThisTurn = false;
	}
	
	public JumpShip(int[] coordinates, Faction faction, String name, int upkeep, int health, int cargoSpace, int jumpRadius, int[] weapons, int[] defenses){
		super(coordinates, faction, name, upkeep, health, cargoSpace, weapons, defenses);
		this.jumpRadius = jumpRadius;
		this.hasJumpedThisTurn = false;
	}
	
	public void setHasJumpedThisTurn(boolean hasJumped){
		this.hasJumpedThisTurn = hasJumped;
	}
	
	public boolean hasJumpedThisTurn(){
		return hasJumpedThisTurn;
	}

	public int getJumpRadius() {
		return jumpRadius;
	}
	
}
