package util.gui;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
import Starsystem.Star;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import util.Faction;
import util.GameController;
import util.gui.images.ImageCache;

//Bigger click areas for the plus/minus buttons?
public class Shipyard extends BorderPane{
	private ImageCache imageCache;
	private Star selected;
	private Faction currentFaction;
	GameGUI gameGUI;
	
	private Label[] shipSystemsPoints;
	private Label[] weaponSystemsPoints;
	private Label[] defenseSystemsPoints;
	
	private TextField shipNameText;
	private TextArea descriptionText;
	
	private Label shipNameLabel;
	private Label[] shipSystemStats;
	private Label[] weaponSystemStats;
	private Label[] defenseSystemStats;
	
	private Label upkeepStat;
	private Label costStat;
	private Label buildTime;
	
	double upkeep;
	double cost;
	
	
	public Shipyard(ImageCache imageCache, Star selected, Faction currentFacion, GameGUI gameGUI){
		this.imageCache     = imageCache;
		this.selected       = selected;
		this.currentFaction = currentFacion;
		this.gameGUI        = gameGUI;
		this.upkeep = 1;
		this.cost   = 0;
		this.imageCache.loadShipyardImages();
		
		this.setStyle("-fx-border-width: 4; -fx-border-color: " + currentFacion.getColor());
		
		HBox topPane = new HBox();
		Label shipyardLabel = new Label("Shipyard");
		shipyardLabel.setStyle("-fx-font-size: 45;");
		shipyardLabel.setTextFill(Color.WHITE);
		topPane.getChildren().add(shipyardLabel);
		topPane.setAlignment(Pos.CENTER);
		topPane.setPadding(new Insets(40));
		topPane.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + currentFacion.getColor());
		this.setTop(topPane);

		HBox shipDataBox = new HBox();
		this.setCenter(shipDataBox);
		
		shipDataBox.getChildren().addAll(makeBuildPane(), makeShipDisplayPane());
	}
	
	//TODO: Make buttons-  Save as Template/Class, Load Template/Class,
	
	private Node makeShipDisplayPane(){
		GridPane shipDisplayPane = new GridPane();
		shipDisplayPane.setPadding(new Insets(20));
		Pane temp;
		
		Label shipPreviewLabel = new Label("Ship Preview:");
		shipPreviewLabel.setStyle("-fx-font-size: 24;");
		shipPreviewLabel.setTextFill(Color.WHITE);
		shipPreviewLabel.setTranslateX(5);
		temp = new Pane(shipPreviewLabel);
		temp.setStyle("-fx-border-width: 3 2 2 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 0, 0, 4, 1);
		
		Label nameLabel = new Label("Name: ");
		nameLabel.setStyle("-fx-font-size: 20;");
		nameLabel.setTextFill(Color.WHITE);
		nameLabel.setTranslateX(5);
		temp = new Pane(nameLabel);
		temp.setStyle("-fx-border-width: 0 0 0 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		temp.setPrefWidth(150);
		shipDisplayPane.add(temp, 0, 1);
		
		shipNameLabel = new Label("Unnamed");
		shipNameLabel.setStyle("-fx-font-size: 20;");
		shipNameLabel.setTextFill(Color.WHITE);
		temp = new Pane(shipNameLabel);
		temp.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		temp.setPrefWidth(150);
		shipDisplayPane.add(temp, 1, 1);
		
		Label[] statsLabels = {new Label("Hull: "), new Label("Propulsion: "), new Label("Sensors: "), new Label("Cargo Space: "), new Label("Hangar Space: ")};
		shipSystemStats = new Label[5];
		for(int i = 0; i< 5; ++i){
			statsLabels[i].setStyle("-fx-font-size: 20;");
			statsLabels[i].setTextFill(Color.WHITE);
			statsLabels[i].setTranslateX(5);
			temp = new Pane(statsLabels[i]);
			if(i == 4){
				temp.setStyle("-fx-border-width: 0 0 1 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 0 0 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			temp.setPrefWidth(150);
			shipDisplayPane.add(temp, 0, i+2);
			
			shipSystemStats[i] = new Label("0");
			shipSystemStats[i].setStyle("-fx-font-size: 20;");
			shipSystemStats[i].setTextFill(Color.WHITE);
			temp = new Pane(shipSystemStats[i]);
			if(i == 4){
				temp.setStyle("-fx-border-width: 0 1 1 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipDisplayPane.add(temp, 1, i+2);
		}
		
		Label[] weaponLabels = {new Label("Lasers: "), new Label("Missiles: "), new Label("Projectiles: ")};
		weaponSystemStats = new Label[3];
		for(int i = 0; i< 3; ++i){
			weaponLabels[i].setStyle("-fx-font-size: 20;");
			weaponLabels[i].setTextFill(Color.WHITE);
			weaponLabels[i].setTranslateX(5);
			temp = new Pane(weaponLabels[i]);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 0 0 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			temp.setPrefWidth(150);
			shipDisplayPane.add(temp, 0, i+7);
			
			weaponSystemStats[i] = new Label("0");
			weaponSystemStats[i].setStyle("-fx-font-size: 20;");
			weaponSystemStats[i].setTextFill(Color.WHITE);
			temp = new Pane(weaponSystemStats[i]);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 1 2 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipDisplayPane.add(temp, 1, i+7);
		}
		
		Label[] defenseLabels = {new Label("Shields: "), new Label("Point-Defense: "), new Label("Armor: ")};
		defenseSystemStats = new Label[3];
		for(int i = 0; i< 3; ++i){
			defenseLabels[i].setStyle("-fx-font-size: 20;");
			defenseLabels[i].setTextFill(Color.WHITE);
			defenseLabels[i].setTranslateX(5);
			temp = new Pane(defenseLabels[i]);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 0 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			temp.setPrefWidth(150);
			shipDisplayPane.add(temp, 2, i+7);
			
			defenseSystemStats[i] = new Label("0");
			defenseSystemStats[i].setStyle("-fx-font-size: 20;");
			defenseSystemStats[i].setTextFill(Color.WHITE);
			temp = new Pane(defenseSystemStats[i]);
			temp.setMinWidth(75);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 3 2 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipDisplayPane.add(temp, 3, i+7);
		}
		
		//filler pane
		temp = new Pane();
		temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 2, 1, 2, 1);
		
		Label upkeepLabel = new Label("Upkeep Cost: ");
		upkeepLabel.setStyle("-fx-font-size: 20;");
		upkeepLabel.setTextFill(Color.WHITE);
		upkeepLabel.setTranslateX(5);
		temp = new Pane(upkeepLabel);
		temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 2, 2);
		upkeepStat = new Label("0");
		upkeepStat.setStyle("-fx-font-size: 20;");
		upkeepStat.setTextFill(Color.WHITE);
		temp = new Pane(upkeepStat);
		temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 3, 2);
		
		Label costLabel = new Label("Build Cost: ");
		costLabel.setStyle("-fx-font-size: 20;");
		costLabel.setTextFill(Color.WHITE);
		costLabel.setTranslateX(5);
		temp = new Pane(costLabel);
		temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 2, 3);
		costStat = new Label("0");
		costStat.setStyle("-fx-font-size: 20;");
		costStat.setTextFill(Color.WHITE);
		temp = new Pane(costStat);
		temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 3, 3);

		Label buildTimeLabel = new Label("Build Time: ");
		buildTimeLabel.setStyle("-fx-font-size: 20;");
		buildTimeLabel.setTextFill(Color.WHITE);
		buildTimeLabel.setTranslateX(5);
		temp = new Pane(buildTimeLabel);
		temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 2, 4);
		buildTime = new Label("0");
		buildTime.setStyle("-fx-font-size: 20;");
		buildTime.setTextFill(Color.WHITE);
		temp = new Pane(buildTime);
		temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 3, 4);

		//filler pane
		temp = new Pane();
		temp.setStyle("-fx-border-width: 0 3 1 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipDisplayPane.add(temp, 2, 5, 2, 2);
		
		
		VBox buttons = new VBox();
		StackPane buildButton = new StackPane();
		buildButton.setAlignment(Pos.CENTER);
		ImageView buildButtonHighlight = new ImageView(imageCache.getBuildShipHighlight());
		imageCache.recolor(buildButtonHighlight, currentFaction.getColor());
		buildButtonHighlight.setVisible(false);
		Label buildButtonLabel = new Label("Build Specified Ship");
		buildButtonLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
		ImageView buildButtonImage = new ImageView(imageCache.getBuildShipButton());
		imageCache.recolor(buildButtonImage, currentFaction.getColor());
		buildButton.getChildren().addAll(buildButtonLabel, buildButtonImage, buildButtonHighlight);
		buildButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	buildButtonHighlight.setVisible(true);
	        }
	    });
		buildButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				buildButtonHighlight.setVisible(false);
			}
		});
		buildButton.setOnMouseClicked((event) -> {
			if(currentFaction.getTreasury() - cost >= 0){
				System.out.println("Building ship");
				int[] coords    = selected.getCoordinates();
				String shipName = shipNameLabel.getText();
				int hp          = Integer.parseInt(shipSystemStats[0].getText());
				int engine      = Integer.parseInt(shipSystemStats[1].getText());
				int cargoSpace  = Integer.parseInt(shipSystemStats[3].getText());
				
				int[] weapons  = {Integer.parseInt(weaponSystemStats[0].getText()), Integer.parseInt(weaponSystemStats[1].getText()), Integer.parseInt(weaponSystemStats[2].getText())};
				int[] defenses = {Integer.parseInt(defenseSystemStats[0].getText()), Integer.parseInt(defenseSystemStats[1].getText()), Integer.parseInt(defenseSystemStats[2].getText())};
				
				Ship ship;
				if(currentFaction.usesJump()){
					ship = new JumpShip(coords, currentFaction, shipName, (int)upkeep, hp, cargoSpace, engine, weapons, defenses);
				}else{
					ship = new WarpShip(coords, currentFaction, shipName, (int)upkeep, hp, cargoSpace, engine, weapons, defenses);
				}
				
				gameGUI.getGameController().buildShip(ship);
				
				currentFaction.addToTreasury(-cost);
				closeShipyard();
			} else {
				System.out.println("Not building ship");
			}
		});
		
		StackPane cancelButton = new StackPane();
		ImageView cancelButtonHighlight = new ImageView(imageCache.getCancelShipyardHighlight());
		imageCache.recolor(cancelButtonHighlight, currentFaction.getColor());
		cancelButtonHighlight.setVisible(false);
		Label cancelButtonLabel = new Label("Cancel");
		cancelButtonLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
		ImageView cancelButtonImage = new ImageView(imageCache.getCancelShipyardButton());
		imageCache.recolor(cancelButtonImage, currentFaction.getColor());
		cancelButton.getChildren().addAll(cancelButtonLabel, cancelButtonImage, cancelButtonHighlight);
		cancelButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	cancelButtonHighlight.setVisible(true);
	        }
	    });
		cancelButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				cancelButtonHighlight.setVisible(false);
			}
		});
		cancelButton.setOnMouseClicked((event) -> {
			closeShipyard();
		});
		
		buttons.getChildren().addAll(buildButton, cancelButton);
		
		
		
		
		shipDisplayPane.add(buttons, 0, 10, 4, 1);
		
		return shipDisplayPane;
	}
	
	
	

	private void closeShipyard() {
		imageCache.clearShipyardImages();
		gameGUI.getRoot().setCenter(gameGUI.makeMapView());
		gameGUI.getCancelButton().setVisible(false);
		gameGUI.updateDisplay();
	}

	private Node makeBuildPane() {
		GridPane shipBuildPane = new GridPane();
		shipBuildPane.setPadding(new Insets(20));
		
		final int[] buildPoints = {10 + 5 * selected.getShipyardLevel()};
		int maxBuildPoints = buildPoints[0];
		
		Label buildCostDisplay = null;
		Label upkeepCostDisplay = null;
		
		VBox buildPointBox = new VBox();
		Label buildPointLabel = new Label("Build Points:\t\t" + buildPoints[0] + "\t");
		buildPointLabel.setStyle("-fx-font-size: 20;");
		buildPointLabel.setTextFill(Color.WHITE);
		Label maxBuildPointLabel = new Label("Max Build Points:\t" + maxBuildPoints + "\t");
		maxBuildPointLabel.setStyle("-fx-font-size: 20;");
		maxBuildPointLabel.setTextFill(Color.WHITE);
		buildPointBox.getChildren().addAll(buildPointLabel, maxBuildPointLabel);
		buildPointBox.setStyle("-fx-border-width: 3 2 2 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		buildPointBox.setPadding(new Insets(5));
		shipBuildPane.add(buildPointBox, 0, 0, 4, 1);
		GridPane.setHgrow(buildPointBox, Priority.ALWAYS);
		
		Label shipSystemsLabel = new Label("Ship Systems:");
		shipSystemsLabel.setStyle("-fx-font-size: 24;");
		shipSystemsLabel.setTextFill(Color.WHITE);
		shipSystemsLabel.setTranslateX(5); 
		Pane shipSystemsLabelBox = new Pane(shipSystemsLabel);
		shipSystemsLabelBox.setStyle("-fx-border-width: 0 2 1 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(shipSystemsLabelBox, 0, 1, 4, 1);
		GridPane.setHgrow(shipSystemsLabelBox, Priority.ALWAYS);
		
		//TODO: Make shift +click go by increments of 5 or 10
		Label[] shipSystems = {new Label("Hull:"), new Label("Propulsion:"), new Label("Sensors:"), new Label("Cargo:"), new Label("Hangars:")};
		ImageView[] shipSystemsMinus = {new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus())};
		shipSystemsPoints = new Label[5];
		ImageView[] shipSystemsPlus = {new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus())};
		Pane temp;
		for(int i = 0; i < 5; ++i){
			shipSystems[i].setStyle("-fx-font-size: 20;");
			shipSystems[i].setTextFill(Color.WHITE);
			temp = new Pane(shipSystems[i]);
			temp.setPrefWidth(150);
			shipSystems[i].setTranslateX(10);
			if(i == 4){
				temp.setStyle("-fx-border-width: 0 0 2 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 0 0 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 0, i+2);
			
			shipSystemsMinus[i].setTranslateX(8);
			shipSystemsMinus[i].setTranslateY(8);
			temp = new Pane(shipSystemsMinus[i]);
			temp.setPrefWidth(28);
			if(i == 4){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 1, i+2);
			shipSystemsMinus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 5; ++j){
					if(source == shipSystemsMinus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(shipSystemsPoints[index].getText());
				count--;
				if(buildPoints[0] < maxBuildPoints){
					shipSystemsPoints[index].setText("" + count);
					buildPoints[0]++;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					int shipStat = (int) (count * currentFaction.getShipyardTechMods()[index]);
					shipSystemStats[index].setText("" + shipStat);
					
					cost -= currentFaction.getShipyardCostMods()[index];
					costStat.setText("" + (int)cost);
					upkeep -= currentFaction.getShipyardUpkeepMods()[index];
					upkeepStat.setText("" + (int)upkeep);
					
				}
			});
			
			shipSystemsPoints[i] = new Label("0");
			shipSystemsPoints[i].setStyle("-fx-font-size: 20;");
			shipSystemsPoints[i].setTextFill(Color.WHITE);
			temp = new Pane(shipSystemsPoints[i]);
			temp.setPrefWidth(30);
			if(i == 4){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 2, i+2);
			
			shipSystemsPlus[i].setTranslateX(8);
			shipSystemsPlus[i].setTranslateY(8);
			temp = new Pane(shipSystemsPlus[i]);
			temp.setPrefWidth(28);
			if(i == 4){
				temp.setStyle("-fx-border-width: 0 2 2 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 2 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 3, i+2);
			shipSystemsPlus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 5; ++j){
					if(source == shipSystemsPlus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(shipSystemsPoints[index].getText());
				count++;
				if(buildPoints[0] > 0){
					shipSystemsPoints[index].setText("" + count);
					buildPoints[0]--;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					int shipStat = (int) (count * currentFaction.getShipyardTechMods()[index]);
					shipSystemStats[index].setText("" + shipStat);
					
					cost += currentFaction.getShipyardCostMods()[index];
					costStat.setText("" + (int)cost);
					upkeep += currentFaction.getShipyardUpkeepMods()[index];
					upkeepStat.setText("" + (int)upkeep);
				}
			});
		}
		
		Label militarySystemsLabel = new Label("Military Systems:");
		militarySystemsLabel.setStyle("-fx-font-size: 24;");
		militarySystemsLabel.setTextFill(Color.WHITE);
		VBox militarySystemsLabelBox = new VBox(militarySystemsLabel);
		militarySystemsLabelBox.setStyle("-fx-border-width: 0 2 1 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		militarySystemsLabelBox.setPadding(new Insets(5));
		shipBuildPane.add(militarySystemsLabelBox, 0, 7, 8, 1);
		GridPane.setHgrow(militarySystemsLabelBox, Priority.ALWAYS);
		
		Label[] weaponSystems = {new Label("Lasers:"), new Label("Missiles:"), new Label("Projectiles:")};
		ImageView[] weaponSystemsMinus = {new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus())};
		weaponSystemsPoints = new Label[4];
		ImageView[] weaponSystemsPlus = {new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus())};
		for(int i = 0; i < 3; ++i){
			weaponSystems[i].setStyle("-fx-font-size: 20;");
			weaponSystems[i].setTextFill(Color.WHITE);
			weaponSystems[i].setTranslateX(10);
			temp = new Pane(weaponSystems[i]);
			temp.setPrefWidth(150);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 3 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 0 0 3; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 0, i+8);
			
			
			weaponSystemsMinus[i].setTranslateX(8);
			weaponSystemsMinus[i].setTranslateY(8);
			temp = new Pane(weaponSystemsMinus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 1, i+8);
			weaponSystemsMinus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == weaponSystemsMinus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(weaponSystemsPoints[index].getText());
				count--;
				if(buildPoints[0] < maxBuildPoints){
					weaponSystemsPoints[index].setText("" + count);
					buildPoints[0]++;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					int shipStat = (int)(count * currentFaction.getShipyardTechMods()[index + 5]);
					weaponSystemStats[index].setText("" + shipStat);

					cost -= currentFaction.getShipyardCostMods()[index + 5];
					costStat.setText("" + (int)cost);
					upkeep -= currentFaction.getShipyardUpkeepMods()[index + 5];
					upkeepStat.setText("" + (int)upkeep);
				}
			});
			
			weaponSystemsPoints[i] = new Label("0");
			weaponSystemsPoints[i].setStyle("-fx-font-size: 20;");
			weaponSystemsPoints[i].setTextFill(Color.WHITE);
			temp = new Pane(weaponSystemsPoints[i]);
			temp.setPrefWidth(30);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 2, i+8);
			
			weaponSystemsPlus[i].setTranslateX(8);
			weaponSystemsPlus[i].setTranslateY(8);
			temp = new Pane(weaponSystemsPlus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 1 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 3, i+8);
			weaponSystemsPlus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == weaponSystemsPlus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(weaponSystemsPoints[index].getText());
				count++;
				if(buildPoints[0] > 0){
					weaponSystemsPoints[index].setText("" + count);
					buildPoints[0]--;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");

					int shipStat = (int)(count * currentFaction.getShipyardTechMods()[index + 5]);
					weaponSystemStats[index].setText("" + shipStat);

					cost += currentFaction.getShipyardCostMods()[index + 5];
					costStat.setText("" + (int)cost);
					upkeep += currentFaction.getShipyardUpkeepMods()[index + 5];
					upkeepStat.setText("" + (int)upkeep);
				}
			});
		}
		
		Label[] defenseSystems = {new Label("Shields:"), new Label("Point-Defense:"), new Label("Armour:")};
		ImageView[] defenseSystemsMinus = {new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus())};
		defenseSystemsPoints = new Label[3];
		ImageView[]defenseSystemsPlus = {new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus())};
		for(int i = 0; i < 3; ++i){
			defenseSystems[i].setStyle("-fx-font-size: 20;");
			defenseSystems[i].setTextFill(Color.WHITE);
			defenseSystems[i].setTranslateX(10);
			temp = new Pane(defenseSystems[i]);
			temp.setPrefWidth(150);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 4, i+8);
			
			
			defenseSystemsMinus[i].setTranslateX(8);
			defenseSystemsMinus[i].setTranslateY(8);
			temp = new Pane(defenseSystemsMinus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 5, i+8);
			defenseSystemsMinus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == defenseSystemsMinus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(defenseSystemsPoints[index].getText());
				count--;
				if(buildPoints[0] < maxBuildPoints){
					defenseSystemsPoints[index].setText("" + count);
					buildPoints[0]++;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					int shipStat = (int)(count * currentFaction.getShipyardTechMods()[index + 8]);
					defenseSystemStats[index].setText("" + shipStat);

					cost += currentFaction.getShipyardCostMods()[index + 8];
					costStat.setText("" + (int)cost);
					upkeep += currentFaction.getShipyardUpkeepMods()[index + 8];
					upkeepStat.setText("" + (int)upkeep);
				}
			});

			defenseSystemsPoints[i] = new Label("0");
			defenseSystemsPoints[i].setStyle("-fx-font-size: 20;");
			defenseSystemsPoints[i].setTextFill(Color.WHITE);
			temp = new Pane(defenseSystemsPoints[i]);
			temp.setPrefWidth(30);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 6, i+8);
			
			defenseSystemsPlus[i].setTranslateX(8);
			defenseSystemsPlus[i].setTranslateY(8);
			temp = new Pane(defenseSystemsPlus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 2 3 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			} else {
				temp.setStyle("-fx-border-width: 0 2 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
			}
			shipBuildPane.add(temp, 7, i+8);
			defenseSystemsPlus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == defenseSystemsPlus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(defenseSystemsPoints[index].getText());
				count++;
				if(buildPoints[0] > 0){
					defenseSystemsPoints[index].setText("" + count);
					buildPoints[0]--;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");

					int shipStat = (int)(count * currentFaction.getShipyardTechMods()[index + 8]);
					defenseSystemStats[index].setText("" + shipStat);

					cost += currentFaction.getShipyardCostMods()[index + 8];
					costStat.setText("" + (int)cost);
					upkeep += currentFaction.getShipyardUpkeepMods()[index + 8];
					upkeepStat.setText("" + (int)upkeep);
				}
			});
		}
		
		

		Label shipInfo = new Label("Ship Information:");
		shipInfo.setStyle("-fx-font-size: 24;");
		shipInfo.setTextFill(Color.WHITE);
		VBox shipInfoBox = new VBox(shipInfo);
		shipInfoBox.setPadding(new Insets(5));
		shipInfoBox.setStyle("-fx-border-width: 3 3 1 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(shipInfoBox, 4, 0, 4, 1);
		GridPane.setHgrow(shipInfoBox, Priority.ALWAYS);
		
		Label nameLabel = new Label("Name:");
		nameLabel.setStyle("-fx-font-size: 20;");
		nameLabel.setTextFill(Color.WHITE);
		nameLabel.setTranslateX(5);
		temp = new Pane(nameLabel);
		temp.setTranslateY(4);
		temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 4, 1);
		shipNameText = new TextField();
		shipNameText.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + ";");
		shipBuildPane.add(shipNameText, 5, 1, 3, 1);
		shipNameText.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	shipNameLabel.setText(newValue);
		    }
		});
		
		Label buildCostLabel = new Label("Build Cost:");
		buildCostLabel.setStyle("-fx-font-size: 20;");
		buildCostLabel.setTextFill(Color.WHITE);
		buildCostLabel.setTranslateX(5);
		temp = new Pane(buildCostLabel);
		temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 4, 2);
		buildCostDisplay = new Label("0");
		buildCostDisplay.setStyle("-fx-font-size: 20;");
		buildCostDisplay.setTextFill(Color.WHITE);
		temp = new Pane(buildCostDisplay);
		temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 5, 2, 3, 1);
		temp = new Pane();
		temp.setMinWidth(100);
		shipBuildPane.add(temp, 7, 2);
		
		Label upkeepCostLabel = new Label("Upkeep Cost:");
		upkeepCostLabel.setStyle("-fx-font-size: 20;");
		upkeepCostLabel.setTextFill(Color.WHITE);
		upkeepCostLabel.setTranslateX(5);
		temp = new Pane(upkeepCostLabel);
		temp.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 4, 3);
		upkeepCostDisplay = new Label("0");
		upkeepCostDisplay.setStyle("-fx-font-size: 20;");
		upkeepCostDisplay.setTextFill(Color.WHITE);
		temp = new Pane(upkeepCostDisplay);
		temp.setStyle("-fx-border-width: 0 3 1 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 5, 3, 3, 1);
		
		
		Label descriptionLabel = new Label("Description:");
		descriptionLabel.setStyle("-fx-font-size: 20;");
		descriptionLabel.setTextFill(Color.WHITE);
		descriptionLabel.setTranslateX(5);
		temp = new Pane(descriptionLabel);
		temp.setStyle("-fx-border-width: 0 3 0 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 4, 4, 4, 1);
		descriptionText = new TextArea();
		descriptionText.setTranslateX(5);
		temp = new Pane(descriptionText);
		temp.setStyle("-fx-border-width: 0 3 1 0; -fx-border-color: " + currentFaction.getColor() + "; -fx-background-color: rgba(16, 16, 16, .75);");
		shipBuildPane.add(temp, 4, 5, 4, 2);
		descriptionText.setWrapText(true);
		descriptionText.setPrefWidth(295);
		descriptionText.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				ScrollBar scrollBarv = (ScrollBar)descriptionText.lookup(".scroll-bar:vertical");
				if(scrollBarv != null){
					scrollBarv.setDisable(true);
				}
				return;
		    }
		});
		descriptionText.setPrefHeight(20);
		
		
		return shipBuildPane;
	}
}
