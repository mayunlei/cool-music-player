/**
 * 软件欢迎界面类
 * 实现架子鼓界面创建
 * 作者胡凯翔（仅融合界面，架子鼓本体作者阮光平）
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

public class jiazigu {
	static JPanel panel3 = new JPanel();
	static JPanel center = new JPanel();
	static JPanel east = new JPanel();
	static JPanel west = new JPanel();
	static JPanel north = new JPanel();
	static JPanel south = new JPanel();
	static JPanel up = new JPanel();
	static JPanel pup1 = new JPanel();
	static JPanel pup2 = new JPanel();
	static JLabel pup1l = new JLabel();
	static JLabel pup2l = new JLabel();
	static JPanel pup1lp = new JPanel();
	static JPanel pup2lp = new JPanel();
	static JPanel down = new JPanel();
	static JPanel pdown1 = new JPanel();
	static JPanel pdown2 = new JPanel();
	static JPanel pdown3 = new JPanel();
	static JPanel pdown4 = new JPanel();
	static JLabel pdown1l = new JLabel();
	static JLabel pdown2l = new JLabel();
	static JLabel pdown3l = new JLabel();
	static JLabel pdown4l = new JLabel();
	static JPanel pdown1lp = new JPanel();
	static JPanel pdown2lp = new JPanel();
	static JPanel pdown3lp = new JPanel();
	static JPanel pdown4lp = new JPanel();
	static JLabel wuxianpu = new JLabel("五线谱帮助");
	static JLabel jianpan = new JLabel("键盘帮助");
	static JLabel jianpu = new JLabel("简谱帮助");
	static JLabel gu = new JLabel("架子鼓帮助");
	
	//将架子鼓框架融合到主体界面中
	public static JPanel jp(){
		panel3.setLayout(new BorderLayout());
		panel3.add(west, BorderLayout.WEST);
		panel3.add(east, BorderLayout.EAST);
		panel3.add(north, BorderLayout.NORTH);
		panel3.add(south, BorderLayout.SOUTH);
		panel3.add(center, BorderLayout.CENTER);
		west.setPreferredSize(new Dimension(40,1));
		east.setPreferredSize(new Dimension(40,1));
		north.setPreferredSize(new Dimension(1,40));
		south.setPreferredSize(new Dimension(1,40));
		center.setLayout(new GridLayout(2,1,40,40));
		center.add(up);
		center.add(down);
		up.setLayout(new GridLayout(1,2,40,40));
		up.add(pup1);
		up.add(pup2);
		pup1.setLayout(new BorderLayout());
		pup1.add(pup1lp, BorderLayout.CENTER);
		pup1lp.setPreferredSize(new Dimension(300,300));
		pup1lp.add(pup1l);
		pup1l.setIcon(new ImageIcon("up1.png"));
		pup2.setLayout(new BorderLayout());
		pup2.add(pup2lp, BorderLayout.CENTER);
		pup2lp.setPreferredSize(new Dimension(300,300));
		pup2lp.add(pup2l);
		pup2l.setIcon(new ImageIcon("up2.png"));
		pup1.setBorder(BorderFactory.createEtchedBorder());
		pup2.setBorder(BorderFactory.createEtchedBorder());
		pup1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				Webkeyinput b = new Webkeyinput();
				b.construct();
			}
			public void mousePressed(MouseEvent e){
				pup1.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			public void mouseReleased(MouseEvent e){
				pup1.setBorder(BorderFactory.createEtchedBorder());
			}
			public void mouseEntered(MouseEvent e){
				pup1.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			public void mouseExited(MouseEvent e){
				pup1.setBorder(BorderFactory.createEtchedBorder());
			}
		});
		pup2.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				Main jiazi = new Main();
				jiazi.setVisible(true);
				jiazi.setLocation(200, 200);				
			}
			public void mousePressed(MouseEvent e){
				pup2.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			public void mouseReleased(MouseEvent e){
				pup2.setBorder(BorderFactory.createEtchedBorder());
			}
			public void mouseEntered(MouseEvent e){
				pup2.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			public void mouseExited(MouseEvent e){
				pup2.setBorder(BorderFactory.createEtchedBorder());
			}
		});
		down.setLayout(new GridLayout(1,4,40,40));
		down.add(pdown1);
		down.add(pdown2);
		down.add(pdown3);
		down.add(pdown4);
		pdown1.setBorder(BorderFactory.createEtchedBorder());
		pdown2.setBorder(BorderFactory.createEtchedBorder());
		pdown3.setBorder(BorderFactory.createEtchedBorder());
		pdown4.setBorder(BorderFactory.createEtchedBorder());
		pdown1.setLayout(new BorderLayout());
		pdown1.add(jianpu, BorderLayout.NORTH);
		pdown1.add(pdown1lp, BorderLayout.CENTER);
		jianpu.setPreferredSize(new Dimension(1,40));
		jianpu.setFont(new Font("华文彩云", Font.BOLD, 40));
		pdown1lp.add(pdown1l);
		pdown1l.setIcon(new ImageIcon("down1.png"));
		pdown1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				new Help("简谱帮助", rd("jianpu"));
			}
			public void mousePressed(MouseEvent e){
				pdown1.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			public void mouseReleased(MouseEvent e){
				pdown1.setBorder(BorderFactory.createEtchedBorder());
			}
			public void mouseEntered(MouseEvent e){
				pdown1.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			public void mouseExited(MouseEvent e){
				pdown1.setBorder(BorderFactory.createEtchedBorder());
			}
		});
		
		pdown2.setLayout(new BorderLayout());
		pdown2.add(wuxianpu, BorderLayout.NORTH);
		pdown2.add(pdown2lp, BorderLayout.CENTER);
		wuxianpu.setPreferredSize(new Dimension(1,40));
		wuxianpu.setFont(new Font("华文彩云", Font.BOLD, 40));
		pdown2lp.add(pdown2l);
		pdown2l.setIcon(new ImageIcon("down2.png"));
		pdown2.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				new Help("五线谱帮助", rd("wuxianpu"));
			}
			public void mousePressed(MouseEvent e){
				pdown2.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			public void mouseReleased(MouseEvent e){
				pdown2.setBorder(BorderFactory.createEtchedBorder());
			}
			public void mouseEntered(MouseEvent e){
				pdown2.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			public void mouseExited(MouseEvent e){
				pdown2.setBorder(BorderFactory.createEtchedBorder());
			}
		});
		
		pdown3.setLayout(new BorderLayout());
		pdown3.add(jianpan, BorderLayout.NORTH);
		pdown3.add(pdown3lp, BorderLayout.CENTER);
		jianpan.setPreferredSize(new Dimension(1,40));
		jianpan.setFont(new Font("华文彩云", Font.BOLD, 40));
		pdown3lp.add(pdown3l);
		pdown3l.setIcon(new ImageIcon("down3.png"));
		pdown3.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				new Help("键盘帮助", rd("jianpan"));
			}
			public void mousePressed(MouseEvent e){
				pdown3.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			public void mouseReleased(MouseEvent e){
				pdown3.setBorder(BorderFactory.createEtchedBorder());
			}
			public void mouseEntered(MouseEvent e){
				pdown3.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			public void mouseExited(MouseEvent e){
				pdown3.setBorder(BorderFactory.createEtchedBorder());
			}
		});
		
		pdown4.setLayout(new BorderLayout());
		pdown4.add(gu, BorderLayout.NORTH);
		pdown4.add(pdown4lp, BorderLayout.CENTER);
		gu.setPreferredSize(new Dimension(1,40));
		gu.setFont(new Font("华文彩云", Font.BOLD, 40));
		pdown4lp.add(pdown4l);
		pdown4l.setIcon(new ImageIcon("down4.png"));
		pdown4.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				new Help("架子鼓帮助", rd("jiazigu"));
			}
			public void mousePressed(MouseEvent e){
				pdown4.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			public void mouseReleased(MouseEvent e){
				pdown4.setBorder(BorderFactory.createEtchedBorder());
			}
			public void mouseEntered(MouseEvent e){
				pdown4.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			public void mouseExited(MouseEvent e){
				pdown4.setBorder(BorderFactory.createEtchedBorder());
			}
		});
		
		return panel3;
	}
	
	private static String rd(String name){
		String s = "";
		try {
			Scanner sw = new Scanner(new FileReader(new File(name+".htxt")));
			while(sw.hasNextLine()){
				s += sw.nextLine() + "\n";
			}
			sw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}	
}