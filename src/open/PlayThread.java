package open;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author RuanGuangPing
 * A TimerTask (thread) which convert text string to sound.
 * Timer is used because it's need to reconstruct sound in a real time.
 * Timer's period is the tempo which user selected when he saved this record. 
 */
public class PlayThread extends TimerTask {
	static char[] chrArray;
	boolean isStart = false;
	static int index = 0;
	
	public void run(){
		//in case fileString is null
		if (OpenFile.fileString == null)
			OpenFile.fileString ="~";
		char[] array = OpenFile.fileString.toCharArray();
		
		if (!isStart){
			//scan all the string
			for ( index = 0; index < array.length;index++){
				//ignore the drum type name
				if (array[index] == '@' && index > 4){
					isStart = true;
					break;
				}
			}
		}
		else {	
			//use robot to press a specified key.
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			if (index==array.length)
				return;
			
			switch(array[index++]){
			case 'H':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_H);
				return;
			case 'L':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_U);
				return;
			case 'R':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_G);
				return;
			case 'D':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_F);
				return;
			case '8':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_8);
				return;
			case '9':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_9);
				return;
			case '0':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_0);
				return;
			case 'P':
				//robot presses the key board.
				robot.keyPress(KeyEvent.VK_P);
				return;
				default:
					System.out.println("Sleep");
			}
		}
	}
		
}
