package util;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
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
					if(!ship.getCoordinates().equals(ship.getDestination()) && ((WarpShip)ship).getMovementRemaining() > 0){
						moveJumpShip((JumpShip)ship);
					}
					((JumpShip)ship).setHasDecrementedThisTurn(false);
				}
			}
		}
		
		//TODO: handle building queues and shipyards and stuff like that, 
		//and build the infrastructure to support that
		
		//TODO add income, population growth, resource collection, etc.
		
		
		currentFactionIndex = currentFactionIndex==(factions.length-1)? 0:++currentFactionIndex;
	}
	
	public Faction getCurrentFaction(){
		return factions[currentFactionIndex];
	}
	
	public void moveJumpShip(JumpShip ship){
		
		if(ship.getTurnsToJump() > 0) {
			//Decrement is outside the if/else block
		} else if ( ship.getTurnsToJump() == 0){
			//TODO actually make the jump
			if(map.getMap()[ship.getDestination()[0]][ship.getDestination()[1]] == null){
				map.getMap()[ship.getDestination()[0]][ship.getDestination()[1]] = new Location(ship);
				System.out.println("new location");
			} else {
				map.getMap()[ship.getDestination()[0]][ship.getDestination()[1]].addShip(ship);
				System.out.println("Added to old location!");
			}
			map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].removeShip(ship);
			if(!map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].hasAnything()){
				map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]] = null;
				System.out.println("Deleted!");
			}
			ship.setCoordinates(ship.getDestination()[0], ship.getDestination()[1]);
			

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateDisplay();
				}
			});
			
		} else { //if negative number, calculate new jump
			int deltaX = ship.getDestination()[0] - ship.getCoordinates()[0];
			int deltaY = ship.getDestination()[1] - ship.getCoordinates()[1];
			double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
			int jumpTime = (int)(distance * ship.getBaseCalcTime());
			ship.setTurnsToJump(jumpTime);
		}
		if(!ship.hasDecrementedThisTurn()){
			ship.decrementTurnsToJump();
		}
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
				System.out.println("new location");
			} else {
				map.getMap()[tempX][tempY].addShip(ship);
				System.out.println("Added to old location!");
			}
			map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].removeShip(ship);
			if(!map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].hasAnything()){
				map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]] = null;
				System.out.println("Deleted!");
			}
			ship.setCoordinates(tempX, tempY);
			ship.decrementMovementRemaining();
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateDisplay();
				}
			});
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			};
			
			deltaX = ship.getDestination()[0] - ship.getCoordinates()[0];
			deltaY = ship.getDestination()[1] - ship.getCoordinates()[1];
			distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
			angle = Math.toDegrees(Math.atan2( deltaY , deltaX));
		}//end while loop
		
		
	}
}
