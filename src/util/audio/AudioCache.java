package util.audio;

import java.io.File;

import javafx.scene.media.Media;

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
