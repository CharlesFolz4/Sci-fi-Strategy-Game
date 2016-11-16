package util.gui;

import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Faction;
import util.GameController;
import util.Location;
import util.Map;
import util.Selectable;
import util.audio.AudioCache;
import util.gui.images.ImageCache;

//TODO: Add comprehensive javadoc
public class GameGUI extends Application{
	private ImageCache imageCache;
	private AudioCache audioCache;
	private Map gameMap;
	private GameController gameController;
	private Selectable selected;
	private ImageView targetMarker;
	
	private BorderPane root;
	private GridPane mapView;
	private ImageView sideMenuImage;
	private Label sideMenuTitle;
	private Label[] sideInfoLabels;
	private Label topLabel;
	private Label middleLabel;
	private Label bottomLabel;
	private StackPane cancelButton;
	private ScrollPane scrollPane;
	
	private boolean shipMoveFlag;
	
	private ArrayList<Node> colorable;
	
	
	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		root.setCenter(makeMapView());
		VBox sideMenu = makeSideMenu();
		sideMenu.prefHeightProperty().bind(root.heightProperty());
		root.setRight(sideMenu);
		
		root.setStyle("-fx-background-image: url(/util/gui/images/Star_Background.png)");
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/util/gui/css.css").toExternalForm();
		scene.getStylesheets().add(css); 
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(imageCache.getFlag());
		primaryStage.setTitle("PLACEHOLDER");
		//primaryStage.setFullScreen(true);
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		centerViewOn(gameController.getCurrentFaction().getCapital().getCoordinates()[0], gameController.getCurrentFaction().getCapital().getCoordinates()[1]);
		
		primaryStage.setOnCloseRequest((event) -> {
			System.exit(0);;
		});
		
//		KeyCode.
		//keyboard shortcuts
		scene.setOnKeyPressed(e -> {
		    switch(e.getCode()){
		    	case M:
		    	case TAB:
		    		//TODO: make a map popup
		    		break;
		    	case SPACE:
		    		//TODO: centers view on home planet/capital
//		    		centerViewOn(gameController.getCurrentFaction().getCapital().getCoordinates()[0], gameController.getCurrentFaction().getCapital().getCoordinates()[1]);
		    		break;
		    	case ESCAPE:
		    		//TODO: Opens a menu thing
		    		break;
		    	case BACK_SPACE:
		    		//TODO: Sets center as the map if in shipyard or somewhere else
		    		
		    		break;
	    		default: System.out.print(e.getCharacter().toString());
		    }
		    e.consume();
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
		VBox root = new VBox(10);
		
		VBox infoBox = new VBox();
		infoBox.setAlignment(Pos.CENTER);
		sideMenuTitle = new Label(gameController.getCurrentFaction().getName());
		sideMenuTitle.setStyle("-fx-font-size: 40; -fx-text-fill: white;");
		sideMenuTitle.setAlignment(Pos.CENTER);
		sideMenuImage = new ImageView(imageCache.getGalaxies()[0]);
		Pane imagePane = new Pane(sideMenuImage);
		sideMenuImage.setX(1);
		sideMenuImage.setY(1); //because JavaFX is stupid with alignments and Panes
		imagePane.setStyle("-fx-border-width: 1; -fx-border-color: " + gameController.getCurrentFaction().getColor());
		colorable.add(imagePane);
		
		infoBox.getChildren().addAll(sideMenuTitle, imagePane);
		
		sideInfoLabels = new Label[5];
		for(int i = 0; i < sideInfoLabels.length; ++i){
			sideInfoLabels[i] = new Label();
			sideInfoLabels[i].setStyle("-fx-text-fill: white;");
			sideInfoLabels[i].setAlignment(Pos.CENTER_LEFT);
		}
		sideInfoLabels[0].setText("Income: \t\t" + gameController.getCurrentFaction().getIncome());
		sideInfoLabels[1].setText("Treasury: \t\t" + gameController.getCurrentFaction().getTreasury());
		sideInfoLabels[2].setText("Population: \t" + gameController.getCurrentFaction().getPopulation());
		sideInfoLabels[3].setText("Planets: \t\t" + gameController.getCurrentFaction().getPlanetCount());
		sideInfoLabels[4].setText("Systems: \t\t" + gameController.getCurrentFaction().getSystemCount());
		infoBox.getChildren().addAll(sideInfoLabels);
		
		VBox actionBox = new VBox(20);
		
		ImageView[] highlights = new ImageView[4];
		for(int i = 0; i < highlights.length; ++i){
			highlights[i] = new ImageView(imageCache.getMenuButtonHighlight());
			imageCache.recolor(highlights[i], gameController.getCurrentFaction().getColor());
			colorable.add(highlights[i]);
			highlights[i].setVisible(false);
		}
		
		StackPane topButton = new StackPane();
		ImageView topButtonImage = new ImageView(imageCache.getMenuButton());
		imageCache.recolor(topButtonImage, gameController.getCurrentFaction().getColor());
		colorable.add(topButtonImage);
		topButton.getChildren().addAll(topButtonImage, highlights[0]);
		topLabel = new Label("Empire");
		topLabel.setStyle("-fx-font-size: 40; -fx-text-fill: white;");
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
			topAction();
		});
		
		StackPane middleButton = new StackPane();
		ImageView middleButtonImage = new ImageView(imageCache.getMenuButton());
		imageCache.recolor(middleButtonImage, gameController.getCurrentFaction().getColor());
		colorable.add(middleButtonImage);
		middleButton.getChildren().addAll(middleButtonImage, highlights[1]);
		middleLabel = new Label("Foreign Affairs");
		middleLabel.setStyle("-fx-font-size: 40; -fx-text-fill: white;");
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
		middleButton.setOnMouseClicked((event) -> {
			middleAction();
		});
		
		StackPane bottomButton = new StackPane();
		ImageView bottomButtonImage = new ImageView(imageCache.getMenuButton());
		imageCache.recolor(bottomButtonImage, gameController.getCurrentFaction().getColor());
		colorable.add(bottomButtonImage);
		bottomButton.getChildren().addAll(bottomButtonImage, highlights[2]);
		bottomLabel = new Label("Science");
		bottomLabel.setStyle("-fx-font-size: 40; -fx-text-fill: white;");
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
		bottomButton.setOnMouseClicked((event) -> {
			bottomAction();
		});
		
		cancelButton = new StackPane();
		cancelButton.setVisible(false);
		ImageView cancelButtonImage = new ImageView(imageCache.getMenuButton());
		imageCache.recolor(cancelButtonImage, gameController.getCurrentFaction().getColor());
		colorable.add(cancelButtonImage);
		cancelButton.getChildren().addAll(cancelButtonImage, highlights[3]);
		Label cancelLabel = new Label("Done");
		cancelLabel.setStyle("-fx-font-size: 40;  -fx-text-fill: white;");
		cancelButton.getChildren().add(cancelLabel);
		cancelButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[3].setVisible(true);
	        }
	    });
		cancelButton.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[3].setVisible(false);
			}
		});
		cancelButton.setOnMouseClicked((event) -> {
			this.root.setCenter(makeMapView());
			cancelButton.setVisible(false);
		});
		
		actionBox.getChildren().addAll(topButton, middleButton, bottomButton, cancelButton);
		
		root.setPadding(new Insets(25, 25, 0, 25));
		root.setStyle("-fx-background-color: rgba(16, 16, 16, .75);");
		root.getChildren().addAll(infoBox, actionBox);
		return root;
	}
	
	
	private void topAction() {
		if(selected instanceof Star){
			System.out.println(((Star) selected).getFaction());
		}
		if(selected instanceof Star && ((Star)selected).getFaction() == null && gameMap.getMap()[selected.getCoordinates()[0]][selected.getCoordinates()[1]].getShips() != null){
			//Colonization!
			for(Ship ship : gameMap.getMap()[selected.getCoordinates()[0]][selected.getCoordinates()[1]].getShips()){
				if(ship.getFaction().equals(gameController.getCurrentFaction())){
					((Star)selected).changePopulation(ship.getCargoPeople());
					ship.setCargoPeople(0);
					((Star)selected).setFaction(gameController.getCurrentFaction());
					gameController.getCurrentFaction().addStar((Star) selected);
					Node temp = getNodeByCoordinate(mapView, selected.getCoordinates()[0], selected.getCoordinates()[1]);
					temp.setStyle("-fx-background-color: transparent; -fx-border-width: 1 1 1 1; -fx-border-color: " + gameController.getCurrentFaction().getColor());
				}
			}
			
		} else if(selected instanceof Star && !((Star)selected).getFaction().equals(gameController.getCurrentFaction())){
			//invasion
			if(gameMap.getMap()[selected.getCoordinates()[0]][selected.getCoordinates()[1]].getShips() != null){
				
				System.out.println("Invasion!");
				int soldiers = 0;
				for(Ship ship : gameMap.getMap()[selected.getCoordinates()[0]][selected.getCoordinates()[1]].getShips()){
					if(ship.getFaction().equals(gameController.getCurrentFaction())){
						soldiers += ship.getSoldiers();
						ship.setSoldiers(0);
					}
				}
				gameController.invadeSystem((Star) selected, soldiers);
				select(selected);
				if(((Star)selected).getFaction().equals(gameController.getCurrentFaction())){
					//invasion succeeded
					Node temp = getNodeByCoordinate(mapView, selected.getCoordinates()[0], selected.getCoordinates()[1]);
					temp.setStyle("-fx-background-color: transparent; -fx-border-width: 1 1 1 1; -fx-border-color: " + gameController.getCurrentFaction().getColor());
				}
			}
		}else if(selected instanceof Ship && !shipMoveFlag){
			shipMoveFlag = true;
		} else if (selected instanceof Ship && shipMoveFlag){
			shipMoveFlag = false;
		} 
		//TODO: finish implementing
	}
	
	private void middleAction(){
		//TODO: implement 
		if(selected instanceof Star && ((Star) selected).getFaction().equals(gameController.getCurrentFaction())){

			cancelButton.setVisible(true);
			root.setCenter(new OrbitingShipPane((Star)selected, this, imageCache,  gameMap.getMap()[selected.getCoordinates()[0]][selected.getCoordinates()[1]].getShips()));
			
		}
	}
	
	private void bottomAction(){
		
		if(selected instanceof Star && ((Star) selected).getFaction().equals(gameController.getCurrentFaction())){

			cancelButton.setVisible(true);
			root.setCenter(new Shipyard(imageCache, (Star)selected, gameController.getCurrentFaction(), this));
			
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
			centerViewOn(selected.getCoordinates()[0], selected.getCoordinates()[1]);
			
			sideMenuImage.setImage(imageCache.getShip());
			topLabel.setText("Move");
			middleLabel.setText("Attack");
			bottomLabel.setText("Patrol");
			sideMenuTitle.setText(((Ship) selected).getName());
			
			sideInfoLabels[0].setText("HP: \t\t" + ((Ship) selected).getCurrentHealth() + "/" + ((Ship) selected).getMaxHealth());
			sideInfoLabels[2].setText("Destination: \t(" + ((Ship) selected).getDestination()[0] + "," + ((Ship) selected).getDestination()[1] + ")");
			sideInfoLabels[3].setText("Armament: \t\t" + Arrays.toString(((Ship) selected).getWeapons()));
			sideInfoLabels[4].setText("Defenses: \t\t" + Arrays.toString(((Ship) selected).getDefenses()));
			if( selected instanceof WarpShip){
				sideInfoLabels[1].setText("Movement: \t\t" + ((WarpShip)selected).getMovementRemaining() +"/" + ((WarpShip)selected).getSpeed());
			} else {
				sideInfoLabels[1].setText("Jump Radius: \t" + ((JumpShip)selected).getJumpRadius());
				
			}
			
		} else if (selected instanceof Star){
			sideMenuImage.setImage(imageCache.getRedStar());
			sideMenuTitle.setText(((Star) selected).getName());
			
			if(((Star) selected).getFaction() == null){
				topLabel.setText("Colonize");
				middleLabel.setText("Survey");
				bottomLabel.setText("Terraform");
				
				sideInfoLabels[0].setText("Population cap: \t" + ((Star) selected).getPopCap());
				sideInfoLabels[1].setText("Population: \t\tUninhabited");
				sideInfoLabels[2].setText("Industry: \t\tNone");
				sideInfoLabels[3].setText("Shipyard: \t\t" + ((Star) selected).getShipyardLevel());
				sideInfoLabels[4].setText("Planets: \t\t" + ((Star) selected).getPlanets().length);
			} else if (((Star) selected).getFaction() == gameController.getCurrentFaction()){
				topLabel.setText("Manage");
				middleLabel.setText("Ships");
				bottomLabel.setText("Shipyard");
				
				sideInfoLabels[0].setText("Income: \t\t" + ((Star) selected).getIncome());
				sideInfoLabels[1].setText("Population: \t" + ((Star) selected).getPopulation());
				sideInfoLabels[2].setText("Industry: \tN/A");
				sideInfoLabels[3].setText("Shipyard: \t\t" + ((Star) selected).getShipyardLevel());
				sideInfoLabels[4].setText("Planets: \t\t" + ((Star) selected).getPlanets().length);
			} else if (((Star) selected).getFaction() != gameController.getCurrentFaction()){
				topLabel.setText("Invade");
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

	//TODO: bug wherein target circle doesn't disappear during turnchange when a shipsprite is on same square
	public void updateDisplay(){
		StackPane temp;
		for(int x = 0; x < gameMap.getDimensions()[0]; ++x){
			for(int y = 0; y < gameMap.getDimensions()[1]; ++y){
				//System.out.println("testing " + x + "," + y);
				if( gameMap.getMap()[x][y] == null ){ 
					if( !((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().isEmpty() ){
				
						if(selected != null && selected instanceof Ship){
							int[] coords = {x,y};
							
							if(!Arrays.equals(((Ship)selected).getDestination(), coords)){
								((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().clear();
							}
						} else {
							((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().clear();
						}
					}
				} else if (gameMap.getMap()[x][y].getShips() != null){
					if((((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().isEmpty() || ((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().contains(targetMarker))){
						
						ImageView shipSprite = new ImageView(imageCache.getShipS());
						imageCache.recolor(shipSprite, gameMap.getMap()[x][y].getShips()[0].getFaction().getColor());
						((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().add(shipSprite);
					
					}
				} else if (gameMap.getMap()[x][y].getShips() == null && (((StackPane)getNodeByCoordinate(mapView, x, y)).getChildren().size() > 1)){
					temp = (StackPane)getNodeByCoordinate(mapView, x, y);
					temp.getChildren().clear();
					if(gameMap.getMap()[x][y].getStar() != null){
						temp.getChildren().add(new ImageView(imageCache.getRedStarS()));
					}
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
	
	public Node makeMapView() {
		StackPane subRoot = new StackPane();
		subRoot.setStyle("-fx-background-color: transparent; -fx-border-width: 4; -fx-border-color:  " + gameController.getCurrentFaction().getColor());
		colorable.add(subRoot);
		subRoot.prefHeightProperty().bind(root.heightProperty());
		
		BorderPane utilPane = new BorderPane();
		HBox bottomRoot = new HBox();
		bottomRoot.setAlignment(Pos.BASELINE_RIGHT);
		bottomRoot.prefWidthProperty().bind(utilPane.widthProperty());

		
		StackPane nextButton = new StackPane();
		Label nextLabel = new Label("Next");
		nextLabel.setStyle("-fx-font-size: 28; -fx-text-fill: white;");
		ImageView nextHighlight = new ImageView(imageCache.getButtonBRHighlight());
		imageCache.recolor(nextHighlight, gameController.getCurrentFaction().getColor());
		colorable.add(nextHighlight);
		nextHighlight.setVisible(false);
		ImageView nextButtonImage = new ImageView(imageCache.getButtonBR());
		colorable.add(nextButtonImage);
		imageCache.recolor(nextButtonImage, gameController.getCurrentFaction().getColor());
		nextButton.getChildren().addAll(nextButtonImage, nextLabel, nextHighlight);
		nextButton.setOnMouseEntered((event) -> {
        	nextHighlight.setVisible(true);
	    });
		nextButton.setOnMouseExited((event) -> {
			nextHighlight.setVisible(false);
		});
		nextButton.setOnMouseClicked((event) -> {
			selected = null;
			updateDisplay();
			gameController.endFactionTurn();
			updateSideMenu();
			recolor();
			centerViewOn(gameController.getCurrentFaction().getCapital().getCoordinates()[0], gameController.getCurrentFaction().getCapital().getCoordinates()[1]);
		});
		
		bottomRoot.getChildren().addAll(nextButton);
		utilPane.setBottom(bottomRoot);
		utilPane.setPickOnBounds(false);
		bottomRoot.setStyle("-fx-background-color: transparent");

		
		
		
		scrollPane = new ScrollPane();
		mapView = new GridPane();
		mapView.setStyle("-fx-background-color: transparent");
		
		StackPane temp;
		for(int x = 0; x < gameMap.getDimensions()[0]; ++x){
			for(int y = 0; y < gameMap.getDimensions()[1]; ++y){
				temp = new StackPane();
				temp.setPrefSize(102, 102);
				temp.setMinSize(102, 102);
				temp.setStyle("-fx-background-color: transparent;" +
						"-fx-border-width: 1 1 1 1; -fx-border-color: rgba(255, 255, 255, .125);");
				Location location = gameMap.getMap()[x][y];
				if(location != null){
					if(location.getStar() != null){
						temp.getChildren().add(new ImageView(imageCache.getRedStarS()));
						if(location.getStar().getFaction() != null){
							temp.setStyle("-fx-background-color: transparent; -fx-border-width: 1 1 1 1; -fx-border-color: " + location.getStar().getFaction().getColor());
						}
					}
					if(location.getShips() != null){
						temp.getChildren().add(imageCache.recolor(new ImageView(imageCache.getShipS()), location.getShips()[0].getFaction().getColor()));
					}
				}
				mapView.add(temp, x, y);
				
				temp.setOnMouseClicked((event) -> {
					StackPane source = (StackPane)event.getSource();
					int sourceX = GridPane.getColumnIndex(source);
					int sourceY = GridPane.getRowIndex(source);
					
					Location target = gameMap.getMap()[sourceX][sourceY];
					
					if(event.getButton() == MouseButton.SECONDARY || shipMoveFlag){
//						System.out.println("MOVING OR RMB");
						shipMoveFlag = false;
						
						if(selected instanceof Ship && ((Ship) selected).getFaction() == gameController.getCurrentFaction()){
							try{
								source.getChildren().add(targetMarker);
							}catch(IllegalArgumentException e){
								//duplicate children attempted to add itself
							}
//							System.out.println("source(" + sourceX + "," + sourceY + ")\n");
							
							((Ship)selected).setDestination(sourceX, sourceY);
							
							sideInfoLabels[2].setText("Destination: \t(" + sourceX + "," + sourceY + ")");


							if(gameMap.getMap()[sourceX][sourceY] != null){
								if(gameMap.getMap()[sourceX][sourceY].getShips() != null){
									for(Ship locationShip : gameMap.getMap()[sourceX][sourceY].getShips()){
										if(!locationShip.getFaction().equals(((Ship) selected).getFaction())){
											if(Math.abs(((Ship)selected).getCoordinates()[0] - sourceX) <= 1 && Math.abs(((Ship)selected).getCoordinates()[1] - sourceY) <= 1){
												//in range to attack, and hostile ship is present
												Thread fightThread = new Thread( () -> {
													gameController.attackShip((Ship) selected, locationShip);
													return;
												});
												fightThread.start();
											}
										}
									}
								}
							}
							
							Thread pathFinderThread = new Thread( () -> {
								if(selected instanceof WarpShip){
									gameController.moveWarpShip((WarpShip)selected);
								} else if (selected instanceof JumpShip ){
									gameController.moveJumpShip((JumpShip)selected);
								}
								return;
							});
							pathFinderThread.start();
						}
						//TODOFix bug where warp ship can be caught in infinite loop between two threads
						 
						
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
		scrollPane.setContent(mapView);
		scrollPane.setPannable(true);
		scrollPane.setStyle("-fx-background-color: transparent");
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		subRoot.getChildren().addAll(scrollPane, utilPane);
		
		return subRoot;
	}
	
	public void recolor(){
		char[] style;
		char[] color = gameController.getCurrentFaction().getColor().toCharArray();
		for(Node node : colorable){
			style = node.getStyle().toCharArray();
			if(style.length > 0){
				for(int i = 0; i < 6; ++i){
					style[style.length-(i+1)] = color[color.length-(i+1)]; 
				}
				node.setStyle(new String(style));
			} else { //it's an image
				imageCache.recolor((ImageView)node, gameController.getCurrentFaction().getColor());
			}
		}
	}
	
	public GameGUI(Map gameMap){
		this.gameMap = gameMap;
		
		imageCache = new ImageCache();
		imageCache.loadMenuImages();
		imageCache.loadGameImages();
//		audioCache = new AudioCache();
//		audioCache.loadGameAudio();
		
		gameController = new GameController(gameMap, this, gameMap.getFactions().toArray(new Faction[0]));
		
		targetMarker = new ImageView(imageCache.getTarget());
		targetMarker.setMouseTransparent(true);
		colorable = new ArrayList<Node>();
		
		shipMoveFlag = false;
	}
	
	//default test constructor
	public GameGUI(){
		imageCache = new ImageCache();
		imageCache.loadMenuImages();
		imageCache.loadGameImages();
//		audioCache = new AudioCache();
//		audioCache.loadGameAudio();
		
		double[]  galacticProbabilities = {.025, .85, .1875};
		String[]  factionNames = {"Alpha Association", "Delta Dominion"};
		boolean[] factionUsesJump = {true, false};
		String[]  factionColors = {"#004182", "#66ff00"};
		
		gameMap = new Map(20, 20, galacticProbabilities, factionNames, factionUsesJump, factionColors);
		gameController = new GameController(gameMap, this, gameMap.getFactions().toArray(new Faction[0]));
		
		targetMarker = new ImageView(imageCache.getTarget());
		targetMarker.setMouseTransparent(true);
		colorable = new ArrayList<Node>();
		
		shipMoveFlag = false;
	}
	
	private void centerViewOn(double x, double y){
		double viewportWidth    = scrollPane.getViewportBounds().getWidth();
	    double maxHscrollPixels = mapView.getWidth() - viewportWidth;
	    double hscrollPixels    = (x + 0.5) * mapView.getWidth() / gameMap.getDimensions()[0] - viewportWidth / 2;
	    scrollPane.setHvalue(hscrollPixels / maxHscrollPixels);
	    
	    double viewportHeight   = scrollPane.getViewportBounds().getHeight();
	    double maxVscrollPixels = mapView.getHeight() - viewportHeight;
	    double vscrollPixels    = (y + 0.5) * mapView.getHeight() / gameMap.getDimensions()[1] - viewportHeight / 2;
	    scrollPane.setVvalue(vscrollPixels / maxVscrollPixels);
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

	
	public Selectable getSelected() {
		return selected;
	}
	
	public GameController getGameController(){
		return gameController;
	}
	
	public StackPane getCancelButton(){
		return cancelButton;
	}
	
	public BorderPane getRoot(){
		return root;
	}
}
