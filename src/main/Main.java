package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JPanel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import open.DatabaseInterface;


import playalong.PlayAlong;
import save.SaveFrame;

import listener.BassListener;
import listener.Cymbal1Listener;
import listener.Cymbal2Listener;
import listener.Cymbal3Listener;
import listener.HiHatListener;
import listener.HighTomListener;
import listener.LowTom1Listener;
import listener.LowTom2Listener;
import listener.MidTomListener;
import listener.PedalListener;
import listener.SnareListener;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/

/**
 * @RuanGuangPing
 * This class initialize the main frame of program. 
 * It contains all frame's components.
 * These components action listener is implemented right after its declaration.
 */
public class Main extends JFrame {
	
	/*
	 * Frame's components adding
	 * These components were added by Jigloo tool.
	 */
	private JPanel drumPane ;
	private JPanel buttonPane;
	private JLabel jLabel1;
	private JMenu playAlongMn;
	private JMenuItem electric;
	private JMenuItem rock;
	private JMenuItem jazz;
	private JMenuItem normal;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem1;
	private JMenu select;
	private JMenu jMenu1;
	private JMenuBar menu;
	
	private JButton playAlong;
	private JLabel jLabel2;
	private JComboBox function;
	private JButton save;
	private JLabel drumLable;
	private JButton open;
	private JButton exit;
	/************/
	
	
	/*
	 * RoundButton components 
	 * (since the drum is a round, RoundButton class was implemented,
	 * for more information, please see RoundButton1.java) 
	 */
	//Bass drum
	static RoundButton1 bass = new RoundButton1(false,"H");
	//snare
	static private RoundButton1 snare = new RoundButton1("U");
	//hi-hat
	static private RoundButton1 hiHat = new RoundButton1(false,"F");
	//pedal
	static private RoundButton1 pedal = new RoundButton1(false,"G");
	//Cymbal1
	static private RoundButton1 cymbal1 = new RoundButton1(false,"8");
	//hi-tom
	static private RoundButton1 hiTom = new RoundButton1(false,"9");
	//mid-tom
	static private RoundButton1 midTom = new RoundButton1(false,"0");
	//cymbal2
	static private RoundButton1 cymbal2 = new RoundButton1(false,"P");
	//low-tom 1
	static private RoundButton1 lowTom1 = new RoundButton1(false,"L");
	//cymbal3
	static private RoundButton1 cymbal3 = new RoundButton1(false,"Q");
	//low tom 2
	static private RoundButton1 lowTom2 = new RoundButton1(false,"1");
	/*******complete adding roundButtons********/
	
	/*
	 * Midi channel declaration
	 */
	// The channel we play on: 10 is for percussion
	 static MidiChannel channel;  
	// Default volume.
	 static int velocity = 90;    
	 //Synthesizer for midi playing.
	 static Synthesizer  synthesizer;
	/********************************/
	 
	 
	 /*
	  * Record drum note.
	  */
	 //drumNote which user has played.
	 public static String drumNote = null;
	 //drum type name.
	 static String init = null;
	 //SaveFrame object, using for save drum note.
	 public static SaveFrame saveFrame = new SaveFrame();
	 //default tempo(speed).
	 public static int tempo = 120;

	 public static void main(String[] args) {
			
			//Show the maze
			Main inst = new Main();
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);
			

		}
	 
	/*
	 * Default constructor
	 */
	public Main() {
		super();

		 try {
			 //get Synthesizer of the midi system and open it.
				synthesizer = MidiSystem.getSynthesizer( );
				synthesizer.open( );
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	      
		
        // Channel 10 is the GeneralMidi percussion channel.  
		//In Java code, we number channels from 0 and use channel 9 instead.
        channel = synthesizer.getChannels( )[9];
        
        /*
         * Add KeyListener
         */
        normalSet();
        
        //Show frame interface and add listeners.
		initGUI();
	}
	
	/*
	 * GUI initialization.
	 */
	private void initGUI() {
		try {
			//Main frame setting
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle("Drum set");
			this.setResizable(false);
			this.setAlwaysOnTop(true);
			this.toFront();
			{
				{
					//menu setting
					menu = new JMenuBar();
					setJMenuBar(menu);
					menu.setBackground(new java.awt.Color(0,0,0));
					menu.setForeground(new java.awt.Color(204,2,37));
					menu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					{
						jMenu1 = new JMenu();
						menu.add(jMenu1);
						jMenu1.setText("File");
						jMenu1.setForeground(new java.awt.Color(204,2,37));
						{
							jMenuItem1 = new JMenuItem();
							jMenu1.add(jMenuItem1);
							jMenuItem1.setText("Open");
							jMenuItem1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									//just simple click the open button
									open.doClick();
								}
							});
						}
						{
							jMenuItem2 = new JMenuItem();
							jMenu1.add(jMenuItem2);
							jMenuItem2.setText("Record");
							jMenuItem2.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									//just simple click save button
									save.doClick();
								}
							});
						}
						{
							jMenuItem3 = new JMenuItem();
							jMenu1.add(jMenuItem3);
							jMenuItem3.setText("Exit");
							jMenuItem3.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									//system exit
									System.exit(0);
								}
							});
						}
					}
					{
						select = new JMenu();
						menu.add(select);
						select.setText("Switch");
						select.setBackground(new java.awt.Color(0,0,0));
						select.setForeground(new java.awt.Color(204,2,37));
						{
							normal = new JMenuItem();
							select.add(normal);
							normal.setText("Normal");
						}
						{
							jazz = new JMenuItem();
							select.add(jazz);
							jazz.setText("Jazz");
						}
						{
							rock = new JMenuItem();
							select.add(rock);
							rock.setText("Rock");
						}
						{
							electric = new JMenuItem();
							select.add(electric);
							electric.setText("Electric");
						}
					}
					{
						playAlongMn = new JMenu();
						menu.add(playAlongMn);
						playAlongMn.setText("Play Along");
						playAlongMn.setForeground(new java.awt.Color(204,2,37));
						playAlongMn.setBackground(new java.awt.Color(0,0,0));
						playAlongMn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								//just simple click play along button
								playAlong.doClick();
							}
						});
					}
				}
				drumPane = new JPanel();
				getContentPane().add(drumPane, "Center");
				drumPane.setLayout(null);
				drumPane.setBounds(0, 0, 478, 364);
				
				
				/*
				 * adding buttons and it mouse click action listener.
				 */
				drumPane.add(bass);
				bass.setBounds(156, 115, 158, 146);
				bass.addActionListener(new BassListener());
				
				drumPane.add(snare);
				snare.setBounds(126, 244, 126, 106);
				snare.addActionListener(new SnareListener());
				
				drumPane.add(hiHat);
				hiHat.setBounds(2, 153, 147, 140);
				hiHat.addActionListener(new HiHatListener());
				
				drumPane.add(cymbal1);
				cymbal1.setBounds(0, 0, 163, 155);
				cymbal1.addActionListener(new Cymbal1Listener());
				
				drumPane.add(hiTom);
				hiTom.setBounds(112, 11, 118, 116);
				hiTom.addActionListener(new HighTomListener());
				
				drumPane.add(midTom);
				midTom.setBounds(236, 19, 123, 114);
				midTom.addActionListener(new MidTomListener());
				
				drumPane.add(cymbal2);
				cymbal2.setBounds(317, 6, 160, 182);
				cymbal2.addActionListener(new Cymbal2Listener());
				
				drumPane.add(lowTom1);
				lowTom1.setBounds(320, 136, 116, 133);
				lowTom1.addActionListener(new LowTom1Listener());
				
				drumPane.add(cymbal3);
				cymbal3.setBounds(368, 220, 102, 115);
				cymbal3.addActionListener(new Cymbal3Listener());
				
				drumPane.add(lowTom2);
				lowTom2.setBounds(282, 255, 138, 121);
				lowTom2.addActionListener(new LowTom2Listener());
				
				drumPane.add(pedal);
				pedal.setBounds(35, 272, 92, 89);
				pedal.addActionListener(new PedalListener());
				
				/*****adding completed*****/
				
				
				{
					jLabel1 = new JLabel();
					drumPane.add(jLabel1);
					jLabel1.setText("jLabel1");
					jLabel1.setBounds(-1, -2, 484, 365);
					jLabel1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/drumFace.jpg")));
				}
			}
			{
				buttonPane = new JPanel();
				getContentPane().add(buttonPane);
				buttonPane.setBounds(0, 362, 478, 161);
				buttonPane.setLayout(null);
				{
					open = new JButton();
					buttonPane.add(open);
					open.setText("Open");
					open.setBounds(12, 10, 127, 55);
					open.setBackground(new java.awt.Color(255,255,255));
					open.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Load Icon.jpg")));
					open.setFocusable(false);
					//Play an exit record
					open.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							DatabaseInterface database = new DatabaseInterface();
							database.setVisible(true);
						}
					});
				}
				{
					ComboBoxModel functionModel = 
						new DefaultComboBoxModel(
								new String[] { "Normal", "Jazz", "Rock Band","Funk","Electric","Latin"});
					function = new JComboBox();
					buttonPane.add(function);
					function.setModel(functionModel);
					function.setBounds(262, 29, 108, 23);
					function.setFocusable(false);
					
					//Change drum type: Latin, Rock, Funk, Electric ....
					function.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
						
							//remove current KeyListeners
							removeKeyListener(getKeyListeners()[0]);
							
							//add a new KeyListeners
							int selected = function.getSelectedIndex();
							switch(selected){
							case 0:
								//normal drum set
								normalSet();
								break;
							case 1:
								//jazz drum set
								jazzSet();
								break;
							case 2:
								//rock drum set
								rockSet();
								break;
							case 3:
								//funk drum set
								funkSet();
								break;
							case 4:
								//electric drum set
								electricSet();
								break;							
							case 5:
								//latin drum set
								latinSet();
								break;
								default:
									System.out.println("Something wrong with the drum set setting.");
							}
						}
					});
				}
				{
					jLabel2 = new JLabel();
					buttonPane.add(jLabel2);
					jLabel2.setText("Drum type");
					jLabel2.setBounds(182, 36, 69, 16);
					jLabel2.setForeground(new java.awt.Color(220,1,34));
				}
				{
					save = new JButton();
					buttonPane.add(save);
					save.setText("Record");
					save.setBounds(12, 93, 127, 55);
					save.setBackground(new java.awt.Color(255,255,255));
					save.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Save.png")));
					save.setFocusable(false);
					
					//When save button clicked. SaveFrame will pop up.
					save.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
						
							//Show the save frame
							saveFrame.setVisible(true);
							//reset the drumNote.
							drumNote = init;
							
						}
					});
				}
				{
					exit = new JButton();
					buttonPane.add(exit);
					exit.setText("Exit");
					exit.setBounds(341, 91, 115, 55);
					exit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Exit Icon.jpg")));
					exit.setBackground(new java.awt.Color(255,255,255));
					exit.setFont(new java.awt.Font("Gisha",0,12));
					exit.setFocusable(false);
					exit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.exit(0);
						}
					});
				}
				{
					playAlong = new JButton();
					buttonPane.add(playAlong);
					playAlong.setText("Play Along");
					playAlong.setBounds(170, 91, 148, 55);
					playAlong.setBackground(new java.awt.Color(255,255,255));
					playAlong.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Info Icon.jpg")));
					playAlong.setFont(new java.awt.Font("Segoe UI",0,12));
					playAlong.setFocusable(false);
					
					//PlayAlong button clicked. PlayAlong window pops up.
					playAlong.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							PlayAlong playAlong = new PlayAlong();
							playAlong.setVisible(true);
						}
					});
				}	
				
			}
			{
			}
			pack();
			this.setSize(483, 587);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
/**
 * Normal drum's key listener adding	
 */
public void normalSet(){
		init = new String("@Normal drum kit@");
		//adding title to the output file
		drumNote = init;
	
		addKeyListener(new KeyAdapter( ) {
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar( );    
                switch (key){
                case 'h':
                	Main.channel.noteOn(36,Main.velocity);
                	//append the note
                	drumNote += "H";
                	break;
                case 'u':
                	Main.channel.noteOn(38,Main.velocity);
                	drumNote += "L";
                	break;
                case 'g':
                	Main.channel.noteOn(46,Main.velocity);
                	drumNote += "R";
                	break;
                case 'f':
                	Main.channel.noteOn(44,Main.velocity);
                	drumNote += "D";
                	break;
                case '8':
                	Main.channel.noteOn(49,Main.velocity);
                	drumNote += "8";
                	break;
                case '9':
                	Main.channel.noteOn(50,Main.velocity);
                	drumNote += "9";
                	break;	
                case '0':
                	Main.channel.noteOn(48,Main.velocity);
                	drumNote += "0";
                	break;
                case 'p':
                	Main.channel.noteOn(55,Main.velocity);
                	drumNote += "P";
                	break;
                case 'l':
                	Main.channel.noteOn(45,Main.velocity);
                	break;
                case 'q':
                	Main.channel.noteOn(56,Main.velocity);
                	break;
                case '1':
                	Main.channel.noteOn(64,Main.velocity);
                	break;
                default:
                	Main.channel.noteOn(65,Main.velocity);
                }
            }
            
            //Key release respond
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode( );
                	Main.channel.noteOff(key);
            }
        });
	}

/**
 * Jazz drum's key listener adding	
 */
public void jazzSet(){
	init = new String("@jazz drum kit@");
	//adding title to the output file
	drumNote = init;

	addKeyListener(new KeyAdapter( ) {
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar( );    
            switch (key){
            case 'h':
            	Main.channel.noteOn(35,Main.velocity+20);
            	//append the note
            	drumNote += "H";
            	break;
            case 'u':
            	Main.channel.noteOn(37,Main.velocity-5);
            	drumNote += "L";
            	break;
            case 'g':
            	Main.channel.noteOn(46,Main.velocity);
            	drumNote += "R";
            	break;
            case 'f':
            	Main.channel.noteOn(42,Main.velocity);
            	drumNote += "D";
            	break;
            case '8':
            	Main.channel.noteOn(49,Main.velocity);
            	drumNote += "8";
            	break;
            case '9':
            	Main.channel.noteOn(45,Main.velocity);
            	drumNote += "9";
            	break;	
            case '0':
            	Main.channel.noteOn(47,Main.velocity);
            	drumNote += "0";
            	break;
            case 'p':
            	Main.channel.noteOn(59,Main.velocity);
            	drumNote += "P";
            	break;
            case 'l':
            	Main.channel.noteOn(41,Main.velocity);
            	break;
            case 'q':
            	Main.channel.noteOn(57,Main.velocity);
            	break;
            case '1':
            	Main.channel.noteOn(64,Main.velocity);
            	break;
            default:
            	Main.channel.noteOn(65,Main.velocity);
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode( );
            	Main.channel.noteOff(key);
        }
    });
}
	
/**
 * Rock drum's key listener adding	
 */
public void rockSet(){
	init = new String("@Rock band drum kit@");
	//adding title to the output file
	drumNote = init;

	addKeyListener(new KeyAdapter( ) {
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar( );    
            switch (key){
            case 'h':
            	Main.channel.noteOn(36,Main.velocity+20);
            	//append the note
            	drumNote += "H";
            	break;
            case 'u':
            	Main.channel.noteOn(38,Main.velocity-5);
            	drumNote += "L";
            	break;
            case 'g':
            	Main.channel.noteOn(46,Main.velocity);
            	drumNote += "R";
            	break;
            case 'f':
            	Main.channel.noteOn(44,Main.velocity);
            	drumNote += "D";
            	break;
            case '8':
            	Main.channel.noteOn(49,Main.velocity);
            	drumNote += "8";
            	break;
            case '9':
            	Main.channel.noteOn(50,Main.velocity);
            	drumNote += "9";
            	break;	
            case '0':
            	Main.channel.noteOn(48,Main.velocity);
            	drumNote += "0";
            	break;
            case 'p':
            	Main.channel.noteOn(57,Main.velocity);
            	drumNote += "P";
            	break;
            case 'l':
            	Main.channel.noteOn(45,Main.velocity);
            	break;
            case 'q':
            	Main.channel.noteOn(57,Main.velocity);
            	break;
            case '1':
            	Main.channel.noteOn(64,Main.velocity);
            	break;
            default:
            	Main.channel.noteOn(65,Main.velocity);
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode( );
            	Main.channel.noteOff(key);
        }
    });
}
	
/**
 * Funk drum's key listener adding	
 */
public void funkSet(){
	init = new String("@Funk drum kit@");
	//adding title to the output file
	drumNote = init;

	addKeyListener(new KeyAdapter( ) {
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar( );    
            switch (key){
            case 'h':
            	Main.channel.noteOn(36,Main.velocity+20);
            	//append the note
            	drumNote += "H";
            	break;
            case 'u':
            	Main.channel.noteOn(38,Main.velocity-5);
            	drumNote += "L";
            	break;
            case 'g':
            	Main.channel.noteOn(46,Main.velocity);
            	drumNote += "R";
            	break;
            case 'f':
            	Main.channel.noteOn(44,Main.velocity);
            	drumNote += "D";
            	break;
            case '8':
            	Main.channel.noteOn(49,Main.velocity);
            	drumNote += "8";
            	break;
            case '9':
            	Main.channel.noteOn(45,Main.velocity);
            	drumNote += "9";
            	break;	
            case '0':
            	Main.channel.noteOn(48,Main.velocity);
            	drumNote += "0";
            	break;
            case 'p':
            	Main.channel.noteOn(55,Main.velocity);
            	drumNote += "P";
            	break;
            case 'l':
            	Main.channel.noteOn(45,Main.velocity);
            	break;
            case 'q':
            	Main.channel.noteOn(57,Main.velocity);
            	break;
            case '1':
            	Main.channel.noteOn(64,Main.velocity);
            	break;
            default:
            	Main.channel.noteOn(65,Main.velocity);
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode( );
            	Main.channel.noteOff(key);
        }
    });
}
	
/**
 * Electric drum's key listener adding	
 */
public void electricSet(){
	init = new String("@Electric drum kit@");
	//adding title to the output file
	drumNote = init;

	addKeyListener(new KeyAdapter( ) {
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar( );    
            switch (key){
            case 'h':
            	Main.channel.noteOn(36,Main.velocity+20);
            	//append the note
            	drumNote += "H";
            	break;
            case 'u':
            	Main.channel.noteOn(40,Main.velocity-5);
            	drumNote += "L";
            	break;
            case 'g':
            	Main.channel.noteOn(46,Main.velocity);
            	drumNote += "R";
            	break;
            case 'f':
            	Main.channel.noteOn(44,Main.velocity);
            	drumNote += "D";
            	break;
            case '8':
            	Main.channel.noteOn(49,Main.velocity);
            	drumNote += "8";
            	break;
            case '9':
            	Main.channel.noteOn(50,Main.velocity);
            	drumNote += "9";
            	break;	
            case '0':
            	Main.channel.noteOn(48,Main.velocity);
            	drumNote += "0";
            	break;
            case 'p':
            	Main.channel.noteOn(55,Main.velocity);
            	drumNote += "P";
            	break;
            case 'l':
            	Main.channel.noteOn(45,Main.velocity);
            	break;
            case 'q':
            	Main.channel.noteOn(39,Main.velocity);
            	break;
            case '1':
            	Main.channel.noteOn(64,Main.velocity);
            	break;
            default:
            	Main.channel.noteOn(65,Main.velocity);
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode( );
            	Main.channel.noteOff(key);
        }
    });
}

/**
 * Latin drum's key listener adding	
 */
public void latinSet(){
	init = new String("@Latin drum kit@");
	//adding title to the output file
	drumNote = init;

	addKeyListener(new KeyAdapter( ) {
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar( );    
            switch (key){
            case 'h':
            	Main.channel.noteOn(35,Main.velocity+20);
            	//append the note
            	drumNote += "H";
            	break;
            case 'u':
            	Main.channel.noteOn(38,Main.velocity-5);
            	drumNote += "L";
            	break;
            case 'g':
            	Main.channel.noteOn(46,Main.velocity);
            	drumNote += "R";
            	break;
            case 'f':
            	Main.channel.noteOn(44,Main.velocity);
            	drumNote += "D";
            	break;
            case '8':
            	Main.channel.noteOn(64,Main.velocity);
            	drumNote += "8";
            	break;
            case '9':
            	Main.channel.noteOn(60,Main.velocity);
            	drumNote += "9";
            	break;	
            case '0':
            	Main.channel.noteOn(61,Main.velocity);
            	drumNote += "0";
            	break;
            case 'p':
            	Main.channel.noteOn(55,Main.velocity);
            	drumNote += "P";
            	break;
            case 'l':
            	Main.channel.noteOn(76,Main.velocity);
            	break;
            case 'q':
            	Main.channel.noteOn(71,Main.velocity);
            	break;
            case '1':
            	Main.channel.noteOn(64,Main.velocity);
            	break;
            default:
            	Main.channel.noteOn(67,Main.velocity);
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode( );
            	Main.channel.noteOff(key);
        }
    });
}
	
}

