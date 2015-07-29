package open;
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
 * An interface(frame) which shows user the record in text format.
 */
public class OpenFrame extends javax.swing.JFrame {
	private JPanel jPanel1;
	static JTextArea text;
	private JButton stop;
	private JButton play;

	public OpenFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Play your record");
			this.setFocusable(false);
			this.setFocusableWindowState(false);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setBackground(new java.awt.Color(0,0,0));
				jPanel1.setFocusable(false);
				{
					text = new JTextArea();
					jPanel1.add(text);
					text.setPreferredSize(new java.awt.Dimension(468, 316));
					text.setEditable(false);
					text.setLineWrap(true);
					text.setFocusable(false);
				}
				{
					play = new JButton();
					jPanel1.add(play);
					play.setText("play");
					play.setPreferredSize(new java.awt.Dimension(67, 23));
					play.setFocusable(false);
					play.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//play record
							OpenFile.playFile();
						}
					});
				}
				{
					stop = new JButton();
					jPanel1.add(stop);
					stop.setText("Stop");
					stop.setPreferredSize(new java.awt.Dimension(71, 23));
					stop.setFocusable(false);
					stop.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//stop play record
							if (OpenFile.timer != null)
								OpenFile.stopPlay();
						}
					});
				}
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
}
