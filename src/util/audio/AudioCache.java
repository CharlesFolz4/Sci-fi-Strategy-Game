package util.audio;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import util.gui.images.ImageCache;

public class AudioCache {
	private String path = "src" + File.separator + "util" + File.separator + "audio" + File.separator;
	
	private Media mars;
	private Media uranus;
	
	public void loadMenuAudio(){
		mars = loadAudio("Mars.mp3");
		uranus = loadAudio("Uranus.mp3");
	}

	public void loadGameAudio() {
		mars = loadAudio("Mars.mp3");
		uranus = loadAudio("Uranus.mp3");
	}
	
	private Media loadAudio(String mediaName){
		Media media = null;
		
		//case for when program is run from a jar
		if(AudioCache.class.getResource("AudioCache.class").toString().substring(0, 3).equals("jar")){
			path = ImageCache.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path = path.substring(0, path.length()-4);
			path += File.separator + "util" + File.separator + "audio" + File.separator;
		}
		
		File mediaFile = new File(path + mediaName);
		media = new Media(mediaFile.toURI().toString());
		return media;
	}
	
	public Media getMars(){
		return mars;
	}
	
	public Media getUranus(){
		return uranus;
	}
	
}
