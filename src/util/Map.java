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
		
		Faction jump = new Faction();
		factions[0] = jump;
		Planet homeWorldJ = new Planet("Alpha Prime");
		Star homeStarJ = new Star(1, 1, jump, "Alpha", homeWorldJ);
		JumpShip flagshipJ = new JumpShip(1, 2, jump, "Alpha Flagship", .25);
		map[1][1] = new Location(homeStarJ);
		map[1][2] = new Location(flagshipJ);
		
		Faction warp = new Faction();
		factions[1] = warp;
		Planet homeWorldW = new Planet("Beta Prime");
		Star homeStarW = new Star(14, 13, warp, "Beta", homeWorldW);
		WarpShip flagshipW = new WarpShip(14, 12, warp, "Beta Flagship", 4);
		map[14][13] = new Location(homeStarW);
		map[14][12] = new Location(flagshipW);
		
		Faction uninhabited = new Faction();
		factions[2] = uninhabited;
		Planet deltaP = new Planet("Delta Prime");
		Star deltaS = new Star(3, 12, uninhabited, "Delta", deltaP);
		map[3][12] = new Location(deltaS);
		
		Planet gammaP = new Planet("Gamma Prime");
		Star gammaS = new Star(12, 3, uninhabited, "Gamma", gammaP);
		map[12][3] = new Location(gammaS);
		
		//Test map
//		Faction test = new Faction();
//		JumpShip jumpShip = new JumpShip(1, 3, test, "Jump Ship", .25);
//		WarpShip warpShip = new WarpShip(1, 1, test, "Warp Ship", 4);
//		map[1][3] = new Location(jumpShip);
//		map[1][1] = new Location(warpShip);
//		Planet planet = new Planet("Planet");
//		Star star = new Star(3, 3, "Test Star","Blue Star", planet);
//		map[3][3] = new Location(star);
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
