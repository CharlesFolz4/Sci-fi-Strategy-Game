package util;

import java.util.ArrayList;

import Ships.Ship;
import Starsystem.Star;

public class Faction{
	String name;
	private boolean usesJump;
	private ArrayList<Star> stars;
	private ArrayList<Ship> ships;
	
	private double taxRate;
	private double treasury;
	
	private double[] shipyardTechMods;
	
	public Faction(String name, boolean usesJump){
		this.name = name;
		this.usesJump = usesJump;
		stars = new ArrayList<Star>();
		ships = new ArrayList<Ship>();
		taxRate = 0.25;
		treasury = 1000;
		shipyardTechMods = new double[10];
		shipyardTechMods[0] = 2.1;
		shipyardTechMods[1] = 1.1;
		shipyardTechMods[2] = 1.1;
		shipyardTechMods[3] = 10;
		shipyardTechMods[4] = 1;
		shipyardTechMods[5] = 1;
		shipyardTechMods[6] = 1;
		shipyardTechMods[7] = 1;
		shipyardTechMods[8] = 1;
		shipyardTechMods[9] = 1;
	}
	
	public void setTaxRate(double taxRate){
		this.taxRate = taxRate;
	}
	
	
	public double getIncome() {
		double income = 0;
		for(Star star:stars){
			income += star.getPopulation() * star.getProsperity() * taxRate;
		}
		for(Ship ship:ships){
			income -= ship.getUpkeep();
		}
		//TODO add build costs, trade income etc.
		return income;
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
	
	public void setShipyardTechMod(int index, double newValue){
		shipyardTechMods[index] = newValue;
	}
	
	public double[] getShipyardTechMods(){
		return shipyardTechMods;
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

	public void addShip(Ship ship) {
		ships.add(ship);
	}

	public double getTreasury() {
		return treasury;
	}
	
	public void addToTreasury(double amount){
		treasury += amount;
	}

	public String getName() {
		return name;
	}

	public double getTaxRate() {
		return taxRate;
	}
	
	
}
