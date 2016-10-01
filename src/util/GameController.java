package util;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
import Starsystem.Star;
import javafx.application.Platform;
import util.gui.GameGUI;

public class GameController {
	private Map map;
	private Faction[] factions;
	private int currentFactionIndex;
	private GameGUI GUI;
	
	public GameController(Map map, GameGUI GUI, Faction... factions){
		this.map = map;
		this.GUI = GUI;
		this.factions = factions;
		currentFactionIndex = 0;
	}
	
	public boolean attackShip(Ship attacker, Ship defender){
		int xDistance = Math.abs(attacker.getCoordinates()[0] - defender.getCoordinates()[0]);
		int yDistance = Math.abs(attacker.getCoordinates()[1] - defender.getCoordinates()[1]);
		if(xDistance <= 1 && yDistance <=1){
			boolean attacking = true;
			int damage ;
			//TODO: Firing will consume supplies
			while(attacker.getCurrentHealth() > 0 && defender.getCurrentHealth() > 0){
				damage = 0;
				if(attacking){
					damage =  attacker.getWeapons()[0] - defender.getDefenses()[0] ;
					damage += attacker.getWeapons()[1] - defender.getDefenses()[1];
					damage += attacker.getWeapons()[2] - defender.getDefenses()[2];
					
					defender.takeDamage(damage);
				}else{
					damage = defender.getWeapons()[0] - attacker.getDefenses()[0];
					damage = defender.getWeapons()[1] - attacker.getDefenses()[1];
					damage = defender.getWeapons()[2] - attacker.getDefenses()[2];
					
					attacker.takeDamage(damage);
				}
				attacking = !attacking;
			}
		}
		return false;
	}
	
	public void endFactionTurn(){
		//move all unmoved ships of the faction that still need to move, reset stuff for next turn
		if(factions[currentFactionIndex].getShips() != null){
			for(Ship ship : factions[currentFactionIndex].getShips()){
				if(ship instanceof WarpShip){
					if(!ship.getCoordinates().equals(ship.getDestination()) && ((WarpShip)ship).getMovementRemaining() > 0){
						moveWarpShip((WarpShip)ship);
					}
					((WarpShip)ship).setMovementRemaining(((WarpShip) ship).getSpeed());
				} else { //ship is jump ship
					if(!ship.getCoordinates().equals(ship.getDestination()) && !((JumpShip)ship).hasDecrementedThisTurn()){
						moveJumpShip((JumpShip)ship);
					}
					((JumpShip)ship).setHasDecrementedThisTurn(false);
				}
			}
		}
		
		//TODO: handle building queues and shipyards and stuff like that, 
		//and build the infrastructure to support that
		
		//TODO add population growth
		factions[currentFactionIndex].addToTreasury(factions[currentFactionIndex].getIncome());
		for(Star star : factions[currentFactionIndex].getSystems()){
			//make pop grow by 2.2% ?
			star.growPopulation(0.022);
		}
		
		
		currentFactionIndex = currentFactionIndex==(factions.length-2)? 0:++currentFactionIndex;
		//TODO: This is where the faction AI will control their empires from
	}
	
	public void moveJumpShip(JumpShip ship){
		if(!ship.hasDecrementedThisTurn()){
			if(ship.getTurnsToJump() > 1) {
				System.out.println("Doesn't jump this turn "  + ship.getTurnsToJump());
				
			} else  { //if negative number, calculate new jump
				int deltaX = ship.getDestination()[0] - ship.getCoordinates()[0];
				int deltaY = ship.getDestination()[1] - ship.getCoordinates()[1];
				double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
				int jumpTime = (int)(distance * ship.getCalcTimeMod());
				ship.setTurnsToJump(jumpTime);
				System.out.println("Negative number, calculate new jump distance: " + ship.getTurnsToJump());
			}
			ship.decrementTurnsToJump();
			if(ship.getTurnsToJump() == 0){
				System.out.println("makes the jump "  + ship.getTurnsToJump());
				if(map.getMap()[ship.getDestination()[0]][ship.getDestination()[1]] == null){
					map.getMap()[ship.getDestination()[0]][ship.getDestination()[1]] = new Location(ship);
				} else {
					map.getMap()[ship.getDestination()[0]][ship.getDestination()[1]].addShip(ship);
				}
				map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].removeShip(ship);
				if(!map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].hasAnything()){
					map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]] = null;
				}
				ship.setCoordinates(ship.getDestination()[0], ship.getDestination()[1]);
				
				ship.decrementTurnsToJump();
			}
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateDisplay();
					if(GUI.getSelected() instanceof JumpShip){
						GUI.getSideInfoLabels()[1].setText("Turns to jump: \t" + ((JumpShip)ship).getTurnsToJump());
					}
				}
			});
		}
	}
	
	public Faction getCurrentFaction(){
		return factions[currentFactionIndex];
	}

	public void moveWarpShip(WarpShip ship) {
		int deltaX = ship.getDestination()[0] - ship.getCoordinates()[0];
		int deltaY = ship.getDestination()[1] - ship.getCoordinates()[1];
		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		double angle = Math.toDegrees(Math.atan2( deltaY , deltaX));
		
		while(distance > 0 && ship.getMovementRemaining() != 0){
			int tempX;
			int tempY;
			switch ( 45 * (int)Math.round(angle/45)){
				case    0:  tempX = ship.getCoordinates()[0]+1;
							tempY = ship.getCoordinates()[1]+0;
							break;
				case  -45:  tempX = ship.getCoordinates()[0]+1;
							tempY = ship.getCoordinates()[1]-1;
							break;
				case  -90:  tempX = ship.getCoordinates()[0]+0;
							tempY = ship.getCoordinates()[1]-1;
							break;
				case -135:  tempX = ship.getCoordinates()[0]-1;
							tempY = ship.getCoordinates()[1]-1;
							break;
				case  180:  tempX = ship.getCoordinates()[0]-1;
							tempY = ship.getCoordinates()[1]+0;
							break;
				case  135:  tempX = ship.getCoordinates()[0]-1;
							tempY = ship.getCoordinates()[1]+1;
							break;
				case   90:  tempX = ship.getCoordinates()[0]+0;
							tempY = ship.getCoordinates()[1]+1;
							break;
				case   45:  tempX = ship.getCoordinates()[0]+1;
							tempY = ship.getCoordinates()[1]+1;
							break;
				default:    System.out.println("ERROR");
							tempX = -1;
							tempY = -1;
			}
			if(map.getMap()[tempX][tempY] == null){
				map.getMap()[tempX][tempY] = new Location(ship);
//				System.out.println("new location");
			} else {
				map.getMap()[tempX][tempY].addShip(ship);
//				System.out.println("Added to old location!");
			}
			map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].removeShip(ship);
			if(!map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].hasAnything()){
				map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]] = null;
//				System.out.println("Deleted!");
			}
			ship.setCoordinates(tempX, tempY);
			ship.decrementMovementRemaining();
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateDisplay();
					GUI.getSideInfoLabels()[1].setText("Movement: \t\t" + ((WarpShip)ship).getMovementRemaining() +"/" + ((WarpShip)ship).getSpeed());
				}
			});
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
			};
			
			deltaX = ship.getDestination()[0] - ship.getCoordinates()[0];
			deltaY = ship.getDestination()[1] - ship.getCoordinates()[1];
			distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
			angle = Math.toDegrees(Math.atan2( deltaY , deltaX));
		}//end while loop
		
		
	}
}
