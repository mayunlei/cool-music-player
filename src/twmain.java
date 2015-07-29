/**
 * �����������
 * ʵ�������洴��
 * ���ߺ�����
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
	//�������ȡ��߶ȡ���ʼ����
	static final int FRAME_WIDTH = 1100;
	static final int FRAME_HEIGHT = 750;
	static final int FRAME_POSITION_X = 50;
	static final int FRAME_POSITION_Y = 50;
	
	public static void main(String[] args) {
		WindowSplashFrame t = new WindowSplashFrame();//����һ����ӭ����
		t.prepareSplash();//��ӭ����׼��
		t.startSplash();//��ӭ��������
		JFrame frame = new JFrame("СС������");//���������洰��
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
		
		//��Ը������ܴ�����Ӧ�ı�ǩ
		//JPanel panel4 = jiazigu.jp();
		tabbedPane.addTab("��ӭҳ", new ImageIcon("icon1.png"), jiazigu.jp(), null);
		
		tabbedPane.addTab("����", new ImageIcon("icon2.png"), JianPu.jp(), null);
		
		JPanel panel2 = new Staff();
		tabbedPane.addTab("������", new ImageIcon("icon3.png"), panel2, null);
		
		Keyinput k = new Keyinput();
		final JPanel panel3 = k.Keyboardp();
		tabbedPane.addTab("���ټ���", new ImageIcon("icon4.png"), panel3, null);
		
		tabbedPane.setSelectedIndex(0);
		
		
		//���ټ�����Ҫ��ӽ���
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
		frame.setVisible(true);//��ʾ������
		t.stopSplash();//���ٻ�ӭ����
	}
}
