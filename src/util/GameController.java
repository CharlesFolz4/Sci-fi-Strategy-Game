package util;

import java.util.Arrays;

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
	
	public void attackShip(Ship attacker, Ship defender){
		if(attacker.getWeapons() != null){
			System.out.println("Fight some nerds!");
			int xDistance = Math.abs(attacker.getCoordinates()[0] - defender.getCoordinates()[0]);
			int yDistance = Math.abs(attacker.getCoordinates()[1] - defender.getCoordinates()[1]);
			if(xDistance <= 1 && yDistance <=1){
				System.out.println("In distance");
				boolean attacking = true;
				int damage ;
				//TODO: Firing will consume supplies
				while(attacker.getCurrentHealth() > 0 && defender.getCurrentHealth() > 0){
					damage = 0;
					if(attacking){
						damage =  attacker.getWeapons()[0] - defender.getDefenses()[0] ;
						damage += attacker.getWeapons()[1] - defender.getDefenses()[1];
						damage += attacker.getWeapons()[2] - defender.getDefenses()[2];
						
						damage = Math.abs(damage);
						defender.takeDamage(damage);
					}else{
						damage = defender.getWeapons()[0] - attacker.getDefenses()[0];
						damage = defender.getWeapons()[1] - attacker.getDefenses()[1];
						damage = defender.getWeapons()[2] - attacker.getDefenses()[2];

						damage = Math.abs(damage);
					}
					System.out.println("FIGHTING- attacker: " + attacker.getCurrentHealth() + " Defender: " + defender.getCurrentHealth());
					attacking = !attacking;
				}//end while
				//TODO add option (or small percentage chance) to disable and capture enemy vessel
				//TODO: Ship isn't being destroyed well enough.  Need to eliminate all traces of it ever existing, just to be sure.
				Ship destroyed = null;
				if(attacker.getCurrentHealth() <= 0){
					destroyed = attacker;
				} else if (defender.getCurrentHealth() <= 0){
					destroyed = defender;
				}
				System.out.println(destroyed.getName() + " was destroyed");
				map.getMap()[destroyed.getCoordinates()[0]][destroyed.getCoordinates()[1]].removeShip(destroyed);
				if(!map.getMap()[destroyed.getCoordinates()[0]][destroyed.getCoordinates()[1]].hasAnything()){
					map.getMap()[destroyed.getCoordinates()[0]][destroyed.getCoordinates()[1]] = null;
				}
				destroyed.getFaction().getShips().remove(destroyed);
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						GUI.updateDisplay();
					}
				});
			}
		}
	}
	
	public void endFactionTurn(){
		//move all unmoved ships of the faction that still need to move, reset stuff for next turn
		
		
		if(factions[currentFactionIndex].getShips() != null){
			for(Ship ship : factions[currentFactionIndex].getShips()){
				if(!Arrays.equals(ship.getCoordinates(), ship.getDestination())){
					if(ship instanceof WarpShip){
						if(((WarpShip)ship).getMovementRemaining() > 0){
							//prevent GUI from being stupid
							Thread pathFinderThread = new Thread( () -> {
								moveWarpShip((WarpShip)ship);
								return;
							});
							pathFinderThread.start();
						}
						((WarpShip)ship).setMovementRemaining(((WarpShip) ship).getSpeed());
					} else { //ship is jump ship
						if(!((JumpShip)ship).hasJumpedThisTurn()){
							moveJumpShip((JumpShip)ship);
						}
						((JumpShip)ship).setHasJumpedThisTurn(false);
					}
				}
			}
		}
		
		//TODO: handle building queues and shipyards and stuff like that, 
		//and build the infrastructure to support that
		
		
		factions[currentFactionIndex].addToTreasury(factions[currentFactionIndex].getIncome());
		for(Star star : factions[currentFactionIndex].getSystems()){
			//final pop growth rate undecided
			star.growPopulation(0.022);
		}
		
		
		currentFactionIndex = currentFactionIndex==(factions.length-2)? 0:++currentFactionIndex;
		//TODO: This is where the faction AI will control their empires from
	}
	
	
	//TODO overhaul movement so as to be more intuitive- series of smaller jumps of max range
	public void moveJumpShip(JumpShip ship){
		if(!ship.hasJumpedThisTurn()){
			int deltaX = ship.getDestination()[0] - ship.getCoordinates()[0];
			int deltaY = ship.getDestination()[1] - ship.getCoordinates()[1];
			double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
			
			if(distance <= ship.getJumpRadius()){
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
				
			} else { //destination outside of jump radius, jump as far as range will allow in that direction
				double scale = ship.getJumpRadius()/distance;
				deltaX *= scale;
				deltaY *= scale;
				
				if(map.getMap()[ship.getCoordinates()[0] + deltaX][ship.getCoordinates()[1] + deltaY] == null){
					map.getMap()[ship.getCoordinates()[0] + deltaX][ship.getCoordinates()[1] + deltaY] = new Location(ship);
				} else {
					map.getMap()[ship.getCoordinates()[0] + deltaX][ship.getCoordinates()[1] + deltaY].addShip(ship);
				}

				map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].removeShip(ship);
				if(!map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].hasAnything()){
					map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]] = null;
				}
				
				ship.setCoordinates(ship.getCoordinates()[0] + deltaX, ship.getCoordinates()[1] + deltaY);
			}
			ship.setHasJumpedThisTurn(true);
			
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateDisplay();
				}
			});
		}
	}
	
	public Faction getCurrentFaction(){
		return factions[currentFactionIndex];
	}
	
	public void buildShip(Ship ship){
		map.getMap()[ship.getCoordinates()[0]][ship.getCoordinates()[1]].addShip(ship);
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
				case -180:
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
				default:    System.out.println("ERROR: " + (45 * (int)Math.round(angle/45)));
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
			
			//doesn't work properly on end turns with more than one ship
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateDisplay();
					if(GUI.getSelected() instanceof WarpShip){
						GUI.getSideInfoLabels()[1].setText("Movement: \t\t" + ((WarpShip)ship).getMovementRemaining() +"/" + ((WarpShip)ship).getSpeed());
					}
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
		return;
		
	}
}
