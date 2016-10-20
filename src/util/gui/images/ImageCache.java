package util.gui.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ImageCache {
	private String path = "bin" + File.separator + "util" + File.separator + "gui" + File.separator + "images" + File.separator;
	
	private Image flag;
	private Image background;
	private Image menuButton;
	private Image menuButtonHighlight;
	private Image buttonBR;
	private Image buttonBRHighlight;
	private Image buttonBL;
	private Image addFactionButton;
	private Image deleteFactionButton;
	private Image factionButtonHighlight;
	
	private Image plus;
	private Image minus;
	private Image loadShipButton;
	private Image loadShipHighlight;
	private Image saveShipButton;
	private Image saveShipHighlight;
	private Image buildShipButton;
	private Image buildShipButtonHighlight;
	private Image cancelShipyardButton;
	private Image cancelShipyardHighlight;
	
	private Image[] galaxies;
	private Image blueStar;
	private Image blueStarS;
	private Image redStar;
	private Image redStarS;
	private Image ship;
	private Image shipS;
	private Image ship1;
	private Image target;
	
	//TODO note- background loads from inline css fine
	public void loadShipyardImages(){
		plus  = loadImage("plus.png");
		minus = loadImage("minus.png");
		
		buildShipButton          = loadImage("Build_Ship.png");
		buildShipButtonHighlight = loadImage("Build_Ship_Highlight.png");
		cancelShipyardButton     = loadImage("Cancel_Shipyard.png");
		cancelShipyardHighlight  = loadImage("Cancel_Shipyard_Highlight.png");
	}
	
	public void clearShipyardImages(){
		plus  = null;
		minus = null;
		
		loadShipButton    = null;
		saveShipButton    = null;
		loadShipHighlight = null;
		saveShipHighlight = null;
	}
	
	public void loadOrbitalShipsImages(){
		plus  = loadImage("plus.png");
		minus = loadImage("minus.png");
	}
	
	public void clearOrbitalShipsImages(){
		plus  = null;
		minus = null;
	}
	
	public void loadGameImages(){
		background = loadImage("Star_Background.png");
		redStar    = loadImage("Star_Red.png");
		redStarS   = loadImage("Star_Red_S.png");
		shipS      = loadImage("Ship_Icon.png");
		ship       = loadImage("Ship_Blueprints_S.png");
		ship1      = loadImage("Ship_Blueprints1.png");
		target     = loadImage("target.png");
	}
	
	public void loadMenuImages(){
		flag        = loadImage("flag.png");
		background  = loadImage("Whirlpool_Galaxy.png");		
		menuButton  = loadImage("Menu_Button.png");
		buttonBR    = loadImage("ButtonBR.png");
		menuButtonHighlight = loadImage("Button_Highlight.png");
		buttonBRHighlight   = loadImage("ButtonBR_Highlight.png");
		addFactionButton       = loadImage("AddFactionButton.png");
		deleteFactionButton    = loadImage("deleteFactionButton.png");
		factionButtonHighlight = loadImage("FactionButtonHighlight.png");
		
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
		addFactionButton = null;
		deleteFactionButton = null;
		factionButtonHighlight = null;
		galaxies = null;
	}
	
	private Image loadImage(String imageName){
		Image image       = null;
		InputStream input = null;
		File imageFile    = null;
		
		//case for when program is run from a jar
		if(ImageCache.class.getResource("ImageCache.class").toString().substring(0, 3).equals("jar")){
			path = ImageCache.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path = path.substring(0, path.length()-4);
			path += File.separator + "util" + File.separator + "gui" + File.separator + "images" + File.separator;
		}
		
//		System.out.println(path + imageName);
		imageFile = new File(path + imageName);
		try{
			input = new FileInputStream(imageFile);
			image = new Image(input);
			input.close();
		}catch(FileNotFoundException e){
			System.out.println("File not found: " + imageName + " at " + imageFile.getAbsolutePath() + "\n");
//			e.printStackTrace();
		}catch(IOException e){
			System.out.println("General exception");
//			e.printStackTrace();
		}
		return image;
	}
	
	public ImageView recolor(ImageView image, String hexColor){
		int red   = Integer.parseInt(String.copyValueOf(hexColor.toCharArray(), 1, 2), 16);
		int green = Integer.parseInt(String.copyValueOf(hexColor.toCharArray(), 3, 2), 16);
		int blue  = Integer.parseInt(String.copyValueOf(hexColor.toCharArray(), 5, 2), 16);
		
//		System.out.println(red + "," + green + "," + blue);
		Color recolor = new Color(red/255.0, green/255.0, blue/255.0, 0);
//		System.out.println(hexColor + " -> " + recolor.toString().toUpperCase());
		
		ColorAdjust adjust = new ColorAdjust();
		adjust.setHue(((recolor.getHue()+180)%360)/180 -1 );
		adjust.setSaturation((recolor.getSaturation()) );
		image.setEffect(adjust);
		return image;
	}

	public Image getAddFactionButton(){
		return addFactionButton;
	}
	
	public Image getDeleteFactionButton(){
		return deleteFactionButton;
	}
	
	public Image getFactionButtonHighlight(){
		return factionButtonHighlight;
	}
	
	public Image getCancelShipyardButton(){
		return cancelShipyardButton;
	}
	
	public Image getCancelShipyardHighlight(){
		return cancelShipyardHighlight;
	}
	
	public Image getBuildShipButton(){
		return buildShipButton;
	}
	
	public Image getBuildShipHighlight(){
		return buildShipButtonHighlight;
	}
	
	public Image getMinus(){
		return minus;
	}
	
	public Image getPlus(){
		return plus;
	}

	public Image getLoadShipButton(){
		return loadShipButton;
	}
	public Image getLoadShipHighlight(){
		return loadShipHighlight;
	}
	public Image getSaveShipButton(){
		return saveShipButton;
	}
	public Image getSaveShipHighlight(){
		return saveShipHighlight;
	}
	
	public Image getTarget(){
		return target;
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
	
	public Image getRedStar(){
		return redStar;
	}
	
	public Image getRedStarS(){
		return redStarS;
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
