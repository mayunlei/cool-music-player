/**
 * 启动屏幕生成类
 * 在软件加载过程中显示此类的对象
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;


@SuppressWarnings("serial")
public class WindowSplashFrame extends JFrame{
	private JWindow splashWin;
	private final static int picsizewidth = 500;//图片宽度
	private final static int picsizeheight = 291;//图片高度
	private static Dimension screensize;//屏幕大小
	public void prepareSplash() {
		splashWin = new JWindow(this);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("LOGON.JPG");
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		label.setBackground(Color.red);
		label.setVisible(true);
		splashWin.add(label);
		screensize = Toolkit.getDefaultToolkit().getScreenSize();//获得屏幕大小
		splashWin.setLocation((int) ((screensize.getWidth()-picsizewidth)/2), (int) ((screensize.getHeight()-picsizeheight)/2));//保证启动屏幕的图片显示在屏幕正中心
		splashWin.setSize(picsizewidth, picsizeheight);
		splashWin.setVisible(true);
	}
	
	
	//启动欢迎界面
	public void startSplash(){
		splashWin.setVisible(true);
		splashWin.toFront();
	}
	
	//终止欢迎界面
	public void stopSplash(){
		splashWin.dispose();
	}
	
	//构造函数
	WindowSplashFrame(){
	}
}
