package util;

import Ships.Ship;
import javafx.application.Platform;
import util.gui.GameGUI;

public class GameController {
	private Map map;
	private Faction[] factions;
	private int currentFactionIndex;
	
	public GameController(Map map, Faction... factions){
		this.map = map;
		this.factions = factions;
		currentFactionIndex = 0;
	}
	
	public void endTurn(){
		/*TODO
		 * handle turns!  YAY! :D
		 * 
		 */
		currentFactionIndex = 0;
	}
	
	public Faction getCurrentFaction(){
		return factions[currentFactionIndex];
	}

	public void moveShip(Ship ship, int targetX, int targetY, GameGUI GUI) {
		int deltaX = targetX - ship.getCoordinates()[0];
		int deltaY = targetY - ship.getCoordinates()[1];
		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		double angle = Math.toDegrees(Math.atan2( deltaY , deltaX));
		
		while(distance > 0){
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
			} else {
				map.getMap()[tempX][tempY].addShip(ship);
			}
			map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].removeShip(ship);
			if(!map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].hasAnything()){
				map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]] = null;
			}
			ship.setCoordinates(tempX, tempY);
			
			
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
			
			deltaX = targetX - ship.getCoordinates()[0];
			deltaY = targetY - ship.getCoordinates()[1];
			distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
			angle = Math.toDegrees(Math.atan2( deltaY , deltaX));
		}
		
		
	}
}
