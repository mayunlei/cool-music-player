/**
 * ������Ļ������
 * ��������ع�������ʾ����Ķ���
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
	private final static int picsizewidth = 500;//ͼƬ���
	private final static int picsizeheight = 291;//ͼƬ�߶�
	private static Dimension screensize;//��Ļ��С
	public void prepareSplash() {
		splashWin = new JWindow(this);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("LOGON.JPG");
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		label.setBackground(Color.red);
		label.setVisible(true);
		splashWin.add(label);
		screensize = Toolkit.getDefaultToolkit().getScreenSize();//�����Ļ��С
		splashWin.setLocation((int) ((screensize.getWidth()-picsizewidth)/2), (int) ((screensize.getHeight()-picsizeheight)/2));//��֤������Ļ��ͼƬ��ʾ����Ļ������
		splashWin.setSize(picsizewidth, picsizeheight);
		splashWin.setVisible(true);
	}
	
	
	//������ӭ����
	public void startSplash(){
		splashWin.setVisible(true);
		splashWin.toFront();
	}
	
	//��ֹ��ӭ����
	public void stopSplash(){
		splashWin.dispose();
	}
	
	//���캯��
	WindowSplashFrame(){
	}
}
