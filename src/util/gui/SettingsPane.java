package util.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class SettingsPane extends BorderPane{
	
	public SettingsPane(){
		Label title = new Label("Settings");
		title.setStyle("-fx-font-size: 36");
		title.setTextFill(Color.WHITE);
		BorderPane.setAlignment(title, Pos.CENTER);
		this.setTop(title);
		
		
		
		
		
	}
	
	
	
}
