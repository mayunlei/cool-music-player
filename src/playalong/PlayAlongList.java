package playalong;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;






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
 * Handle accompaniments list.
 * Since there are many genres, accompaniments list handling is not such simple.
 * Which list user selected, and this list contains which accompaniments, all are handled here.
 */
public class PlayAlongList extends javax.swing.JFrame {
	
	 /*
	 * sound tracks list
	 */
	int LISTLENGTH = 20;
	//swing tracks list
	String[] swingList = new String[LISTLENGTH];
	//jazz tracks list
	String[] jazzList = new String[LISTLENGTH];
	//rock tracks list
	String[] rockList = new String[LISTLENGTH];
	//latin tracks list
	String[] latinList = new String[LISTLENGTH];
	//funk tracks list
	String[] funkList = new String[LISTLENGTH];
	//pop tracks list
	String[] popList = new String[LISTLENGTH];
	
	//frame components
	private JList list;
	private JButton exit;
	static private JPanel jPanel1;
	private JLabel jLabel1;
	private JPanel listPane;
	
	
	//List file: contains all the file name
	File listFile = new File("playList.txt");
	
	//user selected to find which list.Default is swing genres.
	String whichList = new String("swing");
	
	//which song will be play
	static String songName;
	

	
	public PlayAlongList() {
		super();
		initGUI();
	}
	
	public PlayAlongList(String str) {
		super();
		this.whichList = str;
		initGUI();
		this.setTitle(whichList+" sound tracks List");
	}
	private void initGUI() {
		try {
			//read outer list file
			getListFile(listFile);

			
			//data initialization
			int i = 0;
			//get user selected list.
			i = getWhichList();			
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setFocusable(false);
			this.setTitle("Play along sound tracks List");
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			getContentPane().setBackground(new java.awt.Color(0,0,0));
			{
				listPane = new JPanel();
				getContentPane().add(listPane, new AnchorConstraint(1, 1001, 826, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				listPane.setLayout(null);
				listPane.setBackground(new java.awt.Color(0,0,0));
				listPane.setPreferredSize(new java.awt.Dimension(381, 254));
				{
					jLabel1 = new JLabel();
					listPane.add(jLabel1);
					jLabel1.setText(whichList+" List:");
					jLabel1.setBounds(0, 0, 205, 16);
					jLabel1.setForeground(new java.awt.Color(255,0,0));
				}
				{
					DefaultListModel listModel = new DefaultListModel();
					list = new JList();
					AnchorLayout listLayout = new AnchorLayout();
					list.setLayout(listLayout);
					listPane.add(list);
					list.setModel(listModel);
					list.setBounds(0, 22, 381, 232);
					list.setBackground(new java.awt.Color(192,192,192));
					list.setFocusable(false);
					
					//after getting the proper song, close the window, return to the play back window.
					list.addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							songName = (String) list.getSelectedValue();
							
							//close window
							Window win = SwingUtilities.getWindowAncestor(jPanel1);
							win.dispose(); //exit program.
						}
					});
				}
			}
			{
				jPanel1 = new JPanel();
				AnchorLayout jPanel1Layout = new AnchorLayout();
				getContentPane().add(jPanel1, new AnchorConstraint(826, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jPanel1.setBackground(new java.awt.Color(0,0,0));
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(381, 54));
				{
					exit = new JButton();
					jPanel1.add(exit, new AnchorConstraint(138, 673, 916, 334, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					exit.setText("Back");
					exit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/back-icon.png")));
					exit.setFocusable(false);
					exit.setPreferredSize(new java.awt.Dimension(129, 42));
					exit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Window win = SwingUtilities.getWindowAncestor(jPanel1);
							win.dispose(); //exit program.
						}
					});
				}
			}
			
			//Show the proper list to the list screen.
			switch (i){
			case 0:
				list.setListData(swingList);
				break;
			case 1:
				list.setListData(jazzList);
				break;
			case 2:
				list.setListData(rockList);
				break;
			case 3:
				list.setListData(latinList);
				break;
			case 4:
				list.setListData(popList);
				break;
				default :
					System.out.println("Something wrong in get which list.");
			}
			
			
			
			pack();
			this.setSize(397, 346);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	/**
	 * According to the name of list, return an integer value in order to 
	 * easy to use switch (string is not supported for switch). 
	 * @return
	 */
	public int getWhichList(){
		if (whichList.equals("swing"))
			return 0;
		else
			if (whichList.equals("jazz"))
				return 1;
			else
				if (whichList.equals("rock"))
					return 2;
				else
					if (whichList.equals("funk"))
						return 3;
					else
						if (whichList.equals("latin"))
							return 4;
						else
							if (whichList.equals("pop"))
								return 5;
							else
								return 2;
		
	}
	
	/**
	 * method: public void getListFile( File )
	 * usage: read the outer file for play list.
	 */

	public void getListFile( File file ){
		try{
			//construct input Streams.
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader( new InputStreamReader(dis));
			int i = 0;
			String tmpString;
			
			while ( ( tmpString = br.readLine() )!= null ){
				if (tmpString.equals(""))
					continue;
				
				//get swing list
				if (tmpString.equals("swing")){
					i = 0;
					tmpString = br.readLine();
					while (!tmpString.equals("")){
						//tmpString == "" means new list found.
					swingList[i] = new String();	
					swingList[i] = tmpString; 
					i++;	//next catching
					tmpString = br.readLine();
					if (tmpString == null)
						break;
					}
					tmpString = br.readLine();
				}
				
				//get jazz list
				if (tmpString.equals("jazz")){
					i = 0;
					tmpString = br.readLine();
					while (!tmpString.equals("")){
						//tmpString == "" means new list found.
					jazzList[i] = new String();	
					jazzList[i] = tmpString; 
					i++;	//next user catching
					tmpString = br.readLine();
					if (tmpString == null)
						break;
					}
					tmpString = br.readLine();
				}
				
				//get rock list
				if (tmpString.equals("rock")){
					i = 0;
					tmpString = br.readLine();
					while (!tmpString.equals("")){
						//tmpString == "" means new list found.
						rockList[i] = new String();	
						rockList[i] = tmpString; 
						i++;	//next user catching
						tmpString = br.readLine();
						if (tmpString == null)
							break;
						}
					tmpString = br.readLine();
					}
				
				//get funk list
				if (tmpString.equals("funk")){
					i = 0;
					tmpString = br.readLine();
					while (!tmpString.equals("")){
						//tmpString == "" means new list found.
						funkList[i] = new String();	
						funkList[i] = tmpString; 
						i++;	//next user catching
						tmpString = br.readLine();
						if (tmpString == null)
							break;
						}
					tmpString = br.readLine();
					}
				
				//get latin list
				if (tmpString.equals("latin")){
					i = 0;
					tmpString = br.readLine();
					while (!tmpString.equals("")){
						//tmpString == "" means new list found.
						latinList[i] = new String();	
						latinList[i] = tmpString; 
						i++;	//next user catching
						tmpString = br.readLine();
						if (tmpString == null)
							break;
						}
					tmpString = br.readLine();
					}
				//get pop list
				if (tmpString.equals("pop")){
					i = 0;
					tmpString = br.readLine();
					while (!tmpString.equals("")){
						//tmpString == "" means new list found.
						popList[i] = new String();	
						popList[i] = tmpString; 
						i++;	//next user catching
						tmpString = br.readLine();
						if (tmpString == null)
							break;
						}
					tmpString = br.readLine();
					}
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

}
