package util;

import Ships.JumpShip;
import Ships.WarpShip;
import Starsystem.Planet;
import Starsystem.Star;

public class Map {
	private Location map[][];
	private Faction factions[];
	
	public Map(int x, int y){
		map = new Location[x][y];
	}
	
	public Map(){
		map = new Location[16][16];
		factions = new Faction[3];
		
		//default map
		
		Faction jump = new Faction("Alpha Association", true);
		factions[0] = jump;
		Planet homeWorldJ = new Planet("Alpha Prime", 100, 10_000_000);
		Star homeStarJ = new Star(1, 1, jump, "Alpha", homeWorldJ);
		//jump.addStar(homeStarJ);
		JumpShip flagshipJ = new JumpShip(1, 2, 1, jump, "Alpha Flagship", .5, 10);
		map[1][1] = new Location(homeStarJ);
		map[1][2] = new Location(flagshipJ);
		
		Faction warp = new Faction("Delta Dominion", false);
		factions[1] = warp;
		Planet homeWorldW = new Planet("Delta Prime", 100, 10_000_000);
		Star homeStarW = new Star(14, 13, warp, "Delta", homeWorldW);
		//warp.addStar(homeStarW);
		WarpShip flagshipW = new WarpShip(14, 12, 1, warp, "Delta Flagship", 2, 10);
		map[14][13] = new Location(homeStarW);
		map[14][12] = new Location(flagshipW);
		
		Faction uninhabited = new Faction("", false);
		factions[2] = uninhabited;
		Planet betaP = new Planet("Beta Prime", 6_000_000);
		Star betaS = new Star(3, 12,  "Beta", betaP);
		map[3][12] = new Location(betaS);
		
		Planet gammaP = new Planet("Gamma Prime", 6_000_000);
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
	
	public Faction[] getFactions(){
		return factions;
	}
}
