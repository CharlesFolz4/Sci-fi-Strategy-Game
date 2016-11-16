package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
import Starsystem.Planet;
import Starsystem.Star;

public class Map {
	private Location map[][];
	private ArrayList<Faction> factions;
	
	
	//TODO: consider alternative random generators- true random, jittered grid, poisson disc sampling, poisson disc with minimum distance determined by perlin noise
	//SEE: http://devmag.org.za/2009/05/03/poisson-disk-sampling/
	public Map(int x, int y, double[] galacticProbabilities, String[] factionNames, boolean[] factionUsesJump, String factionColors[]){
		map = new Location[x][y];
		
		//make stars and planets
		ArrayList<String> starNames = generateStarNames();
		ArrayList<Planet> tempPlanets;
		Star tempStar;
		double[][] randomWeights = generateSmoothedArray(x, y);
		for(int i = 0; i < x; ++i){
			for(int j = 0; j < y; ++j){
				if(Math.random() <= randomWeights[i][j] * galacticProbabilities[0]){
					tempPlanets = new ArrayList<Planet>();

					int index = (int)(Math.random() * starNames.size());
					
					Random rand = new Random();
					int planetCount = (int) (rand.nextGaussian()*3+8); //Standard deviation 3, median 8
					for(int p = 0; p < planetCount; ++p){
						if(Math.random() < galacticProbabilities[2]){
							int popCap = (int)(Math.random() * 1500);
							popCap *= 1000;
							popCap += 100000;
							tempPlanets.add(new Planet(starNames.get(index) + " " + integerToRomanNumerals(p), popCap)); //add randomization element to the population cap
						} else {
							tempPlanets.add(new Planet(starNames.get(index) + " " + integerToRomanNumerals(p), 0));
						}
					}
					
					tempStar = new Star(i, j, starNames.get(index), tempPlanets.toArray(new Planet[0]));
					map[i][j] = new Location(tempStar);
					starNames.remove(index);
				}
			}
		}
		
		//add factions and their home worlds 
		factions = new ArrayList<Faction>();
		int factionX;
		int factionY;
		Planet tempPlanet;
		Faction tempFaction;
		Ship tempShip;
		
		for(int i = 0; i < factionNames.length; ++i){
			tempFaction = new Faction(factionNames[i], factionUsesJump[i], factionColors[i]);
			factions.add(tempFaction);
			factionX = (int) (Math.random() * (x-2)+1);
			factionY = (int) (Math.random() * (y-2)+1);
			tempPlanet = new Planet(factionNames[i] + " Prime", 500, 1_000_000);
			tempStar = new Star(factionX, factionY, tempFaction, factionNames[i] + " Capital", tempPlanet);
			map[factionX][factionY] = new Location(tempStar);
			tempFaction.setCapital(map[factionX][factionY]);
			if(tempFaction.usesJump()){
				tempShip = new JumpShip(factionX, factionY+1, 1, tempFaction, factionNames[i] + " Flagship", 3, 10);
			} else {
				tempShip = new WarpShip(factionX, factionY+1, 1, tempFaction, factionNames[i] + " Flagship", 3, 10);
			}
			map[factionX][factionY+1] = new Location(tempShip);
		}
		
		
		//This is here because of reasons involving turn changing
		Faction uninhabited = new Faction("", false, "#000000");
		factions.add(uninhabited);
	}
	
	//TODO: Change to Perlin Noise eventually
	private double[][] generateSmoothedArray(int xx, int yy){
		double[][] raw = new double[xx][yy];
		for(int x = 0; x < xx; ++x){
			for(int y = 0; y < yy; ++y){
				raw[x][y] = Math.random();
			}
		}
		
		int[] xOffset = {-1,0,1};
		int[] yOffset = {-1,0,1};
		double[][] smoothed = new double[xx][yy];
		for(int x = 0; x < xx; ++x){
			for(int y = 0; y < yy; ++y){
				double temp = 0;
				for(int oX : xOffset){
					for( int oY : yOffset){
						if(x+oX > 0 && y+oY >0 && x+oX < xx && y+oY < yy){
							temp += raw[x+oX][y+oY];
						}
					}
				}
				smoothed[x][y] = temp/(xOffset.length * yOffset.length); //average of surrounding squares
			}
		}
		
		return smoothed;
	}
	
	//This method doesn't really belong in this class
	//is only intended to deal with numbers less than 20
	private String integerToRomanNumerals(int num){
		String roman = "";
		while(num > 0){
			if(num >= 10){
				roman += "X";
				num   -= 10;
			} else if (num >= 5){
				roman += "V";
				num   -= 5;
			} else if (num >= 1){
				roman += "I";
				num   -= 1;
			}
		}
		return roman;
	}
	
	//TODO: Change to a text file and for loop to read it
	private ArrayList<String> generateStarNames() {
		ArrayList<String> starNames = new ArrayList<String>();
		
		String path = "bin" + File.separator + "util" + File.separator;
		File stars = new File(path + "Stars.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(stars));

			String starName;
			while ((starName = reader.readLine()) != null) {
				if(!starName.equals("")){
					starNames.add(starName);
				}
		    }
			
		} catch (FileNotFoundException e) {
			System.out.println("No file");
//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Other I/O exception");
//			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return starNames;
	}

	//default constructor for testing purposes
	public Map(){
		map = new Location[16][16];
		factions = new ArrayList<Faction>();
		
		//default map
		
		Faction jump = new Faction("Alpha Association", true, "#B71C1C");
		factions.add(jump);
		Planet homeWorldJ = new Planet("Alpha Prime", 500, 1_000_000);
		Star homeStarJ = new Star(1, 1, jump, "Alpha", homeWorldJ);
		//jump.addStar(homeStarJ);
		JumpShip flagshipJ = new JumpShip(1, 2, 1, jump, "Alpha Flagship", 3, 10);
		map[1][1] = new Location(homeStarJ);
		map[1][2] = new Location(flagshipJ);
		
		Faction warp = new Faction("Delta Dominion", false, "#66ff00");
		factions.add(warp);
		Planet homeWorldW = new Planet("Delta Prime", 500, 1_000_000);
		Star homeStarW = new Star(14, 13, warp, "Delta", homeWorldW);
		//warp.addStar(homeStarW);
		WarpShip flagshipW = new WarpShip(14, 12, 1, warp, "Delta Flagship", 3, 10);
		map[14][13] = new Location(homeStarW);
		map[14][12] = new Location(flagshipW);
		
		Faction uninhabited = new Faction("", false, "	#000000");
		factions.add(uninhabited);
		Planet betaP = new Planet("Beta Prime", 600_000);
		Star betaS = new Star(3, 12,  "Beta", betaP);
		map[3][12] = new Location(betaS);
		
		Planet gammaP = new Planet("Gamma Prime", 600_000);
		Star gammaS = new Star(12, 3,  "Gamma", gammaP);
		map[12][3] = new Location(gammaS);
	}
	
	
	public double distanceBetween(Location a, Location b){
		int deltaX = a.getCoordinates()[0] - b.getCoordinates()[0];
		int deltaY = a.getCoordinates()[1] - b.getCoordinates()[1];
		return Math.sqrt( deltaX*deltaX + deltaY*deltaY );
	}
	
	public int[] getDimensions(){
		int x = map.length;
		int y = map[0].length;
		int[] dimensions = {x,y};
		return dimensions;
	}
	
	public Location[][] getMap(){
		return map;
	}
	
	public ArrayList<Faction> getFactions(){
		return factions;
	}
	
	public void addFaction(Faction faction){
		factions.add(faction);
	}
	
	public void removeFaction(Faction faction){
		factions.remove(faction);
	}
}
