package util.gui;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.Map;
import util.gui.images.ImageCache;

public class NewGamePane extends BorderPane{
	private ImageCache imageCache;
	private Label title;
	
	private Slider[] galaxySettings;
	private ArrayList<TextField>   factionNames;
	private ArrayList<RadioButton> factionUsesJump;
	private ArrayList<ColorPicker> factionColors;
	
	
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
				
			} else if(title.getText().equals("Faction Settings")){
				System.out.println("starting process");
				int dimension = (int) galaxySettings[3].getValue();
				double[] probabilities = {galaxySettings[0].getValue(),galaxySettings[1].getValue(),galaxySettings[2].getValue()};
				String[] factionNames = new String[this.factionNames.size()];
				boolean[] factionUsesJump = new boolean[this.factionUsesJump.size()];
				String[] factionColors = new String[this.factionColors.size()];
				
				Color tempColor;
				for(int i = 0; i < factionNames.length; ++i){ //all three arrays will always be same size
					factionNames[i] = this.factionNames.get(i).getText();
					factionUsesJump[i] = this.factionUsesJump.get(i).isSelected();
					tempColor = this.factionColors.get(i).getValue();
					int r,g,b;
					r = (int)(tempColor.getRed()  * 255);
			        g = (int)(tempColor.getGreen()* 255);
			        b = (int)(tempColor.getBlue() * 255);
			        factionColors[i] = "#" + Integer.toHexString(r) + (Integer.toHexString(r).length() == 1? "0":"") +
							   Integer.toHexString(g) + (Integer.toHexString(g).length() == 1? "0":"") +
							   Integer.toHexString(b) + (Integer.toHexString(b).length() == 1? "0":"");
				}
				System.out.println("Bloop");
				Map gameMap = new Map(dimension, dimension, probabilities, factionNames, factionUsesJump, factionColors);
	        	System.out.println("Moose");
	        	GameGUI gameGUI = new GameGUI(gameMap);
	        	Stage applicationStage = new Stage();
				gameGUI.start(applicationStage);
				Stage currentStage = (Stage) nextButton.getScene().getWindow();
				currentStage.close();
				System.out.println("Not here!");
			}
		});
		
		
		bottomRoot.getChildren().addAll(nextButton);
		this.setBottom(bottomRoot);
		
		this.setStyle("-fx-background-color: rgba(16, 16, 16, .75);" +
				  "-fx-border-width: 4 4 4 4; -fx-border-color: white;");
	}


	private Node makeNewEmpire() {
		ScrollPane testRoot = new ScrollPane();
		VBox root = new VBox(20);
		testRoot.setContent(root);
		root.setPadding(new Insets(100));
		title.setText("Faction Settings");
		//do stuff
		root.setAlignment(Pos.CENTER);
		
		factionNames    = new ArrayList<TextField>();
		factionUsesJump = new ArrayList<RadioButton>();
		factionColors   = new ArrayList<ColorPicker>();
		
		ArrayList<HBox> factionBoxes = new ArrayList<HBox>();
		int factionCount = (int)Math.sqrt(galaxySettings[3].getValue());
		System.out.println("Factions:" + factionCount);
		for(int i = 0; i < factionCount; ++i){
			HBox factionBox = new HBox();
			factionBoxes.add(factionBox);
			factionBox.setStyle("-fx-background-color: rgba(16, 16, 16, .85); -fx-border-width: 2; -fx-border-color: white;");
			factionBox.setPadding(new Insets(15));
			Region temp;
			
			Label nameLabel = new Label("Faction Name:   ");
			nameLabel.setTranslateY(4);
			nameLabel.setStyle("-fx-font-size: 18");
			nameLabel.setTextFill(Color.WHITE);
			TextField nameField = new TextField("Player " + (i+1));
			nameField.setTranslateY(5);
			temp = new Region();
			HBox.setHgrow(temp, Priority.ALWAYS);
			factionBox.getChildren().addAll(nameLabel, nameField, temp);
			
			VBox ftlToggleBox = new VBox(4);
			ToggleGroup ftlStyle = new ToggleGroup();
			RadioButton jumpFTL = new RadioButton("Jump Drives ");
			jumpFTL.setToggleGroup(ftlStyle);
			jumpFTL.setTextFill(Color.WHITE);
			RadioButton warpFTL = new RadioButton("Warp Drives ");
			warpFTL.setToggleGroup(ftlStyle);
			warpFTL.setTextFill(Color.WHITE);
			ftlToggleBox.getChildren().addAll(jumpFTL, warpFTL);
			ftlStyle.selectToggle(jumpFTL);
			Label ftlStyleLabel  = new Label("FTL Style:   ");
			ftlStyleLabel.setTranslateY(4);
			ftlStyleLabel.setStyle("-fx-font-size: 18");
			ftlStyleLabel.setTextFill(Color.WHITE);
			temp = new Region();
			HBox.setHgrow(temp, Priority.ALWAYS);
			factionBox.getChildren().addAll(ftlStyleLabel, ftlToggleBox, temp);
			
			ColorPicker factionColorPicker = new ColorPicker(Color.GREEN);
			factionColorPicker.setTranslateY(5);
			Label factionColorLabel = new Label("Faction Color:   ");
			factionColorLabel.setTranslateY(4);
			factionColorLabel.setStyle("-fx-font-size: 18");
			factionColorLabel.setTextFill(Color.WHITE);
			temp = new Region();
			HBox.setHgrow(temp, Priority.ALWAYS);
			factionBox.getChildren().addAll(factionColorLabel, factionColorPicker, temp);
			
			StackPane removeFactionButton = new StackPane();
			ImageView removeFactionImage = new ImageView(imageCache.getDeleteFactionButton());
			ImageView removeFactionHighlight = new ImageView(imageCache.getFactionButtonHighlight());
			removeFactionHighlight.setVisible(false);
			removeFactionButton.getChildren().addAll(removeFactionHighlight, removeFactionImage);
			removeFactionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent t) {
		        	removeFactionHighlight.setVisible(true);
		        }
		    });
			removeFactionButton.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					removeFactionHighlight.setVisible(false);
				}
			});
			removeFactionButton.setOnMouseClicked((event) -> {
				Node tempFactionBox = ((Node) event.getSource()).getParent();
				int index = factionBoxes.indexOf(tempFactionBox);
				((VBox)tempFactionBox.getParent()).getChildren().remove(tempFactionBox);
				factionBoxes.remove(index);
				factionNames.remove(index);
				factionUsesJump.remove(index);
				factionColors.remove(index);
			});
			factionBox.getChildren().add(removeFactionButton);
			
			factionNames.add(nameField);
			factionUsesJump.add(jumpFTL);
			factionColors.add(factionColorPicker);
			root.getChildren().add(factionBox);
		}
		
		StackPane addFactionButton = new StackPane();
		addFactionButton.setStyle("-fx-background-color: rgba(16, 16, 16, .85); -fx-background-radius: 10;");
		addFactionButton.setMaxSize(60, 60);
		ImageView addFactionImage = new ImageView(imageCache.getAddFactionButton());
		ImageView addFactionHighlights = new ImageView(imageCache.getFactionButtonHighlight());
		addFactionHighlights.setVisible(false);
		addFactionButton.getChildren().addAll(addFactionHighlights, addFactionImage);
		addFactionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	addFactionHighlights.setVisible(true);
	        }
	    });
		addFactionButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				addFactionHighlights.setVisible(false);
			}
		});
		addFactionButton.setOnMouseClicked((event) -> {
			HBox factionBox = new HBox();
			factionBoxes.add(factionBox);
			factionBox.setStyle("-fx-background-color: rgba(16, 16, 16, .85); -fx-border-width: 2; -fx-border-color: white;");
			factionBox.setPadding(new Insets(15));
			Region temp;
			
			Label nameLabel = new Label("Faction Name:   ");
			nameLabel.setTranslateY(4);
			nameLabel.setStyle("-fx-font-size: 18");
			nameLabel.setTextFill(Color.WHITE);
			
			int i = factionNames.size()-1;
			String text = factionNames.get(i).getText();
			TextField nameField = new TextField("Player " + (Character.getNumericValue(text.charAt(text.length()-1))+1));
			nameField.setTranslateY(5);
			temp = new Region();
			HBox.setHgrow(temp, Priority.ALWAYS);
			factionBox.getChildren().addAll(nameLabel, nameField, temp);
			
			VBox ftlToggleBox = new VBox(4);
			ToggleGroup ftlStyle = new ToggleGroup();
			RadioButton jumpFTL = new RadioButton("Jump Drives ");
			jumpFTL.setToggleGroup(ftlStyle);
			jumpFTL.setTextFill(Color.WHITE);
			RadioButton warpFTL = new RadioButton("Warp Drives ");
			warpFTL.setToggleGroup(ftlStyle);
			warpFTL.setTextFill(Color.WHITE);
			ftlToggleBox.getChildren().addAll(jumpFTL, warpFTL);
			ftlStyle.selectToggle(jumpFTL);
			Label ftlStyleLabel  = new Label("FTL Style:   ");
			ftlStyleLabel.setTranslateY(4);
			ftlStyleLabel.setStyle("-fx-font-size: 18");
			ftlStyleLabel.setTextFill(Color.WHITE);
			temp = new Region();
			HBox.setHgrow(temp, Priority.ALWAYS);
			factionBox.getChildren().addAll(ftlStyleLabel, ftlToggleBox, temp);
			
			ColorPicker factionColorPicker = new ColorPicker(Color.GREEN);
			factionColorPicker.setTranslateY(5);
			Label factionColorLabel = new Label("Faction Color:   ");
			factionColorLabel.setTranslateY(4);
			factionColorLabel.setStyle("-fx-font-size: 18");
			factionColorLabel.setTextFill(Color.WHITE);
			temp = new Region();
			HBox.setHgrow(temp, Priority.ALWAYS);
			factionBox.getChildren().addAll(factionColorLabel, factionColorPicker, temp);
			
			StackPane removeFactionButton = new StackPane();
			ImageView removeFactionImage = new ImageView(imageCache.getDeleteFactionButton());
			ImageView removeFactionHighlight = new ImageView(imageCache.getFactionButtonHighlight());
			removeFactionHighlight.setVisible(false);
			removeFactionButton.getChildren().addAll(removeFactionHighlight, removeFactionImage);
			removeFactionButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent t) {
		        	removeFactionHighlight.setVisible(true);
		        }
		    });
			removeFactionButton.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					removeFactionHighlight.setVisible(false);
				}
			});
			removeFactionButton.setOnMouseClicked((subEvent) -> {
				Node tempFactionBox = ((Node) subEvent.getSource()).getParent();
				int index = factionBoxes.indexOf(tempFactionBox);
				((VBox)tempFactionBox.getParent()).getChildren().remove(tempFactionBox);
				factionBoxes.remove(index);
				factionNames.remove(index);
				factionUsesJump.remove(index);
				factionColors.remove(index);
			});
			factionBox.getChildren().add(removeFactionButton);
			
			factionNames.add(nameField);
			factionUsesJump.add(jumpFTL);
			factionColors.add(factionColorPicker);
			root.getChildren().add(root.getChildren().size()-1, factionBox);
		});
		
		root.getChildren().add(addFactionButton);
		
		testRoot.setStyle("-fx-background-color: transparent");
		root.prefWidthProperty().bind(testRoot.widthProperty());
		testRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		testRoot.setVbarPolicy(ScrollBarPolicy.NEVER);
		return testRoot;
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
		
		galaxySettings = new Slider[4];
		
		VBox root = new VBox();
		
		HBox top = new HBox(50);
		top.setAlignment(Pos.CENTER);
		
		VBox systemQuantities = new VBox(8);
		
		Label starQuantityLabel   = new Label("Frenquency of Stars");
		starQuantityLabel.setTextFill(Color.WHITE);
		Slider starQuantitySlider = new Slider();
		starQuantitySlider.setMin(.001);
		starQuantitySlider.setMax(.05);
		starQuantitySlider.setValue(.025);
		galaxySettings[0] = starQuantitySlider;
		
		Label planetQuantityLabel   = new Label("Frenquency of All Planets");
		planetQuantityLabel.setTextFill(Color.WHITE);
		Slider planetQuantitySlider = new Slider();
		planetQuantitySlider.setMin(0.05);
		planetQuantitySlider.setMax(0.95);
		planetQuantitySlider.setValue(.75);
		galaxySettings[1] = planetQuantitySlider;
		
		Label habitableQuantityLabel   = new Label("Frenquency of Habitable Planets");
		habitableQuantityLabel.setTextFill(Color.WHITE);
		Slider habitableQuantitySlider = new Slider();
		habitableQuantitySlider.setMin(.05);
		habitableQuantitySlider.setMax(0.5);
		habitableQuantitySlider.setValue(.2);
		galaxySettings[2] = habitableQuantitySlider;
		
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
		galaxySettings[3] = galaxySizeSlider;
		
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
