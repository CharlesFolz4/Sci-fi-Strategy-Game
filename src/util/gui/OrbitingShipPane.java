package util.gui;

import Ships.Ship;
import Starsystem.Star;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import util.gui.images.ImageCache;

//TODO this is used for setting ships as 'permanent' system defenders
//TODO: if there are no ships to select, make a new temp ship 'no ship selected' which cannot be interacted with?
public class OrbitingShipPane extends BorderPane {
	GameGUI gameGUI;
	ImageCache imageCache;
	Star star;

	Ship selected;
	
	public OrbitingShipPane(Star star,GameGUI gameGUI, ImageCache imageCache, Ship... ships){
		this.star       = star;
		this.gameGUI    = gameGUI;
		this.imageCache = imageCache;
		this.imageCache.loadOrbitalShipsImages();
		
		this.setStyle("-fx-border-width: 4; -fx-border-color:" + star.getFaction().getColor());
		
		HBox topPane = new HBox();
		Label shipyardLabel = new Label("Orbiting Ships");
		shipyardLabel.setStyle("-fx-font-size: 45;");
		shipyardLabel.setTextFill(Color.WHITE);
		topPane.getChildren().add(shipyardLabel);
		topPane.setAlignment(Pos.CENTER);
		topPane.setPadding(new Insets(40));
		topPane.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + star.getFaction().getColor());
		this.setTop(topPane);
		
//		this.setBottom(makeBottom());
		
		this.setCenter(makeShipGrid(ships));
	}


	private Node makeBottom(Ship ship) {
		GridPane root = new GridPane();
		root.setStyle("-fx-background-color: rgba(16, 16, 16, .75); -fx-border-width: 4 0 0 0; -fx-border-color: " + star.getFaction().getColor());
		root.setPadding(new Insets(50));
		
		Pane temp;
		
		Label nameLabel = new Label("Name: ");
		nameLabel.setStyle("-fx-font-size: 18;");
		nameLabel.setTextFill(Color.WHITE);
		temp = new Pane(nameLabel);
		root.add(temp, 0, 0);
		Label shipNameLabel = new Label(ship.getName());
		shipNameLabel.setStyle("-fx-font-size: 18;");
		shipNameLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipNameLabel);
		temp.setPrefWidth(150);
		root.add(temp, 1, 0);

		Label factionLabel = new Label("Owner: ");
		factionLabel.setStyle("-fx-font-size: 18;");
		factionLabel.setTextFill(Color.WHITE);
		temp = new Pane(factionLabel);
		root.add(temp, 0, 1);
		Label shipFactionLabel = new Label("Placeholder");
		shipFactionLabel.setStyle("-fx-font-size: 18;");
		shipFactionLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipFactionLabel);
		root.add(temp, 1, 1);

		Label upkeepLabel = new Label("Upkeep: ");
		upkeepLabel.setStyle("-fx-font-size: 18;");
		upkeepLabel.setTextFill(Color.WHITE);
		temp = new Pane(upkeepLabel);
		root.add(temp, 0, 2);
		Label shipUpkeepLabel = new Label("" + ship.getUpkeep());
		shipUpkeepLabel.setStyle("-fx-font-size: 18;");
		shipUpkeepLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipUpkeepLabel);
		root.add(temp, 1, 2);

		Label lasersLabel = new Label("Lasers: ");
		lasersLabel.setStyle("-fx-font-size: 18;");
		lasersLabel.setTextFill(Color.WHITE);
		temp = new Pane(lasersLabel);
		root.add(temp, 0, 3);
		Label shipLasersLabel = new Label("" + ship.getWeapons()[0]);
		shipLasersLabel.setStyle("-fx-font-size: 18;");
		shipLasersLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipLasersLabel);
		root.add(temp, 1, 3);

		Label missilesLabel = new Label("Missiles: ");
		missilesLabel.setStyle("-fx-font-size: 18;");
		missilesLabel.setTextFill(Color.WHITE);
		temp = new Pane(missilesLabel);
		root.add(temp, 0, 4);
		Label shipMissilesLabel = new Label("" + ship.getWeapons()[1]);
		shipMissilesLabel.setStyle("-fx-font-size: 18;");
		shipMissilesLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipMissilesLabel);
		root.add(temp, 1, 4);

		Label projectilesLabel = new Label("Projectiles: ");
		projectilesLabel.setStyle("-fx-font-size: 18;");
		projectilesLabel.setTextFill(Color.WHITE);
		temp = new Pane(projectilesLabel);
		root.add(temp, 0, 5);
		Label shipProjectilesLabel = new Label("" + ship.getWeapons()[2]);
		shipProjectilesLabel.setStyle("-fx-font-size: 18;");
		shipProjectilesLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipProjectilesLabel);
		root.add(temp, 1, 5);

		Label shieldsLabel = new Label("Shields: ");
		shieldsLabel.setStyle("-fx-font-size: 18;");
		shieldsLabel.setTextFill(Color.WHITE);
		temp = new Pane(shieldsLabel);
		root.add(temp, 0, 6);
		Label shipShieldsLabel = new Label("" + ship.getDefenses()[0]);
		shipShieldsLabel.setStyle("-fx-font-size: 18;");
		shipShieldsLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipShieldsLabel);
		root.add(temp, 1, 6);

		Label pointDefenseLabel = new Label("Point-Defense: ");
		pointDefenseLabel.setStyle("-fx-font-size: 18;");
		pointDefenseLabel.setTextFill(Color.WHITE);
		temp = new Pane(pointDefenseLabel);
		root.add(temp, 0, 7);
		Label shipPointDefenseLabel = new Label("" + ship.getDefenses()[1]);
		shipPointDefenseLabel.setStyle("-fx-font-size: 18;");
		shipPointDefenseLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipPointDefenseLabel);
		root.add(temp, 1, 7);

		Label armourLabel = new Label("Armour: ");
		armourLabel.setStyle("-fx-font-size: 18;");
		armourLabel.setTextFill(Color.WHITE);
		temp = new Pane(armourLabel);
		root.add(temp, 0, 8);
		Label shipArmourLabel = new Label("" + ship.getDefenses()[2]);
		shipArmourLabel.setStyle("-fx-font-size: 18;");
		shipArmourLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipArmourLabel);
		root.add(temp, 1, 8);
		
		
		

		Label healthLabel = new Label("Hull: ");
		healthLabel.setStyle("-fx-font-size: 18;");
		healthLabel.setTextFill(Color.WHITE);
		temp = new Pane(healthLabel);
		temp.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: " + star.getFaction().getColor());
		temp.setPrefWidth(150);
		root.add(temp, 2, 1);
		ImageView decomission = new ImageView(imageCache.getMinus());
		decomission.setTranslateX(8);
		decomission.setTranslateY(8);
		temp = new Pane(decomission);
		temp.setOnMouseClicked((event) -> {
			//TODO: decomission ship
		});
		temp.setPrefWidth(28);
		temp.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: " + star.getFaction().getColor());
		root.add(temp, 3, 1);
		Label shipHealthLabel = new Label("" + ship.getCurrentHealth());
		shipHealthLabel.setStyle("-fx-font-size: 18;");
		shipHealthLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipHealthLabel);
		temp.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: " + star.getFaction().getColor());
		root.add(temp, 4, 1);
		Label maxHealthLabel = new Label("/ " + ship.getMaxHealth());
		maxHealthLabel.setStyle("-fx-font-size: 18;");
		maxHealthLabel.setTextFill(Color.WHITE);
		temp = new Pane(maxHealthLabel);
		temp.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: " + star.getFaction().getColor());
		root.add(temp, 5, 1);
		ImageView repair = new ImageView(imageCache.getPlus());
		repair.setTranslateX(8);
		repair.setTranslateY(8);
		temp = new Pane(repair);
		temp.setOnMouseClicked((event) -> {
			ship.getFaction().addToTreasury(-5 * (ship.getMaxHealth() - ship.getCurrentHealth()));
			ship.repair();
		});
		temp.setPrefWidth(28);
		temp.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: " + star.getFaction().getColor());
		root.add(temp, 6, 1);
		
		
		Label cargoLabel = new Label("Cargo: ");
		cargoLabel.setStyle("-fx-font-size: 18;");
		cargoLabel.setTextFill(Color.WHITE);
		temp = new Pane(cargoLabel);
		root.add(temp, 2, 2);
		Label shipCargoLabel = new Label("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
		shipCargoLabel.setStyle("-fx-font-size: 18;");
		shipCargoLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipCargoLabel);
		root.add(temp, 4, 2);
		Label maxCargoLabel = new Label("/ " + ship.getCargoSpace());
		maxCargoLabel.setStyle("-fx-font-size: 18;");
		maxCargoLabel.setTextFill(Color.WHITE);
		temp = new Pane(maxCargoLabel);
		root.add(temp, 5, 2);
		

		Label shipSuppliesLabel = new Label("  " + (int)ship.getSupplies());
		Label suppliesLabel = new Label("\tSupplies: ");
		suppliesLabel.setStyle("-fx-font-size: 18;");
		suppliesLabel.setTextFill(Color.WHITE);
		temp = new Pane(suppliesLabel);
		root.add(temp, 2, 3);
		ImageView dumpSupplies = new ImageView(imageCache.getMinus());
		dumpSupplies.setTranslateX(8);
		dumpSupplies.setTranslateY(8);
		temp = new Pane(dumpSupplies);
		temp.setOnMouseClicked((event) -> {
			if(ship.getSupplies() > 0){
				ship.setSupplies(0);
				shipSuppliesLabel.setText("" + (int)ship.getSupplies());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 3, 3);
		ImageView decrementSupplies = new ImageView(imageCache.getMinus());
		decrementSupplies.setTranslateX(8);
		decrementSupplies.setTranslateY(8);
		temp = new Pane(decrementSupplies);
		temp.setOnMouseClicked((event) -> {
			if(ship.getSupplies() > 0){
				ship.setSupplies(ship.getSupplies() - 1 );
				shipSuppliesLabel.setText("" + (int)ship.getSupplies());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 4, 3);
		shipSuppliesLabel.setStyle("-fx-font-size: 18;");
		shipSuppliesLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipSuppliesLabel);
		root.add(temp, 5, 3);
		ImageView incrementSupplies = new ImageView(imageCache.getPlus());
		incrementSupplies.setTranslateX(8);
		incrementSupplies.setTranslateY(8);
		temp = new Pane(incrementSupplies);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoSpaceRemaining() > 0){
				ship.setSupplies(ship.getSupplies() + 1);
				shipSuppliesLabel.setText("" + (int)ship.getSupplies());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 6, 3);
		ImageView fillSupplies = new ImageView(imageCache.getPlus());
		fillSupplies.setTranslateX(8);
		fillSupplies.setTranslateY(8);
		temp = new Pane(fillSupplies);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoSpaceRemaining() > 0){
				ship.setSupplies(ship.getSupplies() + ship.getCargoSpaceRemaining());
				shipSuppliesLabel.setText("" + (int)ship.getSupplies());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 7, 3);
		
		
		Label shipPeopleLabel = new Label("  " + ship.getCargoPeople());
		Label populationLabel = new Label("\tCivilians: ");
		populationLabel.setStyle("-fx-font-size: 18;");
		populationLabel.setTextFill(Color.WHITE);
		temp = new Pane(populationLabel);
		root.add(temp, 2, 4);
		ImageView unloadPopulation = new ImageView(imageCache.getMinus());
		unloadPopulation.setTranslateX(8);
		unloadPopulation.setTranslateY(8);
		temp = new Pane(unloadPopulation);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoPeople() > 0){
				star.changePopulation(ship.getCargoPeople());
				ship.setCargoPeople(0);
				shipPeopleLabel.setText("" + ship.getCargoPeople());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 3, 4);
		ImageView decrementPopulation = new ImageView(imageCache.getMinus());
		decrementPopulation.setTranslateX(8);
		decrementPopulation.setTranslateY(8);
		temp = new Pane(decrementPopulation);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoPeople() > 0){
				star.changePopulation(1);
				ship.setCargoPeople(ship.getCargoPeople() - 1);
				shipPeopleLabel.setText("" + ship.getCargoPeople());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 4, 4);
		
		shipPeopleLabel.setStyle("-fx-font-size: 18;");
		shipPeopleLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipPeopleLabel);
		root.add(temp, 5, 4);
		ImageView incrementCargoPeople = new ImageView(imageCache.getPlus());
		incrementCargoPeople.setTranslateX(8);
		incrementCargoPeople.setTranslateY(8);
		temp = new Pane(incrementCargoPeople);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoSpaceRemaining() > 0 && star.getPopulation() > 0){
				star.changePopulation(-1);
				ship.setCargoPeople(ship.getCargoPeople() + 1);
				shipPeopleLabel.setText("" + ship.getCargoPeople());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 6, 4);
		ImageView fillCargoPeople = new ImageView(imageCache.getPlus());
		fillCargoPeople.setTranslateX(8);
		fillCargoPeople.setTranslateY(8);
		temp = new Pane(fillCargoPeople);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoSpaceRemaining() > 0  && star.getPopulation() > ship.getCargoSpaceRemaining()/2){
				star.changePopulation(-ship.getCargoSpaceRemaining()/2);
				ship.setCargoPeople(ship.getCargoSpaceRemaining()/2 + ship.getCargoPeople());
				shipPeopleLabel.setText("" + ship.getCargoPeople());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 7, 4);
		
		
		
		
		
		
		Label shipSoldiersLabel = new Label("  " + ship.getSoldiers());
		Label soldiersLabel = new Label("\tSoldiers: ");
		soldiersLabel.setStyle("-fx-font-size: 18;");
		soldiersLabel.setTextFill(Color.WHITE);
		temp = new Pane(soldiersLabel);
		root.add(temp, 2, 5);
		ImageView unloadSoldiers = new ImageView(imageCache.getMinus());
		unloadSoldiers.setTranslateX(8);
		unloadSoldiers.setTranslateY(8);
		temp = new Pane(unloadSoldiers);
		temp.setOnMouseClicked((event) -> {
			if(ship.getSoldiers() > 0){
				System.out.println("A");
				star.addSoldiers(ship.getSoldiers());
				ship.setSoldiers(0);
				shipSoldiersLabel.setText("" + ship.getSoldiers());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 3, 5);
		ImageView decrementSoldiers = new ImageView(imageCache.getMinus());
		decrementSoldiers.setTranslateX(8);
		decrementSoldiers.setTranslateY(8);
		temp = new Pane(decrementSoldiers);
		temp.setOnMouseClicked((event) -> {
			if(ship.getSoldiers() > 0){
				System.out.println("B");
				star.addSoldiers(1);
				ship.setSoldiers(ship.getSoldiers() - 1);
				shipSoldiersLabel.setText("" + ship.getSoldiers());
				shipCargoLabel.setText("" + (ship.getSoldiers() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 4, 5);
		
		shipSoldiersLabel.setStyle("-fx-font-size: 18;");
		shipSoldiersLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipSoldiersLabel);
		root.add(temp, 5, 5);
		ImageView incrementSoldiers = new ImageView(imageCache.getPlus());
		incrementSoldiers.setTranslateX(8);
		incrementSoldiers.setTranslateY(8);
		temp = new Pane(incrementSoldiers);
		temp.setOnMouseClicked((event) -> {
			if(ship.getCargoSpaceRemaining() > 0 && star.getSoldiers() > 0){
				System.out.println("C");
				star.addSoldiers(-1);
				ship.setSoldiers(ship.getSoldiers() + 1);
				shipSoldiersLabel.setText("" + ship.getSoldiers());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 6, 5);
		ImageView fillSoldiers = new ImageView(imageCache.getPlus());
		fillSoldiers.setTranslateX(8);
		fillSoldiers.setTranslateY(8);
		temp = new Pane(fillSoldiers);
		temp.setOnMouseClicked((event) -> {
			System.out.println(ship.getCargoSpaceRemaining());
			System.out.println(star.getSoldiers());
			System.out.println();
			
			
			
			if(ship.getCargoSpaceRemaining() > 0  && star.getSoldiers() > 0 ){
				System.out.println("D");
				if(star.getSoldiers() > ship.getCargoSpaceRemaining()/4){
					star.addSoldiers(-ship.getCargoSpaceRemaining()/4);
					ship.setSoldiers(ship.getCargoSpaceRemaining()/4 + ship.getSoldiers());
				} else { //if cargo is bigger than soldiers
					ship.setSoldiers(ship.getSoldiers() + star.getSoldiers());
					star.setSoldiers(0);
				}
				shipSoldiersLabel.setText("" + ship.getSoldiers());
				shipCargoLabel.setText("" + (ship.getCargoSpace() - ship.getCargoSpaceRemaining()));
				
			}
		});
		temp.setPrefWidth(28);
		root.add(temp, 7, 5);
		
		
		return root;
	}


	
	private Node makeShipGrid(Ship...ships) {
		FlowPane root = new FlowPane();
		root.setPadding(new Insets(35));
		root.setVgap(20);
		root.setHgap(20);
		
		Pane temp;
		if(ships != null){
			for(Ship ship : ships){
				temp = new StackPane();
				temp.setMinSize(200, 200);
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75); -fx-background-radius: 10, 10; -fx-border-radius: 10, 10; -fx-border-color: " + star.getFaction().getColor());
				
				VBox shipDisplay = new VBox();
				shipDisplay.setAlignment(Pos.CENTER);
				Label shipName = new Label(ship.getName());
				shipName.setStyle("-fx-font-size: 18;");
				shipName.setTextFill(Color.WHITE);
				ImageView shipPic = new ImageView(imageCache.getShipS());
				shipDisplay.getChildren().addAll(shipName, shipPic);
				
				temp.getChildren().add(shipDisplay);
				temp.setOnMouseClicked((event) -> {
					((Pane)event.getSource()).setStyle("-fx-background-color: rgba(16, 16, 16, .75); -fx-background-radius: 10, 10; -fx-border-radius: 10, 10; -fx-border-color: " + star.getFaction().getColor() + "; -fx-border-width: 3");
					this.setBottom(makeBottom(ship));
					event.consume();
				});
				root.getChildren().add(temp);
			}
		}
		
		return root;
	}
	
	
}
