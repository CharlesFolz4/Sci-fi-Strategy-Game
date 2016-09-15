package util.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.gui.images.ImageCache;

public class NewGamePane extends BorderPane{
	private ImageCache imageCache;
	private Label title;
	
	
	public NewGamePane(ImageCache imageCache){
		this.imageCache = imageCache;
		this.setCenter(makeNewGalaxySettings());
		
		HBox bottomRoot = new HBox();
		bottomRoot.setAlignment(Pos.BASELINE_RIGHT);
		bottomRoot.prefWidthProperty().bind(this.widthProperty());
		
		StackPane nextButton = new StackPane();
		Label nextLabel = new Label("Next");
		nextLabel.setStyle("-fx-font-size: 28");
		nextLabel.setTextFill(Color.WHITE);
		ImageView nextHighlight = new ImageView(imageCache.getButtonBRHighlight());
		nextHighlight.setVisible(false);
		nextButton.getChildren().addAll(new ImageView(imageCache.getButtonBR()), nextLabel, nextHighlight);
		nextButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	nextHighlight.setVisible(true);
	        }
	    });
		nextButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				nextHighlight.setVisible(false);
			}
		});
		nextButton.setOnMouseClicked((event) -> {
			if(title.getText().equals("Galaxy Settings")){
				this.setCenter(makeNewEmpire());
			} else if(title.getText().equals("Empire Settings")){
				//start game!!  :D
				//Game game = new Game();
				//game.start(new Stage());
				
				
				Stage stage = (Stage) nextButton.getScene().getWindow();
			    stage.close();
			}
		});
		
		
		bottomRoot.getChildren().addAll(nextButton);
		this.setBottom(bottomRoot);
		
		this.setStyle("-fx-background-color: rgba(16, 16, 16, .75);" +
				  "-fx-border-width: 4 4 4 4; -fx-border-color: white;");
	}

	private Node makeNewEmpire() {
		VBox root = new VBox();
		title.setText("Empire Settings");
		//do stuff
		
		return root;
	}

	private VBox makeNewGalaxySettings() {
		StackPane titlePane = new StackPane();
		titlePane.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white;");
		titlePane.prefWidthProperty().bind(this.widthProperty());
		title = new Label("Galaxy Settings");
		title.setStyle("-fx-font-size: 36");
		title.setTextFill(Color.WHITE);
		BorderPane.setAlignment(titlePane, Pos.CENTER);
		title.setPadding(new Insets(15));
		titlePane.getChildren().add(title);
		this.setTop(titlePane);
		
		
		
		VBox root = new VBox();
		
		HBox top = new HBox(50);
		top.setAlignment(Pos.CENTER);
		
		VBox systemQuantities = new VBox(8);
		
		Label starQuantityLabel   = new Label("Frenquency of Stars");
		starQuantityLabel.setTextFill(Color.WHITE);
		Slider starQuantitySlider = new Slider();
		starQuantitySlider.setMin(1);
		starQuantitySlider.setMax(10);
		starQuantitySlider.setValue(5);
		
		Label planetQuantityLabel   = new Label("Frenquency of All Planets");
		planetQuantityLabel.setTextFill(Color.WHITE);
		Slider planetQuantitySlider = new Slider();
		planetQuantitySlider.setMin(1);
		planetQuantitySlider.setMax(18);
		planetQuantitySlider.setValue(9);
		
		Label habitableQuantityLabel   = new Label("Frenquency of Habitable Planets");
		habitableQuantityLabel.setTextFill(Color.WHITE);
		Slider habitableQuantitySlider = new Slider();
		habitableQuantitySlider.setMin(1);
		habitableQuantitySlider.setMax(12);
		habitableQuantitySlider.setValue(4);
		
		systemQuantities.setAlignment(Pos.CENTER);
		systemQuantities.getChildren().addAll(starQuantityLabel, starQuantitySlider, new Label(""),
											  planetQuantityLabel, planetQuantitySlider, new Label(""),
											  habitableQuantityLabel, habitableQuantitySlider);
		
		
		ImageView galaxyView = new ImageView(imageCache.getGalaxies()[0]);
		
		VBox galaxyDescriptions = new VBox(8);
		
		Label galaxySizeLabel = new Label("Galaxy Size");
		galaxySizeLabel.setTextFill(Color.WHITE);
		Slider galaxySizeSlider = new Slider();
		galaxySizeSlider.setMin(10);
		galaxySizeSlider.setMax(100);
		galaxySizeSlider.setValue(25);
		
		Label galaxyShapeLabel = new Label("Galaxy Shape");
		galaxyShapeLabel.setTextFill(Color.WHITE);
		//something
		
		
		galaxyDescriptions.setAlignment(Pos.CENTER);
		galaxyDescriptions.getChildren().addAll(galaxySizeLabel, galaxySizeSlider, new Label(""),
												galaxyShapeLabel);
		
		top.getChildren().addAll(systemQuantities, galaxyView, galaxyDescriptions);
		top.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: white;");
		
		HBox bottom = new HBox();
		
		root.getChildren().addAll(top, bottom);
		
		return root;
	}
	
	
	
	
}
