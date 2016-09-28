package util.gui;

import java.util.Arrays;

import Ships.JumpShip;
import Ships.Ship;
import Ships.WarpShip;
import Starsystem.Star;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.GameController;
import util.Location;
import util.Map;
import util.Selectable;
import util.audio.AudioCache;
import util.gui.images.ImageCache;

//TODO: Add attacking and building, fix turn click bug
//TODO: Add comprehensive javadoc
public class GameGUI extends Application{
	private ImageCache imageCache;
	private AudioCache audioCache;
	private Map gameMap;
	private GameController gameController;
	private Selectable selected;
	private ImageView targetMarker;
	
	private GridPane mapView;
	private ImageView sideMenuImage;
	private Label sideMenuTitle;
	private Label[] sideInfoLabels;
	private Label topLabel;
	private Label middleLabel;
	private Label bottomLabel;
	
	private boolean shipMoveFlag;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane subRoot = new BorderPane();
		subRoot.setStyle("-fx-background-color: transparent");
		StackPane centerPane = new StackPane();
		centerPane.setStyle("-fx-background-color: transparent; -fx-border-width: 4; -fx-border-color: white;");
		centerPane.prefHeightProperty().bind(subRoot.heightProperty());
		
		BorderPane utilPane = new BorderPane();
		utilPane.setStyle("-fx-background-color: transparent");
		HBox bottomRoot = new HBox();
		bottomRoot.setAlignment(Pos.BASELINE_RIGHT);
		bottomRoot.prefWidthProperty().bind(utilPane.widthProperty());

		
		StackPane nextButton = new StackPane();
		Label nextLabel = new Label("Next");
		nextLabel.setStyle("-fx-font-size: 28");
		nextLabel.setTextFill(Color.WHITE);
		ImageView nextHighlight = new ImageView(imageCache.getButtonBRHighlight());
		nextHighlight.setVisible(false);
		nextButton.getChildren().addAll(new ImageView(imageCache.getButtonBR()), nextLabel, nextHighlight);
		nextButton.setOnMouseEntered((event) -> {
        	nextHighlight.setVisible(true);
	    });
		nextButton.setOnMouseExited((event) -> {
			nextHighlight.setVisible(false);
		});
		nextButton.setOnMouseClicked((event) -> {
			System.out.println("Next clicked");
			gameController.endFactionTurn();
			updateSideMenu();
		});
		
		bottomRoot.getChildren().addAll(nextButton);
		utilPane.setBottom(bottomRoot);
		utilPane.setPickOnBounds(false);
		bottomRoot.setStyle("-fx-background-color: transparent");

		
		centerPane.getChildren().addAll(utilPane, makeMapView());
		centerPane.setPickOnBounds(false);
		subRoot.setCenter(centerPane);
		VBox sideMenu = makeSideMenu();
		sideMenu.prefHeightProperty().bind(subRoot.heightProperty());
//		sideMenu.setTranslateY(210);
//		sideMenu.setTranslateX(-70);
		subRoot.setRight(sideMenu);
		
		StackPane root = new StackPane();
		root.getChildren().addAll(new ImageView(imageCache.getBackground()), subRoot);
		
		primaryStage.setScene(new Scene(root));
		primaryStage.getIcons().add(imageCache.getFlag());
		primaryStage.setTitle("PLACEHOLDER");
		//primaryStage.setFullScreen(true);
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			System.exit(0);;
		});
	}
	
	private void updateSideMenu() {
		sideMenuTitle.setText(gameController.getCurrentFaction().getName());
		sideMenuImage.setImage(imageCache.getGalaxies()[0]);
		
		sideInfoLabels[0].setText("Income: \t\t" + gameController.getCurrentFaction().getIncome());
		sideInfoLabels[1].setText("Treasury: \t\t" + gameController.getCurrentFaction().getTreasury());
		sideInfoLabels[2].setText("Population: \t" + gameController.getCurrentFaction().getPopulation());
		sideInfoLabels[3].setText("Planets: \t\t" + gameController.getCurrentFaction().getPlanetCount());
		sideInfoLabels[4].setText("Systems: \t\t" + gameController.getCurrentFaction().getSystemCount());
		
	}

	private VBox makeSideMenu() {
		VBox root = new VBox(15);
		
		VBox infoBox = new VBox();
		infoBox.setAlignment(Pos.CENTER);
		sideMenuTitle = new Label(gameController.getCurrentFaction().getName());
		sideMenuTitle.setStyle("-fx-font-size: 40;");
		sideMenuTitle.setTextFill(Color.WHITE);
		sideMenuTitle.setAlignment(Pos.CENTER);
		sideMenuImage = new ImageView(imageCache.getGalaxies()[0]);
		Pane imagePane = new Pane(sideMenuImage);
		sideMenuImage.setX(1);
		sideMenuImage.setY(1); //because JavaFX is stupid with alignments and Panes
		imagePane.setStyle("-fx-border-width: 1; -fx-border-color: white");
		
		infoBox.getChildren().addAll(sideMenuTitle, imagePane);
		
		sideInfoLabels = new Label[5];
		for(int i = 0; i < sideInfoLabels.length; ++i){
			sideInfoLabels[i] = new Label();
			sideInfoLabels[i].setTextFill(Color.WHITE);
			sideInfoLabels[i].setAlignment(Pos.CENTER_LEFT);
		}
		sideInfoLabels[0].setText("Income: \t\t" + gameController.getCurrentFaction().getIncome());
		sideInfoLabels[1].setText("Treasury: \t\t" + gameController.getCurrentFaction().getTreasury());
		sideInfoLabels[2].setText("Population: \t" + gameController.getCurrentFaction().getPopulation());
		sideInfoLabels[3].setText("Planets: \t\t" + gameController.getCurrentFaction().getPlanetCount());
		sideInfoLabels[4].setText("Systems: \t\t" + gameController.getCurrentFaction().getSystemCount());
		infoBox.getChildren().addAll(sideInfoLabels);
		
		VBox actionBox = new VBox(20);
		
		ImageView[] highlights = new ImageView[3];
		for(int i = 0; i < highlights.length; ++i){
			highlights[i] = new ImageView(imageCache.getMenuButtonHighlight());
			highlights[i].setVisible(false);
		}
		
		StackPane topButton = new StackPane();
		topButton.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		topButton.getChildren().add(highlights[0]);
		topLabel = new Label("Empire");
		topLabel.setStyle("-fx-font-size: 40;");
		topLabel.setTextFill(Color.WHITE);
		topButton.getChildren().add(topLabel);
		topButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[0].setVisible(true);
	        }
	    });
		topButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[0].setVisible(false);
			}
		});
		topButton.setOnMouseClicked((event) -> {
			topStateMachine();
		});
		
		StackPane middleButton = new StackPane();
		middleButton.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		middleButton.getChildren().add(highlights[1]);
		middleLabel = new Label("Foreign Affairs");
		middleLabel.setStyle("-fx-font-size: 40;");
		middleLabel.setTextFill(Color.WHITE);
		middleButton.getChildren().add(middleLabel);
		middleButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[1].setVisible(true);
	        }
	    });
		middleButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[1].setVisible(false);
			}
		});
		
		StackPane bottomButton = new StackPane();
		bottomButton.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		bottomButton.getChildren().add(highlights[2]);
		bottomLabel = new Label("Science");
		bottomLabel.setStyle("-fx-font-size: 40;");
		bottomLabel.setTextFill(Color.WHITE);
		bottomButton.getChildren().add(bottomLabel);
		bottomButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[2].setVisible(true);
	        }
	    });
		bottomButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[2].setVisible(false);
			}
		});
		
		Button tempTurnButton = new Button("next");
		tempTurnButton.setOnAction( (event) -> {
			gameController.endFactionTurn();
			updateSideMenu();
		});
		actionBox.getChildren().addAll(topButton, middleButton, bottomButton, tempTurnButton);
		
		root.setPadding(new Insets(25, 25, 0, 25));
		root.setStyle("-fx-background-color: rgba(16, 16, 16, .75);" +
					  "-fx-border-width: 0 0 0 4; -fx-border-color: #D0D0E0;");
		root.getChildren().addAll(infoBox, actionBox);
		return root;
	}
	
	private void topStateMachine() {
		if(selected instanceof Ship && !shipMoveFlag){
			shipMoveFlag = true;
		} else if (selected instanceof Ship && shipMoveFlag){
			shipMoveFlag = false;
		}
		
	}

	private void select(Selectable newSelected){
		if(targetMarker.getParent() != null){
			((StackPane)targetMarker.getParent()).getChildren().remove(targetMarker);
		}
		
		selected = newSelected;
		if(selected == null){
			sideMenuImage.setImage(imageCache.getGalaxies()[0]);
			sideMenuTitle.setText(gameController.getCurrentFaction().getName());
			topLabel.setText("Empire");
			middleLabel.setText("Foreign Affairs");
			bottomLabel.setText("Science");
			
			sideInfoLabels[0].setText("Income: \t\t" + gameController.getCurrentFaction().getIncome());
			sideInfoLabels[1].setText("Treasury: \t\t" + gameController.getCurrentFaction().getTreasury());
			sideInfoLabels[2].setText("Population: \t" + gameController.getCurrentFaction().getPopulation());
			sideInfoLabels[3].setText("Planets: \t\t" + gameController.getCurrentFaction().getPlanetCount());
			sideInfoLabels[4].setText("Systems: \t\t" + gameController.getCurrentFaction().getSystemCount());
			
		} else if (selected instanceof Ship){
			
			((StackPane)getNodeByCoordinate(mapView, ((Ship)selected).getDestination()[0], ((Ship)selected).getDestination()[1])).getChildren().add(targetMarker);
			
			
			sideMenuImage.setImage(imageCache.getShip());
			topLabel.setText("Move");
			middleLabel.setText("Attack");
			bottomLabel.setText("Patrol");
			sideMenuTitle.setText(((Ship) selected).getName());
			
			sideInfoLabels[0].setText("HP: \t\t" + gameController.getCurrentFaction().getIncome());
			sideInfoLabels[2].setText("Destination: \t(" + ((Ship) selected).getDestination()[0] + "," + ((Ship) selected).getDestination()[1] + ")");
			sideInfoLabels[3].setText("Armament: \t\tN/A");
			sideInfoLabels[4].setText("Defenses: \t\tN/A");
			if( selected instanceof WarpShip){
				sideInfoLabels[1].setText("Movement: \t\t" + ((WarpShip)selected).getMovementRemaining() +"/" + ((WarpShip)selected).getSpeed());
			} else {
				sideInfoLabels[1].setText("Turns to jump: \t" + ((JumpShip)selected).getTurnsToJump());
				
			}
			
		} else if (selected instanceof Star){
			sideMenuImage.setImage(imageCache.getBlueStar());
			sideMenuTitle.setText(((Star) selected).getName());
			
			if(((Star) selected).getFaction() == null){
				topLabel.setText("Colonize");
				middleLabel.setText("Survey");
				bottomLabel.setText("Terraform");
				
				sideInfoLabels[0].setText("Population cap: \t" + ((Star) selected).getPopCap());
				sideInfoLabels[1].setText("Population: \t\tUninhabited");
				sideInfoLabels[2].setText("Industry: \t\tNone");
				sideInfoLabels[3].setText("Shipyard: \t\tNone");
				sideInfoLabels[4].setText("Planets: \t\t" + ((Star) selected).getPlanets().length);
			} else if (((Star) selected).getFaction() == gameController.getCurrentFaction()){
				topLabel.setText("Manage");
				middleLabel.setText("Ships");
				bottomLabel.setText("Shipyard");
				
				sideInfoLabels[0].setText("Income: \t\t" + ((Star) selected).getIncome());
				sideInfoLabels[1].setText("Population: \t" + ((Star) selected).getPopulation());
				sideInfoLabels[2].setText("Industry: \tN/A");
				sideInfoLabels[3].setText("Shipyard: \t\tN/A");
				sideInfoLabels[4].setText("Planets: \t\t" + ((Star) selected).getPlanets().length);
			} else if (((Star) selected).getFaction() != gameController.getCurrentFaction()){
				topLabel.setText("Attack");
				middleLabel.setText("Intel");
				bottomLabel.setText("Speak To");
				
				sideInfoLabels[0].setText("Income: \t\tUnknown");
				sideInfoLabels[1].setText("Population: \t" + ((Star) selected).getPopulation());
				sideInfoLabels[2].setText("Industry: \tN/A");
				sideInfoLabels[3].setText("Shipyard: \t\tN/A");
				sideInfoLabels[4].setText("Planets: \t\t" + ((Star) selected).getPlanets().length);
			}
		}
	}

	public void updateDisplay(){
		StackPane temp;
		for(int x = 0; x < gameMap.getDimensions()[0]; ++x){
			for(int y = 0; y < gameMap.getDimensions()[1]; ++y){
				//System.out.println("testing " + x + "," + y);
				try{
					if(gameMap.getMap()[x][y] == null){
						if(!((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().isEmpty()){
							temp = (StackPane)getNodeByCoordinate(mapView, x, y);
							int[] coords = {x,y};
							if (!Arrays.equals((((Ship)selected).getDestination()),(coords))){
								temp.getChildren().clear();
							}
						}
					} else if (gameMap.getMap()[x][y].getShips() != null && (((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().isEmpty() || ((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().contains(targetMarker))){
						((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().add(new ImageView(imageCache.getShipS()));
						//System.out.println("added " + x + "," + y);
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					System.out.println(x + "," + y);
					System.out.println(gameMap.getMap()[x][y]);
					break;
				}
			}
		}
	}
	
	private double limit(double num, double limit){
		if(num > limit){
			num = limit;
		} else if (num < -limit){
			num = -limit;
		}
		return num;
	}
	
	private Node makeMapView() {
		mapView = new GridPane();
		final int[] dragX = new int[1];
		final int[] dragY = new int[1];
		
		mapView.setOnMouseDragged((event) -> {
			if(dragX[0] != 0){
				mapView.setTranslateX( mapView.getTranslateX() + limit(event.getX() - dragX[0], 25));
				mapView.setTranslateY( mapView.getTranslateY() + limit(event.getY() - dragY[0], 25));
			}
			dragX[0] = (int)event.getX();
			dragY[0] = (int)event.getY();
		});
		
		StackPane temp;
		for(int x = 0; x < gameMap.getDimensions()[0]; ++x){
			for(int y = 0; y < gameMap.getDimensions()[1]; ++y){
				temp = new StackPane();
				temp.setPrefSize(100, 100);
				//temp.setMinSize(100, 100);
				//TODO: make nodes not push each other/overlapping nodes
				temp.setStyle("-fx-background-color: transparent;" +
						"-fx-border-width: 1 1 1 1; -fx-border-color: rgba(255, 255, 255, .125);");
				Location location = gameMap.getMap()[x][y];
				if(location != null){
					if(location.getStar() != null){
						temp.getChildren().add(new ImageView(imageCache.getBlueStarS()));
					}
					if(location.getShips() != null){
						temp.getChildren().add(new ImageView(imageCache.getShipS()));
					}
				}
				mapView.add(temp, x, y);
				
				temp.setOnMouseClicked((event) -> {
					//System.out.println("Map clicked");
					StackPane source = (StackPane)event.getSource();
					int sourceX = GridPane.getColumnIndex(source);
					int sourceY = GridPane.getRowIndex(source);
					
					Location target = gameMap.getMap()[sourceX][sourceY];
					
					if(event.getButton() == MouseButton.SECONDARY || shipMoveFlag){
						System.out.println("MOVING OR RMB");
						shipMoveFlag = false;
						
						if(selected instanceof Ship && ((Ship) selected).getFaction() == gameController.getCurrentFaction()){
							System.out.println("before");
							try{
								source.getChildren().add(targetMarker);
							}catch(IllegalArgumentException e){
								//duplicate children attempted to add itself
							}
							System.out.println("source(" + sourceX + "," + sourceY + ")\n");
							
							((Ship)selected).setDestination(sourceX, sourceY);
							
							sideInfoLabels[2].setText("Destination: \t(" + sourceX + "," + sourceY + ")");
							
							Thread pathFinderThread = new Thread( () -> {
								if(selected instanceof WarpShip){
									gameController.moveWarpShip((WarpShip)selected);
									//System.out.println("Warp ship");
								} else if (selected instanceof JumpShip ){
									gameController.moveJumpShip((JumpShip)selected);
									//System.out.println("It's a jump ship!");
								}
								return;
							});
							pathFinderThread.start();
						}
						/*TODO
						 * Fix bug where warp ship can be caught in infinite loop between two threads
						 */
						
					} else if(event.getButton() == MouseButton.PRIMARY){
						
						if(selected == null){
							if(target != null){
								if(target.getStar() != null){
									select(target.getStar());
								} else if (target.getShips() != null){
									select(target.getShips()[0]);
								}
							}
						} else {
							if(target == null){
								select(null);
							} else if (selected.getCoordinates()[0] == sourceX && selected.getCoordinates()[1] == sourceY){
								if(selected instanceof Star && target.getShips() != null){
									select(target.getShips()[0]);
								} else if (selected instanceof Ship){
									int index = target.getShips().length; //default to break out of later blocks
									for(int i = 0; i < target.getShips().length; ++i){
										if(selected == target.getShips()[i]){
											index = i;
											break;
										}
									}
									if(index + 1 < target.getShips().length){
										select(target.getShips()[index + 1]);
									} else if (target.getStar() != null){
										select(target.getStar());
									} else {
										select(target.getShips()[0]);
									}
								}
							} else {
								if(target.getStar() != null){
									select(target.getStar());
								} else if (target.getShips() != null){
									select(target.getShips()[0]);
								} else {
									System.out.println("Another case?");
								}
							}
						}
					} //end primary mouse handling
				});
			}
		}
		
		return mapView;
	}

	public GameGUI(){
		imageCache = new ImageCache();
		imageCache.loadMenuImages();
		imageCache.loadGameImages();
		audioCache = new AudioCache();
		audioCache.loadGameAudio();
		
		gameMap = new Map();
		gameController = new GameController(gameMap, this, gameMap.getFactions());
		
		targetMarker = new ImageView(imageCache.getTarget());
		targetMarker.setMouseTransparent(true);
		
		shipMoveFlag = false;
	}
	
	/**
	 * Utility function to locate a node in a GridPane by a particular pair of coordinates
	 * 
	 * @param grid The GridPane to find the given coordinates in
	 * @param x X coordinate of desired Node
	 * @param y Y coordinate of desired Node
	 * @return Returns the desired Node
	 */
	private Node getNodeByCoordinate(GridPane grid, int x, int y){ //there has to be a better way to do this
		Node temp = null;
		for(Node node : grid.getChildren()){ 
			int nodeX = GridPane.getColumnIndex(node);
			int nodeY = GridPane.getRowIndex(node);
			if(x == nodeX && y == nodeY){
				temp = node;
				break;
			}
		}
		return temp;
	}
	
	public static void main(String[] args) {
	    launch(args);
	}

	
	public Label[] getSideInfoLabels() {
		return sideInfoLabels;
	}
}
