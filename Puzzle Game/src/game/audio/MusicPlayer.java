package game.audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
	
	public void playMusic(String musicFileName) {
		
		try {
			File musicFile = new File("res/audio/" + musicFileName + ".wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			if (musicFileName.equals("Background")) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				clip.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}