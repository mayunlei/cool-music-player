package playalong;
import java.io.File; 
import java.io.FileInputStream;
import java.io.IOException; 
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.FloatControl; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException; 
 
/**
 * Play an .wav file by its name.
 * Using Clip object.
 * @author RuanGuangPing
 *
 */
public class PlaySound{

	 static Clip clip;
	/*
	 * Play wav file
	 */
	public static void play(String filename){
		try {
			//if there no file to play
			if (filename == null)
				return;
	        File tadaSound = new File(filename);     
	        AudioInputStream audioInputStream = AudioSystem
	              .getAudioInputStream(new FileInputStream(tadaSound));
	        AudioFormat audioFormat = audioInputStream
	              .getFormat();
	        DataLine.Info dataLineInfo = new DataLine.Info(
	              Clip.class, audioFormat);
	        clip = (Clip) AudioSystem
	              .getLine(dataLineInfo);
	        clip.open(audioInputStream);
	        clip.start();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	}
	
	/*
	 * Stop play
	 */
	public static void stopIt(){
		clip.stop();
	}
}
