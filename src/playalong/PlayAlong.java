package playalong;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;







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
 * PlayAlong interface(frame). User can select song list, genres from this interface.
 */
public class PlayAlong extends javax.swing.JFrame {
	/**
	 * Frame components
	 */
	private JPanel selectPane;
	private JButton swingbt;
	private JLabel picLabel;
	private JButton pause;
	private JButton stop;
	private JButton play;
	private JPanel controlPane;
	private JButton electricBt;
	private JButton latinBt;
	private JButton jazzBt;
	private JButton popBt;
	private JButton rockBt;
	/************************/

	//default constructor
	public PlayAlong() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setBackground(new java.awt.Color(0,0,0));
			this.setTitle("Play Along setting");
			this.setFocusable(false);
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			this.setFocusableWindowState(false);
			this.setResizable(false);
			{
				selectPane = new JPanel();
				getContentPane().add(selectPane, new AnchorConstraint(1, 1001, 796, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				selectPane.setLayout(null);
				selectPane.setPreferredSize(new java.awt.Dimension(484, 302));
				{
					swingbt = new JButton();
					selectPane.add(swingbt);
					swingbt.setText("Swing ");
					swingbt.setBounds(27, 20, 132, 62);
					swingbt.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Guitar-2-icon.png")));
					
					//User selected swing list, then PlayAlongList gets the proper list.
					swingbt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							
							//pop up the swing list window
							PlayAlongList lst = new PlayAlongList("swing");
							lst.setVisible(true);
						}
					});
				}
				{
					rockBt = new JButton();
					selectPane.add(rockBt);
					rockBt.setText("Rock");
					rockBt.setBounds(27, 107, 132, 63);
					rockBt.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Edge-icon.png")));
					
					//User selected rock list, then PlayAlongList gets the proper list.
					rockBt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							
							//pop up the swing list window
							PlayAlongList lst = new PlayAlongList("rock");
							lst.setVisible(true);
						}
					});
				}
				{
					popBt = new JButton();
					selectPane.add(popBt);
					popBt.setText("Pop");
					popBt.setBounds(285, 19, 128, 62);
					popBt.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/vinyl-icon.png")));
					
					//User selected pop list, then PlayAlongList gets the proper list.
					popBt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							
							//pop up the swing list window
							PlayAlongList lst = new PlayAlongList("pop");
							lst.setVisible(true);
						}
					});
				}
				{
					jazzBt = new JButton();
					selectPane.add(jazzBt);
					jazzBt.setText("Jazz");
					jazzBt.setBounds(285, 108, 128, 62);
					jazzBt.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/favourites-star-SH-icon.png")));
					
					//User selected jazz list, then PlayAlongList gets the proper list.
					jazzBt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							
							//pop up the swing list window
							PlayAlongList lst = new PlayAlongList("jazz");
							lst.setVisible(true);
						}
					});
				}
				{
					latinBt = new JButton();
					selectPane.add(latinBt);
					latinBt.setText("Latin");
					latinBt.setBounds(27, 210, 132, 64);
					latinBt.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/red-hot-chili-peppers-4-icon.png")));
					
					//User selected latin list, then PlayAlongList gets the proper list.
					latinBt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							
							//pop up the swing list window
							PlayAlongList lst = new PlayAlongList("latin");
							lst.setVisible(true);
						}
					});
				}
				{
					electricBt = new JButton();
					selectPane.add(electricBt);
					electricBt.setText("Electric");
					electricBt.setBounds(285, 210, 128, 64);
					electricBt.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/turntable-icon.png")));
				}
				{
					picLabel = new JLabel();
					AnchorLayout picLabelLayout = new AnchorLayout();
					picLabel.setLayout(picLabelLayout);
					selectPane.add(picLabel);
					picLabel.setBounds(0, 1, 494, 308);
					picLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/play.png")));
				}
			}
			{
				controlPane = new JPanel();
				AnchorLayout controlPaneLayout = new AnchorLayout();
				getContentPane().add(controlPane, new AnchorConstraint(796, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				controlPane.setBackground(new java.awt.Color(0,0,0));
				controlPane.setLayout(controlPaneLayout);
				controlPane.setPreferredSize(new java.awt.Dimension(484, 78));
				{
					play = new JButton();
					controlPane.add(play, new AnchorConstraint(160, 616, 724, 412, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					play.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Button-Play-icon.png")));
					play.setPreferredSize(new java.awt.Dimension(99, 44));

					//User selected play, then play the proper .wav file
					play.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							PlaySound.play(PlayAlongList.songName);
						}
					});
				}
				{
					pause = new JButton();
					controlPane.add(pause, new AnchorConstraint(301, 329, 865, 147, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					pause.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/button-cancel-icon.png")));
					pause.setPreferredSize(new java.awt.Dimension(88, 44));
					pause.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							stop.doClick();
							Window win = SwingUtilities.getWindowAncestor(controlPane);
							win.dispose(); //exit program.
						}
					});
				}
				{
					stop = new JButton();
					controlPane.add(stop, new AnchorConstraint(301, 864, 865, 689, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					stop.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/Button-Stop-icon.png")));
					stop.setPreferredSize(new java.awt.Dimension(85, 44));

					//User selected stop, then stop the proper .wav file
					stop.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//stop the sound
							if (PlaySound.clip!=null)
								PlaySound.stopIt();
						}
					});
				}
			}
			pack();
			this.setSize(500, 418);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
