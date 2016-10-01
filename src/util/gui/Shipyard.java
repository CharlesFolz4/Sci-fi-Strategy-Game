package util.gui;

import Starsystem.Star;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import util.gui.images.ImageCache;

//TODO: name the ship, add build confirmation button, ship cost label, ship upkeep label, turns to completion
//Bigger click areas for the plus/minus buttons
public class Shipyard extends BorderPane{
	public Shipyard(ImageCache imageCache, Star selected){
		GridPane shipBuildPane = new GridPane();
//		shipBuildPane.setPadding(new Insets(50,50,0,50));
		this.setStyle("-fx-border-width: 4; -fx-border-color: white");
		
		HBox topPane = new HBox();
		Label shipyardLabel = new Label("Shipyard");
		shipyardLabel.setStyle("-fx-font-size: 45;");
		shipyardLabel.setTextFill(Color.WHITE);
		topPane.getChildren().add(shipyardLabel);
		topPane.setAlignment(Pos.CENTER);
		topPane.setPadding(new Insets(40));
		topPane.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: white");
		this.setTop(topPane);
		//^^Header stuff
		
		HBox shipDataBox = new HBox();
		this.setCenter(shipDataBox);
		
		final int[] buildPoints = {10 + 5 * selected.getShipyardLevel()};
		int maxBuildPoints = buildPoints[0];
		

		VBox buildPointBox = new VBox();
		Label buildPointLabel = new Label("Build Points:\t\t" + buildPoints[0] + "\t");
		buildPointLabel.setStyle("-fx-font-size: 20;");
		buildPointLabel.setTextFill(Color.WHITE);
		Label maxBuildPointLabel = new Label("Max Build Points:\t" + maxBuildPoints + "\t");
		maxBuildPointLabel.setStyle("-fx-font-size: 20;");
		maxBuildPointLabel.setTextFill(Color.WHITE);
		buildPointBox.getChildren().addAll(buildPointLabel, maxBuildPointLabel);
		buildPointBox.setStyle("-fx-border-width: 0 2 2 0; -fx-border-color: white");
		shipBuildPane.add(buildPointBox, 0, 0, 4, 1);
		GridPane.setHgrow(buildPointBox, Priority.ALWAYS);
		
		Label shipSystemsLabel = new Label("Ship Systems:");
		shipSystemsLabel.setStyle("-fx-font-size: 24;");
		shipSystemsLabel.setTextFill(Color.WHITE);
		VBox shipSystemsLabelBox = new VBox(shipSystemsLabel);
		shipSystemsLabelBox.setStyle("-fx-border-width: 0 2 1 0; -fx-border-color: white");
		shipBuildPane.add(shipSystemsLabelBox, 0, 1, 4, 1);
		GridPane.setHgrow(shipSystemsLabelBox, Priority.ALWAYS);
		
		Label[] shipSystems = {new Label("Hull:"), new Label("Propulsion:"), new Label("Sensors:"), new Label("Cargo:")};
		ImageView[] shipSystemsMinus = {new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus())};
		Label[] shipSystemsPoints = {new Label("0"), new Label("0"), new Label("0"), new Label("0")};
		ImageView[] shipSystemsPlus = {new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus())};
		Pane temp;
		for(int i = 0; i < 4; ++i){
			shipSystems[i].setStyle("-fx-font-size: 20;");
			shipSystems[i].setTextFill(Color.WHITE);
			temp = new Pane(shipSystems[i]);
			temp.setPrefWidth(150);
			if(i == 3){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 0, i+2);
			
			shipSystemsMinus[i].setTranslateX(8);
			shipSystemsMinus[i].setTranslateY(8);
			temp = new Pane(shipSystemsMinus[i]);
			temp.setPrefWidth(28);
			if(i == 3){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 1, i+2);
			shipSystemsMinus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
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
					
					//TODO link to ship display and cost and stuff
					//shipSystemsPoints[index].setText("" + (int)(count * gameController.getCurrentFaction().getShipyardTechMods()[index]));
				}
			});
			
			shipSystemsPoints[i].setStyle("-fx-font-size: 20;");
			shipSystemsPoints[i].setTextFill(Color.WHITE);
			temp = new Pane(shipSystemsPoints[i]);
			temp.setPrefWidth(30);
			if(i == 3){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 2, i+2);
			
			shipSystemsPlus[i].setTranslateX(8);
			shipSystemsPlus[i].setTranslateY(8);
			temp = new Pane(shipSystemsPlus[i]);
			temp.setPrefWidth(28);
			temp.setStyle("-fx-border-width: 0 2 0 0; -fx-border-color: white");
			if(i == 3){
				temp.setStyle("-fx-border-width: 0 2 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 3, i+2);
			shipSystemsPlus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
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
					
					//TODO link to ship display and cost and stuff
					//shipSystemsPoints[index].setText("" + (int)(count * gameController.getCurrentFaction().getShipyardTechMods()[index]));
				}
			});
		}
		
		Label militarySystemsLabel = new Label("Military Systems:");
		militarySystemsLabel.setStyle("-fx-font-size: 24;");
		militarySystemsLabel.setTextFill(Color.WHITE);
		VBox militarySystemsLabelBox = new VBox(militarySystemsLabel);
		militarySystemsLabelBox.setStyle("-fx-border-width: 0 1 1 0; -fx-border-color: white");
		shipBuildPane.add(militarySystemsLabelBox, 0, 6, 8, 1);
		GridPane.setHgrow(militarySystemsLabelBox, Priority.ALWAYS);
		
		Label[] weaponSystems = {new Label("Lasers:"), new Label("Missiles:"), new Label("Projectiles:")};
		ImageView[] weaponSystemsMinus = {new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus())};
		Label[] weaponSystemsPoints = {new Label("0"), new Label("0"), new Label("0")};
		ImageView[] weaponSystemsPlus = {new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus())};
		for(int i = 0; i < 3; ++i){
			weaponSystems[i].setStyle("-fx-font-size: 20;");
			weaponSystems[i].setTextFill(Color.WHITE);
			temp = new Pane(weaponSystems[i]);
			temp.setPrefWidth(150);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 0, i+7);
			
			
			weaponSystemsMinus[i].setTranslateX(8);
			weaponSystemsMinus[i].setTranslateY(8);
			temp = new Pane(weaponSystemsMinus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 1, i+7);
			weaponSystemsMinus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == weaponSystemsMinus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(shipSystemsPoints[index].getText());
				count--;
				if(buildPoints[0] < maxBuildPoints){
					weaponSystemsPoints[index].setText("" + count);
					buildPoints[0]++;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					//TODO link to ship display and cost and stuff
					//shipSystemsPoints[index].setText("" + (int)(count * gameController.getCurrentFaction().getShipyardTechMods()[index]));
				}
			});
			
			weaponSystemsPoints[i].setStyle("-fx-font-size: 20;");
			weaponSystemsPoints[i].setTextFill(Color.WHITE);
			temp = new Pane(weaponSystemsPoints[i]);
			temp.setPrefWidth(30);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 2, i+7);
			
			weaponSystemsPlus[i].setTranslateX(8);
			weaponSystemsPlus[i].setTranslateY(8);
			temp = new Pane(weaponSystemsPlus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 1 2 0; -fx-border-color: white");
			} else {
				temp.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 3, i+7);
			weaponSystemsPlus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == weaponSystemsPlus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(shipSystemsPoints[index].getText());
				count++;
				if(buildPoints[0] > 0){
					weaponSystemsPoints[index].setText("" + count);
					buildPoints[0]--;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					//TODO link to ship display and cost and stuff
					//shipSystemsPoints[index].setText("" + (int)(count * gameController.getCurrentFaction().getShipyardTechMods()[index]));
				}
			});
		}
		
		Label[] defenseSystems = {new Label("Shields:"), new Label("Point-Defense:"), new Label("Armour:")};
		ImageView[] defenseSystemsMinus = {new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus()), new ImageView(imageCache.getMinus())};
		Label[] defenseSystemsPoints = {new Label("0"), new Label("0"), new Label("0")};
		ImageView[] defenseSystemsPlus = {new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus()), new ImageView(imageCache.getPlus())};
		for(int i = 0; i < 3; ++i){
			defenseSystems[i].setStyle("-fx-font-size: 20;");
			defenseSystems[i].setTextFill(Color.WHITE);
			temp = new Pane(defenseSystems[i]);
			temp.setPrefWidth(150);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 4, i+7);
			
			
			defenseSystemsMinus[i].setTranslateX(8);
			defenseSystemsMinus[i].setTranslateY(8);
			temp = new Pane(defenseSystemsMinus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 5, i+7);
			defenseSystemsMinus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == defenseSystemsMinus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(shipSystemsPoints[index].getText());
				count--;
				if(buildPoints[0] < maxBuildPoints){
					defenseSystemsPoints[index].setText("" + count);
					buildPoints[0]++;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					//TODO link to ship display and cost and stuff
					//shipSystemsPoints[index].setText("" + (int)(count * gameController.getCurrentFaction().getShipyardTechMods()[index]));
				}
			});
			
			defenseSystemsPoints[i].setStyle("-fx-font-size: 20;");
			defenseSystemsPoints[i].setTextFill(Color.WHITE);
			temp = new Pane(defenseSystemsPoints[i]);
			temp.setPrefWidth(30);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 6, i+7);
			
			defenseSystemsPlus[i].setTranslateX(8);
			defenseSystemsPlus[i].setTranslateY(8);
			temp = new Pane(defenseSystemsPlus[i]);
			temp.setPrefWidth(28);
			if(i == 2){
				temp.setStyle("-fx-border-width: 0 1 2 0; -fx-border-color: white");
			} else {
				temp.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: white");
			}
			shipBuildPane.add(temp, 7, i+7);
			defenseSystemsPlus[i].setOnMouseClicked((event) -> {
				int index = -1;
				ImageView source = (ImageView)event.getSource();
				for(int j = 0; j < 4; ++j){
					if(source == defenseSystemsPlus[j]){
						index = j;
						break;
					}
				}
				int count = Integer.parseInt(shipSystemsPoints[index].getText());
				count++;
				if(buildPoints[0] > 0){
					defenseSystemsPoints[index].setText("" + count);
					buildPoints[0]--;
					buildPointLabel.setText("Build Points:\t\t" + buildPoints[0] +"\t");
					
					//TODO link to ship display and cost and stuff
					//shipSystemsPoints[index].setText("" + (int)(count * gameController.getCurrentFaction().getShipyardTechMods()[index]));
				}
			});
		}
		
		
		
		
		GridPane shipDisplayPane = new GridPane();
		shipDataBox.getChildren().addAll(shipBuildPane, shipDisplayPane);
	}
}
