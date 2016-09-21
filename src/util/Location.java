package util;
import java.util.ArrayList;
import java.util.Arrays;

import Ships.Ship;
import Starsystem.Star;

public class Location {
	//stores the data for a given map coordinate
	//can have system, anomaly, and/or ship
	//TODO: add anomalies
	private Star star;
	private ArrayList<Ship> ships;
	private int x;
	private int y;
	
	public Location(){
		//empty
	}
	
	public Location(Star star){
		this.ships = new ArrayList<Ship>();
		this.star = star;
		this.x = star.getCoordinates()[0];
		this.y = star.getCoordinates()[1];
	}
	
	public Location(Ship... ships){
		this.ships = new ArrayList<Ship>(Arrays.asList(ships));
		this.x = ships[0].getCoordinates()[0];
		this.y = ships[0].getCoordinates()[1];
	}
	
	public Location(Star star, Ship...ships){
		this.star = star;
		this.ships = (ArrayList<Ship>)Arrays.asList(ships);
		this.x = star.getCoordinates()[0];
		this.y = star.getCoordinates()[1];
	}
	
	public void addShip(Ship ship){
		ships.add(ship);
	}
	
	public void removeShip(Ship ship){
		ships.remove(ship);
	}
	
	public Ship[] getShips(){
		if(ships.size() > 0){
			System.out.println();
			return ships.toArray(new Ship[0]);
		} else {
			return null;
		}
	}
	
	public Star getStar(){
		return star;
	}
	
	public int[] getCoordinates(){
		int[] temp = {x, y};
		return temp;
	}

	public boolean hasAnything() {
		boolean temp = false;
		temp |= star != null;
		temp |= ships.size() > 0;
		return temp;
	}
}
