package listener;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author RuanGuangPing	
 * Process the mid tom sound of the drum
 */
public class MidTomListener extends Listener{
	//override the Listener base class method.
	public void actionPerformed( ActionEvent e){
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//robot presses the key board.
		robot.keyPress(KeyEvent.VK_0);
	}
}
