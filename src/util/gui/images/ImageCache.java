package util.gui.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;

public class ImageCache {
	private String path = "src" + File.separator + "util" + File.separator + "gui" + File.separator + "images" + File.separator;
	
	private Image flag;
	private Image background;
	private Image menuButton;
	private Image menuButtonHighlight;
	private Image buttonBR;
	private Image buttonBRHighlight;
	private Image buttonBL;
	
	private Image[] galaxies;
	private Image blueStar;
	private Image blueStarS;
	private Image ship;
	private Image shipS;
	private Image ship1;
	
	public void loadGameImages(){
		background = loadImage("Star_Background.png");
		blueStar   = loadImage("Star_Blue.png");
		blueStarS  = loadImage("Star_Blue_S.png");
		shipS      = loadImage("Ship_Icon.png");
		ship       = loadImage("Ship_Blueprints_S.png");
		ship1      = loadImage("Ship_Blueprints1.png");
	}
	
	public void loadMenuImages(){
		flag        = loadImage("flag.png");
		background  = loadImage("Whirlpool_Galaxy.png");
		menuButton  = loadImage("Menu_Button.png");
		buttonBR    = loadImage("ButtonBR.png");
		menuButtonHighlight = loadImage("Button_Highlight.png");
		buttonBRHighlight   = loadImage("ButtonBR_Highlight.png");
		
		galaxies[0] = loadImage("Spiral_Galaxy_S.png");
		galaxies[1] = loadImage("Many_Spiral_Galaxy_S.png");
		galaxies[2] = loadImage("Lenticular_Galaxy_S.png");
		galaxies[3] = loadImage("Irregular_Galaxy_S.png");
	}
	
	public void clearMenuImages(){
		flag = null;
		background = null;
		menuButton = null;
		menuButtonHighlight = null;
		buttonBR = null;
		buttonBRHighlight = null;
		galaxies = null;
	}
	
	private Image loadImage(String imageName){
		Image image = null;
		//path = ImageCache.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		//path += File.separator + "util" + File.separator + "gui" + File.separator + "images" + File.separator;
		//System.out.println(path);
		File imageFile    = new File(path + imageName);
		InputStream input = null;
		try{
			input = new FileInputStream(imageFile);
			image = new Image(input);
			input.close();
		}catch(FileNotFoundException e){
			System.out.println("File not found: " + imageName);
			//e.printStackTrace();
		}catch(IOException e){
			System.out.println("General exception");
			//e.printStackTrace();
		}
		
		return image;
	}
	
	public Image getShip1(){
		return ship1;
	}
	
	public Image getShip(){
		return ship;
	}
	
	public Image getShipS(){
		return shipS;
	}
	
	public Image getBlueStar(){
		return blueStar;
	}
	
	public Image getBlueStarS(){
		return blueStarS;
	}
	
	public Image getFlag(){
		return flag;
	}
	
	public Image getBackground(){
		return background;
	}
	
	public Image getMenuButton(){
		return menuButton;
	}
	
	public Image getMenuButtonHighlight(){
		return menuButtonHighlight;
	}
	
	public Image getButtonBR(){
		return buttonBR;
	}
	
	public Image getButtonBRHighlight(){
		return buttonBRHighlight;
	}
	
	public Image getButtonBL(){
		return buttonBL;
	}
	
	public Image[] getGalaxies(){
		return galaxies;
	}
	
	public ImageCache(){
		galaxies = new Image[4];
	}
}
