package Starsystem;

public class Planet {
	private String name;
	//private String category;
	//private int orbitRadius;
	
	private double population;
	private double popCap;
	//private int terraformDifficulty;
	
	public Planet(String name, double popCap){
		this.name       = name;
		this.population = 0;
		this.popCap     = popCap;
	}
	
	public Planet(String name, double population, double popCap){
		this.name       = name;
		this.population = population;
		this.popCap     = popCap;
	}
	
	public String getName(){
		return name;
	}
	
	public void growPopulation(double rate){
		population += population * rate;
		if(population > popCap){
			population = popCap;
		}
	}
	
	public void setPopulation(double pop){
		population = pop;
		if(population < 0){
			population = 0; //can't have population less than 0
		}
	}

	public double getPopulation() {
		return population;
	}
	
	public double getPopCap(){
		return popCap;
	}
}
