package util.gui;

import Ships.Ship;
import Starsystem.Star;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

//TODO this is used for setting ships as 'permanent' system defenders, for loading cargo/colonists onto and off of ships
public class OrbitingShipPane extends BorderPane {
	GameGUI gameGUI;
	ImageCache imageCache;
	Star star;

	Ship selected;
	
	public OrbitingShipPane(Star star,GameGUI gameGUI, ImageCache imageCache, Ship... ships){
		this.gameGUI =gameGUI;
		this.imageCache = imageCache;
		
		this.setStyle("-fx-border-width: 4; -fx-border-color: white");
		
		HBox topPane = new HBox();
		Label shipyardLabel = new Label("Orbiting Ships");
		shipyardLabel.setStyle("-fx-font-size: 45;");
		shipyardLabel.setTextFill(Color.WHITE);
		topPane.getChildren().add(shipyardLabel);
		topPane.setAlignment(Pos.CENTER);
		topPane.setPadding(new Insets(40));
		topPane.setStyle("-fx-border-width: 0 0 3 0; -fx-border-color: white");
		this.setTop(topPane);
		
		this.setBottom(makeBottom());
		
		this.setCenter(makeShipGrid(ships));
	}


	private Node makeBottom() {
		
		
		
		
		return null;
	}
	
	private void updateBottom(Ship ship){
		
	}


	//TODO: add case for when ships == null
	private Node makeShipGrid(Ship...ships) {
		FlowPane root = new FlowPane();
		root.setPadding(new Insets(35));
		//TODO actually make the content
		Pane temp;
		Pane backTemp;
		
		for(Ship ship : ships){
			temp = new StackPane();
			temp.setMinSize(200, 200);
			temp.setStyle("-fx-background-color: rgba(16, 16, 16, .75); -fx-background-radius: 10, 10; -fx-border-radius: 10, 10; -fx-border-color: white");
			
			VBox shipDisplay = new VBox();
			shipDisplay.setAlignment(Pos.CENTER);
			Label shipName = new Label(ship.getName());
			shipName.setStyle("-fx-font-size: 18;");
			shipName.setTextFill(Color.WHITE);
			ImageView shipPic = new ImageView(imageCache.getShipS());
			shipDisplay.getChildren().addAll(shipName, shipPic);
			
			temp.getChildren().add(shipDisplay);
			temp.setOnMouseClicked((event) -> {
				((Pane)event.getSource()).setStyle("-fx-background-color: rgba(16, 16, 16, .75); -fx-background-radius: 10, 10; -fx-border-radius: 10, 10; -fx-border-color: white; -fx-border-width: 3");
			});
			root.getChildren().add(temp);
		}
		
		return root;
	}
	
	
}
