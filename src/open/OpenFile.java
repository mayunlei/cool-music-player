package open;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import main.Main;

/**
 * method: public void getListFile( File )
 * usage: read the outer file for play list.
 */

public class OpenFile{
	//to hold file content
	static String fileString;
	static //construct a timer to run the note tempo.
	Timer timer = null ;
	
	public static void open(String fileName){
	try{
		//in case file name is null
		if (fileName==null){
			String msg = "Selection can not be null!";
			JOptionPane.showMessageDialog(null,
				    msg,
				    "Message",
				    JOptionPane.INFORMATION_MESSAGE);	
			return;
		}
		
		File file = new File(fileName);
		//clear the last file
		fileString = "";
		//construct input Streams.
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		BufferedReader br = new BufferedReader( new InputStreamReader(dis));
		int i = 0;
		String tmpString;
		
		//read all file to fileString.
		while ( ( tmpString = br.readLine() )!= null ){
			//add this line to fileString
			fileString+=tmpString;
		}
		//dispose all streams.
		fis.close();
		dis.close();
		br.close();
		
	}catch (FileNotFoundException e){ //catch exceptions.
		e.printStackTrace();
	}catch (IOException e){
		e.printStackTrace();
	}
	}
	
	/*
	 * Convert a String(drum note) to sound.
	 * Using Timer with period is the user selected tempo when he save this record.
	 */
	public static void playFile(){
				
				timer = new Timer();
				//task is a TempoThread object.
				TimerTask task = new PlayThread();
				//get current time.
				Date now = new Date();
				
				//tempo = how many times/a minute.
				int period = (1000*60)/(Main.tempo*3);
				
				//run timer
				timer.scheduleAtFixedRate(task, now, period);
				
			
	}
	/*
	 * Cancel timer when it is no longer useful.
	 */
	public static void stopPlay(){
		//stop
		if (timer!=null)
		timer.cancel();
}
}