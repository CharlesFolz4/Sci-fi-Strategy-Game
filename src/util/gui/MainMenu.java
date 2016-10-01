package util.gui;

import util.audio.AudioCache;
import util.gui.images.ImageCache;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu extends Application{
	ImageCache imageCache;
	AudioCache audioCache;
	int i = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		imageCache = new ImageCache();
		imageCache.loadMenuImages();
		primaryStage.getIcons().add(imageCache.getFlag());
		audioCache = new AudioCache();
		audioCache.loadMenuAudio();
		MediaPlayer mediaPlayer = new MediaPlayer(audioCache.getUranus());
		mediaPlayer.play();
		
		BorderPane subRoot = new BorderPane();
		subRoot.setStyle("-fx-background-color: transparent");
		subRoot.setRight(makeMenuButtons(subRoot));
		
		StackPane root = new StackPane();
		root.getChildren().addAll(new ImageView(imageCache.getBackground()), subRoot);
		
		primaryStage.setScene(new Scene(root, 1920, 1080));
		primaryStage.setTitle("PLACEHOLDER");
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			System.exit(0);;
		});
	}

	private Node makeMenuButtons(BorderPane subRoot) {
		VBox root = new VBox(20);
		
		ImageView[] highlights = new ImageView[5];
		for(int i = 0; i < highlights.length; ++i){
			highlights[i] = new ImageView(imageCache.getMenuButtonHighlight());
			highlights[i].setVisible(false);
		}
		
		StackPane newGame  = new StackPane();
		newGame.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		newGame.getChildren().add(highlights[0]);
		Label newGameLabel = new Label("New Game");
		newGameLabel.setStyle("-fx-font-size: 40;");
		newGameLabel.setTextFill(Color.WHITE);
		newGame.getChildren().add(newGameLabel);
		Tooltip.install(newGame, new Tooltip("Dev note: Partially Implemented"));
		newGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[0].setVisible(true);
	        }
	    });
		newGame.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[0].setVisible(false);
			}
		});
		newGame.setOnMouseClicked((event) -> {
			NewGamePane pane = new NewGamePane(imageCache);
			//pane.setPadding(new Insets(15));
			pane.prefHeightProperty().bind(subRoot.heightProperty());
			pane.prefWidthProperty().bind(subRoot.widthProperty());
			subRoot.setCenter(pane);
		});
		
		StackPane loadGame = new StackPane();
		loadGame.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		loadGame.getChildren().add(highlights[1]);
		Label loadGameLabel = new Label("Load Game");
		loadGameLabel.setStyle("-fx-font-size: 40;");
		loadGameLabel.setTextFill(Color.WHITE);
		loadGame.getChildren().add(loadGameLabel);
		Tooltip.install(loadGame, new Tooltip("Dev note: Not Yet Implemented"));
		loadGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[1].setVisible(true);
	        }
	    });
		loadGame.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[1].setVisible(false);
			}
		});
		loadGame.setOnMouseClicked((event) -> {
			Pane temp = new Pane();
			temp.setPadding(new Insets(15));
			temp.getChildren().add(new LoadGamePane());
			subRoot.setCenter(temp);
		});
		
		StackPane settings = new StackPane();
		settings.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		settings.getChildren().add(highlights[2]);
		Label settingsLabel = new Label("Settings");
		settingsLabel.setStyle("-fx-font-size: 40;");
		settingsLabel.setTextFill(Color.WHITE);
		settings.getChildren().add(settingsLabel);
		Tooltip.install(newGame, new Tooltip("Dev note: Not Yet Implemented"));
		settings.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[2].setVisible(true);
	        }
	    });
		settings.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[2].setVisible(false);
			}
		});
		settings.setOnMouseClicked((event) -> {
			Pane temp = new Pane();
			temp.setPadding(new Insets(15));
			temp.getChildren().add(new SettingsPane());
			subRoot.setCenter(temp);
		});
		
		StackPane exit 	= new StackPane();
		exit.getChildren().addAll(new ImageView(imageCache.getMenuButton()));
		exit.getChildren().add(highlights[3]);
		Label exitLabel = new Label("Exit");
		exitLabel.setStyle("-fx-font-size: 40;");
		exitLabel.setTextFill(Color.WHITE);
		exit.getChildren().add(exitLabel);
		Tooltip.install(newGame, new Tooltip("Exits to desktop"));
		exit.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	        	highlights[3].setVisible(true);
	        }
	    });
		exit.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				highlights[3].setVisible(false);
			}
		});
		exit.setOnMouseClicked((event) -> {
			System.exit(0);
		});
		
		root.setPrefWidth(400);
		root.setPadding(new Insets(100, 25, 100, 25));
		root.setStyle("-fx-background-color: rgba(16, 16, 16, .75);" +
					  "-fx-border-width: 0 0 0 4; -fx-border-color: #D0D0E0;");
		root.getChildren().addAll(newGame, loadGame, settings, exit);
		return root;
	}

	
	
	public static void main(String[] args) {
	    launch(args);
	}

}
