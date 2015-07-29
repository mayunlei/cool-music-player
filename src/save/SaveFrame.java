package save;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import main.Main;


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
public class SaveFrame extends javax.swing.JFrame {
	/*
	 * Frame's components
	 */
	static JPanel jPanel1;
	static JTextArea screen;
	private JButton start;
	private JComboBox tempoF;
	private JLabel jLabel1;
	private JButton exit;
	private JButton save;
	private JPanel jPanel2;
	static int i = 0;
	/*******************/
	
	
	/*****for JDBC*****/
    public static //To storage result
    ResultSet result;
    int resultLength = 0;
    
	
	//outer file to storage user input note.
	static File outFile ;
	String fileName = "noname";

	Timer timer = null;
	
	//default constructor
	public SaveFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			//create a database for file I/O
			createTable();
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Save you note");
			getContentPane().setBackground(new java.awt.Color(0,0,0));
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			this.setFocusable(false);
			this.setFocusableWindowState(false);
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				getContentPane().add(jPanel1, new AnchorConstraint(1, 1001, 816, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(484, 295));
				{
					screen = new JTextArea();
					jPanel1.add(screen, BorderLayout.CENTER);
					screen.setEditable(false);
					screen.setFocusable(false);
					screen.setLineWrap(true);
				}
			}
			{
				jPanel2 = new JPanel();
				AnchorLayout jPanel2Layout = new AnchorLayout();
				getContentPane().add(jPanel2, new AnchorConstraint(816, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jPanel2.setLayout(jPanel2Layout);
				jPanel2.setBackground(new java.awt.Color(192,192,192));
				jPanel2.setPreferredSize(new java.awt.Dimension(484, 67));
				{
					save = new JButton();
					jPanel2.add(save, new AnchorConstraint(171, 228, 828, 25, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					save.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/save-icon.png")));
					save.setFocusable(false);
					save.setPreferredSize(new java.awt.Dimension(98, 44));

					//Save button listener
					//Write to file.
					save.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
						
							stopTimer();
							
							fileName = getFileNameInput();
							//get user file name
							outFile = new File( fileName);
							/*
							 * write new user information to file.
							 */
							try{
	
								//write
								FileWriter fr = new FileWriter(outFile,false);
								//speed up the writing by buffer.
								BufferedWriter out = new BufferedWriter(fr); 
								
								//write
								out.write(Main.drumNote);
								
								//close buffer
								out.close();
								//close window
								exit.doClick();
								
							} catch (Exception e){
								System.out.println(e);
							}
							
							
							//add to database
							try {
								addRecord();
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					

				}
				{
					exit = new JButton();
					jPanel2.add(exit, new AnchorConstraint(171, 463, 843, 263, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					exit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/button-cancel-icon.png")));
					exit.setFocusable(false);
					exit.setPreferredSize(new java.awt.Dimension(97, 45));
					exit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							stopTimer();
							Window win = SwingUtilities.getWindowAncestor(SaveFrame.jPanel1);
							win.dispose(); //exit program.
						}
					});
				}
				{
					jLabel1 = new JLabel();
					jPanel2.add(jLabel1, new AnchorConstraint(514, 653, 753, 498, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					jLabel1.setText("Your tempo");
					jLabel1.setPreferredSize(new java.awt.Dimension(75, 16));
				}

				{
					start = new JButton();
					jPanel2.add(start, new AnchorConstraint(470, 990, 813, 846, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					start.setText("Start");
					start.setFocusable(false);
					start.setPreferredSize(new java.awt.Dimension(70, 23));

					//Start record button listener
					//Start the recording
					start.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//construct and run thread
						
							//get the selected tempo
							Main.tempo = Integer.parseInt(tempoF.getSelectedItem().toString());
							
							//construct a timer to run the note tempo.
							timer = new Timer();
							//task is a TempoThread object.
							TimerTask task = new TempoThread();
							//get current time.
							Date now = new Date();
							
							//tempo = how many times/a minute.
							int period = (1000*60)/(Main.tempo*2);
							
							//run timer
							timer.scheduleAtFixedRate(task, now, period);
							
						}
					});
					
					
					
				}
				{
					ComboBoxModel tempoFModel = 
						new DefaultComboBoxModel(
								new String[] { "120", "160","180","200","220","100","80" });
					tempoF = new JComboBox();
					jPanel2.add(tempoF, new AnchorConstraint(470, 788, 813, 653, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					tempoF.setModel(tempoFModel);
					tempoF.setEditable(true);
					tempoF.setPreferredSize(new java.awt.Dimension(65, 23));
				}
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	public String getFileNameInput(){
        String str = JOptionPane.showInputDialog(null, "Enter your file name: ", 
        		"Save", 1);
        		        if(str != null)
        		          JOptionPane.showMessageDialog(null, "Your file name is: " + str, 
        		"Save", 1);
        		        else
        		          JOptionPane.showMessageDialog(null, "You pressed cancel button.", 
        		"Save", 1);
        		        return str;
	}


		
		
		/*
		 * default constructor
		 * create a "table student" table.
		 */
		public static void createTable() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
						
			//Connection setting
			String url = "jdbc:mysql://127.0.0.1:3306/";
		    String db = "drum";
		    String driver = "com.mysql.jdbc.Driver";
		    String user = "root";
		    String pass = "root";
			String tableName = "fileio";
	
			//connection
			Connection con;
			try {
				
				Class.forName(driver).newInstance();
				con = DriverManager.getConnection(url+db,user,pass);
				Statement stm = con.createStatement();
				
				//Table create command.
				String executeString = 	
					"CREATE TABLE  `drum`.`"+tableName+"` ("
						 +" `name` varchar(30) NOT NULL default 'noname',"
						 +" `date` varchar(30) NOT NULL default '0'"
						 +") ENGINE=InnoDB DEFAULT CHARSET=latin1;" ;
				
				//Check if there is the table exits
				DatabaseMetaData md = con.getMetaData();
				ResultSet tables = md.getTables(null, "drum", tableName, null);
				
				//if table does not exit then create it. Close statement otherwise.
				if (!tables.next()){
					//create table
					stm.executeUpdate(executeString);
				}
				
				
				//close Statement and Connection.
				stm.close();
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			
			//Get result
			try {
				Class.forName(driver).newInstance();
				con = DriverManager.getConnection(url+db,user,pass);
				Statement stm = con.createStatement();
				result = stm.executeQuery("SELECT * FROM "+"`"+tableName+"`");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		//add new record to table
		public void addRecord() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
			//Connection setting
			String url = "jdbc:mysql://127.0.0.1:3306/";
		    String db = "drum";
		    String driver = "com.mysql.jdbc.Driver";
		    String user = "root";
		    String pass = "root";
			String tableName = "fileio";
			
			
			//connection
			Connection con;
			try {
				
				Class.forName(driver).newInstance();
				con = DriverManager.getConnection(url+db,user,pass);
				Statement stm = con.createStatement();
				
				Date now = new Date();
				SimpleDateFormat Format = new SimpleDateFormat("yyyy/MM/dd hh:mm ");
				String nowStr = Format.format(now);
				
				//Table create command.
				String executeString = 	
					"INSERT INTO  `"+tableName+"` VALUE("
						 +" \""+fileName+"\", "
						 +" \""+nowStr+"\" "
						 +");" ;
				
				//add to table
					stm.addBatch(executeString);
					stm.executeBatch();
				
				//close Statement and Connection.
				stm.close();
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		//Cancel timer
		public void stopTimer(){
			if ( timer != null)
			timer.cancel();
		}
}
