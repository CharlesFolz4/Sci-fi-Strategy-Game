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
	private double[] shipyardCostMods;
	private double[] shipyardUpkeepMods;
	
	public Faction(String name, boolean usesJump){
		this.name = name;
		this.usesJump = usesJump;
		stars = new ArrayList<Star>();
		ships = new ArrayList<Ship>();
		taxRate = 0.25;
		treasury = 2000;
		
		shipyardTechMods = new double[11];
		shipyardTechMods[0] = 2.1;
		shipyardTechMods[1] = 1.1;
		shipyardTechMods[2] = 1.1;
		shipyardTechMods[3] = 10;
		shipyardTechMods[4] = 1.3334;
		shipyardTechMods[5] = 1;
		shipyardTechMods[6] = 1;
		shipyardTechMods[7] = 1;
		shipyardTechMods[8] = 1;
		shipyardTechMods[9] = 1;
		shipyardTechMods[10] = 1;
		
		shipyardCostMods = new double[11];
		shipyardCostMods[0] = 100;
		shipyardCostMods[1] = 80;
		shipyardCostMods[2] = 30;
		shipyardCostMods[3] = 50;
		shipyardCostMods[4] = 150;
		shipyardCostMods[5] = 125;
		shipyardCostMods[6] = 125;
		shipyardCostMods[7] = 125;
		shipyardCostMods[8] = 125;
		shipyardCostMods[9] = 125;
		shipyardCostMods[10] = 125;
		
		shipyardUpkeepMods = new double[11];
		shipyardUpkeepMods[0] = 0.1;
		shipyardUpkeepMods[1] = 0.2;
		shipyardUpkeepMods[2] = 0.1;
		shipyardUpkeepMods[3] = 0.1;
		shipyardUpkeepMods[4] = 0.3;
		shipyardUpkeepMods[5] = 0.25;
		shipyardUpkeepMods[6] = 0.25;
		shipyardUpkeepMods[7] = 0.25;
		shipyardUpkeepMods[8] = 0.25;
		shipyardUpkeepMods[9] = 0.25;
		shipyardUpkeepMods[10] = 0.25;
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
	
	public void setShipyardCostMod(int index, double newValue){
		shipyardCostMods[index] = newValue;
	}
	
	public void setShipyardUpkeepMod(int index, double newValue){
		shipyardUpkeepMods[index] = newValue;
	}

	public double[] getShipyardTechMods(){
		return shipyardTechMods;
	}
	
	public double[] getShipyardCostMods(){
		return shipyardCostMods;
	}
	
	public double[] getShipyardUpkeepMods(){
		return shipyardUpkeepMods;
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
