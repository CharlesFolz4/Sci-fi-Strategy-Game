package util.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class LoadGamePane extends BorderPane{
	
	
	public LoadGamePane(){
		Label title = new Label("Load Game");
		title.setStyle("-fx-font-size: 36");
		title.setTextFill(Color.WHITE);
		BorderPane.setAlignment(title, Pos.CENTER);
		this.setTop(title);
	}
	
	
}
