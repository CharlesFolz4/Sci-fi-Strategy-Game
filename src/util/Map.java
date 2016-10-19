package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		for(int i = 0; i < x; ++i){
			for(int j = 0; j < y; ++j){
				if(Math.random() <= galacticProbabilities[0]){
					tempPlanets = new ArrayList<Planet>();

					int index = (int)(Math.random() * starNames.size());
					
					int counter = 0;
					while(Math.random() < galacticProbabilities[1] && counter < 20){
						if(Math.random() < galacticProbabilities[2]){
							int popCap = (int)(Math.random() * 1500);
							popCap *= 1000;
							popCap += 100000;
							tempPlanets.add(new Planet(starNames.get(index) + " " + integerToRomanNumerals(counter), popCap)); //add randomization element to the population cap
						} else {
							tempPlanets.add(new Planet(starNames.get(index) + " " + integerToRomanNumerals(counter), 0));
						}
						counter++;
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
		
		//generic
		starNames.add("Eridani");
		starNames.add("Cygni");
		starNames.add("Ursae Majoris");
		starNames.add("Ursae Minoris");
		starNames.add("Achernar");
		starNames.add("Algol");
		starNames.add("Antares");
		starNames.add("Hydri");
		starNames.add("Andromedae");
		starNames.add("Gliese");
		starNames.add("Mira");
		starNames.add("Polaris");
		starNames.add("Procyon");
		starNames.add("Enigma");
		starNames.add("Wolf");
		starNames.add("Kruger");
		
		//Smite
		starNames.add("Agni");
		starNames.add("Muzen Cab");
		starNames.add("Ah Puch");
		starNames.add("Anubis");
		starNames.add("Arachne");
		starNames.add("Artemis");
		starNames.add("Ares");
		starNames.add("Bakusura");
		starNames.add("Bellona");
		starNames.add("Cabrakan");
		starNames.add("Chaac");
		starNames.add("Chang'e");
		starNames.add("Chiron");
		starNames.add("Chronos");
		starNames.add("Fenrir");
		starNames.add("Freya");
		starNames.add("Geb");
		starNames.add("Guan Yu");
		starNames.add("Hades");
		starNames.add("Hel");
		starNames.add("Hou Yi");
		starNames.add("Janus");
		starNames.add("Kali");
		starNames.add("Khepri");
		starNames.add("Kukulkan");
		starNames.add("Kumbhakarna");
		starNames.add("Loki");
		starNames.add("Medusa");
		starNames.add("Mercury");
		starNames.add("Neith");
		starNames.add("Nemesis");
		starNames.add("Nox");
		starNames.add("Odin");
		starNames.add("Osiris");
		starNames.add("Ra");
		starNames.add("Ratatoskr");
		starNames.add("Ravana");
		starNames.add("Scylla");
		starNames.add("Serqet");
		starNames.add("Sobek");
		starNames.add("Sol");
		starNames.add("Sun Wukong");
		starNames.add("Sylvanus");
		starNames.add("Thanatos");
		starNames.add("Thor");
		starNames.add("Tyr");
		starNames.add("Ullr");
		starNames.add("Vamana");
		starNames.add("Vulcan");
		starNames.add("Xbalanque");
		starNames.add("Xing Tian");
		starNames.add("Ymir");
		starNames.add("Zeus");
		starNames.add("Zong Kui");
		
		//Stargate
		starNames.add("Ba'al");
		starNames.add("Asgard");
		starNames.add("Abydos");
		starNames.add("Asuras");
		starNames.add("Chulak");
		starNames.add("Dakara");
		starNames.add("Lantea");
		starNames.add("Orilla");
		starNames.add("Tollana");
		starNames.add("Teal'c");
		starNames.add("O'Neil");
		starNames.add("Jackson");
		starNames.add("Carter");
		starNames.add("Hammond");
		starNames.add("Vala");
		starNames.add("Fraiser");
		starNames.add("Chekov");
		starNames.add("Maybourne");
		starNames.add("Woolsey");
		starNames.add("McKay");
		starNames.add("Desala");
		starNames.add("Freyr");
		starNames.add("Kvasir");
		starNames.add("Apophis");
		starNames.add("Hathor");
		starNames.add("Nirrti");
		starNames.add("Sokar");
		starNames.add("Bra'tac");
		starNames.add("Lantash");
		starNames.add("Martouf");
		starNames.add("Selmak");
		starNames.add("Cassandra");
		starNames.add("Chaka");
		starNames.add("Sheppard");
		starNames.add("Weir");
		starNames.add("Ronon");
		starNames.add("Beckett");
		
		//homestuck
		starNames.add("Aradia");
		starNames.add("Nitram");
		starNames.add("Sollux");
		starNames.add("Karkat");
		starNames.add("Vantas");
		starNames.add("Nepeta");
		starNames.add("Kanaya");
		starNames.add("Terezi");
		starNames.add("Pyrope");
		starNames.add("Vriska");
		starNames.add("Serket");
		starNames.add("Equius");
		starNames.add("Gamzee");
		starNames.add("Eridan");
		starNames.add("Feferi");
		starNames.add("Megido");
		starNames.add("Porrim");
		starNames.add("Latula");
		starNames.add("Aranea");
		starNames.add("Meenah");
		starNames.add("Peixes");
		starNames.add("Marquise");
		starNames.add("Spinneret");
		starNames.add("Mindfang");
		starNames.add("Signless");
		starNames.add("Dolorosa");
		starNames.add("Neophyte");
		starNames.add("Redglare");
		starNames.add("Darkleer");
		starNames.add("Dualscar");
		starNames.add("Condesce");
		starNames.add("Egbert");
		starNames.add("Lalonde");
		starNames.add("Strider");
		starNames.add("Harley");
		starNames.add("Roxy");
		starNames.add("Dirk");
		
		//names
		starNames.add("Asimov");
		starNames.add("Rothfuss");
		starNames.add("Wells");
		starNames.add("Heinlein");
		starNames.add("Poe");
		starNames.add("Boethius");
		starNames.add("Tesla");
		starNames.add("Rommel");
		starNames.add("Sun Tzu");
		starNames.add("Tesla");
		starNames.add("Zim");
		starNames.add("Derham");
		starNames.add("Folz");
		starNames.add("Volch");
		starNames.add("Narf");
		starNames.add("Hallahan");
		starNames.add("Hansen");
		starNames.add("Bruno");
		starNames.add("Higham");
		starNames.add("Vigiano");
		
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
