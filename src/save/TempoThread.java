package save;

import java.util.TimerTask;

import main.Main;

/**
 * Update the note that user has been playing.
 * Using Timer with a specified period.
 * @author RuanGuangPing
 *
 */

public class TempoThread extends TimerTask {
	String tmp = Main.drumNote;
	
	public void run(){
			//add "~" to drumNote ("~" means sleep).
			Main.drumNote+="~";
			//show the new note.
			Main.saveFrame.screen.setText(Main.drumNote);
	}
}
