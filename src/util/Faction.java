package util;

import Ships.Ship;
import Starsystem.Star;

public class Faction {
	private boolean usesJump;
	private Star[] stars;
	private Ship[] ships;
	
	
	public String getIncome() {
		// TODO Auto-generated method stub
		return null;
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
		return stars.length;
	}
	
}
