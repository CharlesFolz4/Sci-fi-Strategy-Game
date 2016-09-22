package util;

import java.util.ArrayList;

import Ships.Ship;
import Starsystem.Star;

public class Faction{
	private boolean usesJump;
	private ArrayList<Star> stars;
	private ArrayList<Ship> ships;
	
	public Faction(boolean usesJump){
		this.usesJump = usesJump;
		stars = new ArrayList<Star>();
		ships = new ArrayList<Ship>();
	}
	
	
	public int getIncome() {
		// TODO actually implement income and whatnot
		return 0;
	}
	
	public void addStar(Star star){
		stars.add(star);
	}

	public int getPopulation() {
		int popCount = 0;
		for(Star star:stars){
			popCount += star.getPopulation();
		}
		return popCount;
	}

	public int getPlanetCount() {
		int planetCount = 0;
		for(Star star : stars){
			planetCount += star.getPlanets().length;
		}
		return planetCount;
	}
	
	public int getSystemCount(){
		return stars.size();
	}
	
	public ArrayList<Star> getSystems(){
		return stars;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public boolean usesJump(){
		return usesJump;
	}
}
