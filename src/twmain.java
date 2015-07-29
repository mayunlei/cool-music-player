/**
 * 软件主界面类
 * 实现主界面创建
 * 作者胡凯翔
 */



import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class twmain {
	//主界面宽度、高度、起始坐标
	static final int FRAME_WIDTH = 1100;
	static final int FRAME_HEIGHT = 750;
	static final int FRAME_POSITION_X = 50;
	static final int FRAME_POSITION_Y = 50;
	
	public static void main(String[] args) {
		WindowSplashFrame t = new WindowSplashFrame();//创建一个欢迎界面
		t.prepareSplash();//欢迎界面准备
		t.startSplash();//欢迎界面启动
		JFrame frame = new JFrame("小小作曲家");//创建主界面窗体
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,1));
		frame.setBounds(new Rectangle(FRAME_POSITION_X,FRAME_POSITION_Y,FRAME_WIDTH,FRAME_HEIGHT));
		try {
			@SuppressWarnings("rawtypes")
			Class lafClass = Class.forName("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		    LookAndFeel laf = (LookAndFeel)(lafClass.newInstance());
		    frame.setUndecorated(laf.getSupportsWindowDecorations());
		    frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
			UIManager.setLookAndFeel(laf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//针对各个功能创建相应的标签
		//JPanel panel4 = jiazigu.jp();
		tabbedPane.addTab("欢迎页", new ImageIcon("icon1.png"), jiazigu.jp(), null);
		
		tabbedPane.addTab("简谱", new ImageIcon("icon2.png"), JianPu.jp(), null);
		
		JPanel panel2 = new Staff();
		tabbedPane.addTab("五线谱", new ImageIcon("icon3.png"), panel2, null);
		
		Keyinput k = new Keyinput();
		final JPanel panel3 = k.Keyboardp();
		tabbedPane.addTab("钢琴键盘", new ImageIcon("icon4.png"), panel3, null);
		
		tabbedPane.setSelectedIndex(0);
		
		
		//钢琴键盘需要添加焦点
		tabbedPane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				JTabbedPane tmp = new JTabbedPane();
				tmp = (JTabbedPane) arg0.getSource();
				int i = tmp.getSelectedIndex();
				if(i == 3){
					panel3.requestFocus(true);
				}
			}
		});
		
		frame.add(tabbedPane);
		frame.setVisible(true);//显示主界面
		t.stopSplash();//销毁欢迎界面
	}
}
