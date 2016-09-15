package Starsystem;

public class Planet {
	private String name;
	private String category;
	private int orbitRadius;
	
	private int population;
	private int popCap;
	private int terraformDifficulty;
	
	public Planet(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public int getPopulation() {
		return population;
	}
}
