package util.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *CopyRight: Uber leet sauce Daniel Gesla
 * email: Daniel@Gesla.net
 * @author Arcanist
 */
public class Grid extends Pane {
    List<Line> hLines;
    List<Line> vLines;
    double gridWidth, gridHeight;
    double scaler;
    
    @Override
    public ObservableList getChildren(){
        return super.getChildren();
    }
    
    public Grid(double gwidth, double gheight, double scale){
        super();
        
        scaler     = scale;
        gridWidth  = gwidth;
        gridHeight = gheight;

        //Should be called each time grid is resized.
        render();
    }
    //Renders a grid of lines based on width, height, and distance between lines.
    private void render(){
        //Renders array of lines.
        hLines = new ArrayList<>((int)gridWidth / (int)scaler);
        vLines = new ArrayList<>((int)gridHeight / (int)scaler);
        for (int i = 0; i < gridHeight / scaler + 1; i++){
            hLines.add(new Line(0, i* scaler, gridWidth, i * scaler));
            hLines.get(i).setStroke(Color.LIGHTGRAY);
        }
        for (int i = 0; i < gridWidth / scaler + 1; i++){
            vLines.add(new Line(i * scaler, 0, i* scaler, gridHeight));
            vLines.get(i).setStroke(Color.LIGHTGRAY);
        }
        getChildren().clear();
        getChildren().addAll(hLines);
        getChildren().addAll(vLines);
    }
    
    public void setWidth(int width){
        gridWidth = width;
        render();
    }
    public void setHeight(int height){
        gridHeight = height;
        render();
    }
    public void setMultiplier(int multi){
        scaler = multi;
        render();
    }
    public void setBounds(int width, int height){
        gridWidth = width;
        gridHeight = height;
        render();
    }
}
