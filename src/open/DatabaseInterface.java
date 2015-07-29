package open;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

import save.SaveFrame;




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
 * @author RuanGuangPing
 * The database access interface
 * User can select any item on the table(which connect to program database).Then this item will be queried.
 */
public class DatabaseInterface extends javax.swing.JFrame {
	private JPanel tablePane;
	private static JTable myTable;
	private static JPanel btPane;
	private JButton exit;
	private JButton select;

	//Result set from database.
	ResultSet rst;
	
	//table columns
	String[] cl = {"File Name","Last Modified"};
	static Vector<String> columns ;
	
	//table records
	static Vector<Vector<String>> records = new Vector<Vector<String>>();
	
	
	//constructor
	public DatabaseInterface() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			//create database
			SaveFrame.createTable();

			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Select your file to play");
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			this.setFocusable(false);
			this.setFocusableWindowState(false);
			{
				tablePane = new JPanel();
				AnchorLayout tablePaneLayout = new AnchorLayout();
				getContentPane().add(tablePane, new AnchorConstraint(1, 1000, 892, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tablePane.setLayout(tablePaneLayout);
				tablePane.setPreferredSize(new java.awt.Dimension(605, 444));
				{					
					//initialize the table
					updateTable(SaveFrame.result);
					rst = SaveFrame.result;
					
					//set table model for this table
					TableModel tableModel = 
						new DefaultTableModel(records,columns);
					
					myTable = new JTable();		
					tablePane.add(myTable, new AnchorConstraint(16, 1000, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					myTable.setModel(tableModel);
					
					//add ListSelectionListener	
					//ListSelectionListener implementation, please see LisListener.java
					ListSelectionModel listSelectionModel;
					listSelectionModel = myTable.getSelectionModel();
				    listSelectionModel.addListSelectionListener(new ListListener());
				    myTable.setSelectionModel(listSelectionModel);

					myTable.setPreferredSize(new java.awt.Dimension(605, 437));
					myTable.setFocusable(false);
				}
			}
			{
				btPane = new JPanel();
				AnchorLayout btPaneLayout = new AnchorLayout();
				getContentPane().add(btPane, new AnchorConstraint(892, 1000, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				btPane.setBackground(new java.awt.Color(0,0,0));
				btPane.setLayout(btPaneLayout);
				btPane.setPreferredSize(new java.awt.Dimension(605, 54));
				btPane.setFocusable(false);
				{
					select = new JButton();
					btPane.add(select, new AnchorConstraint(231, 718, 657, 559, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					select.setText("Select");
					select.setPreferredSize(new java.awt.Dimension(96, 23));
					select.setFocusable(false);
					
					//select button action listener.
					//Its mission are:
					//1.Construct OpenFrame which show the record's contents.
					//2.Invoke open method of OpenFile class which load data from file to window.
					select.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//construct
							OpenFrame screen = new OpenFrame();
							//open hard disk file
							OpenFile.open(ListListener.fileName);
							screen.text.setText(OpenFile.fileString);
							screen.setVisible(true);
							
							//then destroy window
							Window win = SwingUtilities.getWindowAncestor(DatabaseInterface.btPane);
							win.dispose(); //exit program.
						}
					});
				}
				{
					exit = new JButton();
					btPane.add(exit, new AnchorConstraint(231, 460, 657, 295, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					exit.setText("Exit");
					exit.setFocusable(false);
					exit.setPreferredSize(new java.awt.Dimension(100, 23));
					exit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Window win = SwingUtilities.getWindowAncestor(DatabaseInterface.btPane);
							win.dispose(); //exit program.
						}
					});
				}
			}
			pack();
			this.setSize(621, 536);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	/*
	 * Update the table in the window by given ResultSet
	 */
public void updateTable(ResultSet result){
	
		//clear the previous data
		if ( records != null && columns != null){
			records.clear();
			columns.clear();		
		}

		
		//check if result is null
		if (result==null)
			return;
		//fill the records for table when it starts
		try {
			//point to the beginning.
			result.beforeFirst();
			while (result.next()){
				//new row
				Vector<String> row = new Vector<String>(); 
				//get all fields of this record
				for (int i = 0; i < cl.length; i++){
					//add information to list.
					row.add(result.getString(i+1));
				}
				//add this record.
				records.add(row);
			}
			
			//set columns name
			columns = new Vector<String>();
			for (int i = 0; i < cl.length; i++)
				columns.add(cl[i]);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


/*
 * Get information from item which user selected on the table.
 * Information means: file name (decides which file on hard disk will be open).
 */
public static void getFileName() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	//Connection setting
	String url = "jdbc:mysql://127.0.0.1:3306/";
    String db = "drum";
    String driver = "com.mysql.jdbc.Driver";
    String user = "root";
    String pass = "root";
	String tableName = "fileio";
	
	ResultSet  result = null;
	//connection
	Connection con;
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
	result.beforeFirst();
	if (ListListener.rowSelected!= 0)
		result.absolute(ListListener.rowSelected+1);
	else result.next();
	String str = result.getString(1);
	ListListener.fileName = str;
}

	
}
	
