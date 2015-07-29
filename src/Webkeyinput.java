import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.lang.*;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;


import java.net.*;



import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.*;

public class Webkeyinput {

	//声音处理部分
	private Synthesizer synthesizer = null;
	private MidiChannel channel;
	private Instrument instruments[];
	private JComboBox instrumentChoice;
	private static int velocity = 70;
	static Keyboard keyboard2 = null;
	
	//网络连接部分
	DataOutputStream dos=null;
	String ip;
	JLabel time;
	clock timer;
	JFrame a;
	
	//按键可视化部分
	JPanel webp;
	//JPanel kbPane;
	MyPanel2 kbPane;
	JPanel instrumentPane;
	
	JPanel msouth;
	JPanel east;
	JPanel easts;
	JPanel center;
	JPanel west;
	
	JTextArea texta;
	JTextArea textu;
	JTextField textf;
	BasicButton send;
	BasicButton link;
	BasicButton disconnect;
	JScrollPane maintext;
	JScrollPane maintext2;
	JScrollBar maintext3;
	JScrollBar maintext4;
	
	boolean endj=false;
	
	
	JPanel webpart()
	{
		//初始化
		webp=new JPanel();
		webp.setLayout(new BorderLayout());
		//kbPane=new JPanel();
		kbPane=new MyPanel2();
		instrumentPane=new JPanel();
		instrumentChoice=new JComboBox();
		time=new JLabel("           ");
		timer=new clock();
		
		//kbPane.setFocusable(false);
		
		msouth=new JPanel();
		msouth.setLayout(new BorderLayout());
		east=new JPanel();
		east.setLayout(new BorderLayout());
		easts=new JPanel();
		center=new JPanel(new GridLayout(2,1,0,20));
		west=new JPanel();
		
		send=new BasicButton("发送");
		send.addActionListener(new ClickListener());
		link=new BasicButton("连接");
		link.addActionListener(new ClickListener());
		disconnect=new BasicButton("断开");
		disconnect.addActionListener(new ClickListener());
		texta=new JTextArea(3,40);
		texta.setLineWrap(true);
		texta.setWrapStyleWord(true);
		
		
		
		textu=new JTextArea(5,20);
		textf=new JTextField(30);
		
		maintext=new JScrollPane(texta);
		texta.setEditable(false);
		maintext2=new JScrollPane(textu);
		textu.setEditable(false);
		maintext3=maintext.getVerticalScrollBar();
		maintext4=maintext2.getVerticalScrollBar();
		
		//开始装PANEL
		easts.add(textf);
		easts.add(send);
		east.add(maintext,BorderLayout.CENTER);
		east.add(easts,BorderLayout.SOUTH);
		east.setBorder(new TitledBorder("讨论区"));
		
		center.add(link);
		center.add(disconnect);
	
		
		JPanel t=new JPanel();
		t.add(center);
		t.setBorder(new TitledBorder("网络连接"));
		
		west.add(maintext2);
		west.setBorder(new TitledBorder("用户监控"));
		
		msouth.add(east, BorderLayout.WEST);
		msouth.add(t);
		msouth.add(west,BorderLayout.EAST);
		
		
		JPanel tem=new JPanel();
		tem.add(msouth);
		tem.setBorder(new TitledBorder("聊天室"));
		webp.add(tem,BorderLayout.SOUTH);
		
		//键盘构造
		kbPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		keyboard2=new Keyboard();
		kbPane.add(keyboard2);
		kbPane.setBorder(new TitledBorder("键盘"));
		
		
		//声音选择下拉框构造
		instrumentChoice.setMaximumRowCount(5);
		instrumentChoice.setPreferredSize(new Dimension(300,30));
		instrumentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		instrumentPane.add(instrumentChoice);
		//instrumentPane.setBorder(new TitledBorder("声音选项"));
	
		JPanel north=new JPanel();
		north.add(instrumentPane);
		north.add(time);
		timer.start();
	    webp.add(north,BorderLayout.NORTH);
	    webp.add(kbPane,BorderLayout.CENTER);
		
///////////////////////////////////////////////////////////////////////			
////////////键盘音乐合成的初始化设置/////////////////////////////////////			
///////////////////////////////////////////////////////////////////////		
	
try {//音乐合成器初始化，
	synthesizer = MidiSystem.getSynthesizer();
	synthesizer.open();
    } catch (Exception e) {
	System.out.println(e);
	System.exit(1);
    }
    //声道初始化
MidiChannel[] channels = synthesizer.getChannels();
for (int i = 0; i < channels.length; i++)
{
		if (channels[i] != null) {
			channel = channels[i];
			break;
		}
}  
//乐器初始化
instruments = synthesizer.getAvailableInstruments();
if (instruments.length == 0) {
	System.err.println("no instruments avaliable");
	System.exit(1);
}

for (int i = 0; i < Math.min(128, instruments.length); i++) {
	instrumentChoice.addItem(instruments[i]);
}
//乐器选择表
channel.programChange(instruments[0].getPatch().getProgram());
instrumentChoice.setSelectedIndex(0);
instrumentChoice.setFocusable(false);
instrumentChoice.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		Patch patch = ((Instrument) instrumentChoice.getSelectedItem())
				.getPatch();
		channel.programChange(patch.getBank(), patch.getProgram());
	}
});
//键盘按键事件

webp.addKeyListener(new KeyListener()
{
	Key pressedKey;
	String msg;
	public void keyPressed(KeyEvent e)
	{
		    int a;
			a=e.getKeyCode();
			pressedKey=keyboard2.getMyKey(a);
		if(pressedKey==null)return;
	    if(pressedKey.Isdoing())pressedKey=null;
		if(pressedKey==null)return;
		
		else{
		
		pressedKey.press();
		if(pressedKey.color==Color.WHITE)
		{
		msg="WD"+pressedKey.ratio;
		  try{
			  dos.writeUTF(msg);
			  dos.flush();
		  }catch(IOException de)
		    {
			  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
			  de.printStackTrace();
		    }
		}
		if(pressedKey.color==Color.BLACK)
		{
			msg="BD"+pressedKey.ratio;
			  try{
				  dos.writeUTF(msg);
				  dos.flush();
			  }catch(IOException de)
			    {
				  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
				  de.printStackTrace();
			    }
		}
		
	    keyboard2.repaint();
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		    int a;
			a=e.getKeyCode();
			pressedKey=keyboard2.getMyKey(a);
			
	    if(pressedKey!=null)
	    {
	    pressedKey.release();
	    if(pressedKey.color==Color.WHITE)
		{
		msg="WU"+pressedKey.ratio;
		 try{
			  dos.writeUTF(msg);
			  dos.flush();
			
		  }catch(IOException de)
		    {
			  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
			  de.printStackTrace();
		    }
		}
	    if(pressedKey.color==Color.BLACK)
		{
			msg="BU"+pressedKey.ratio;
			 try{
				  dos.writeUTF(msg);
				  dos.flush();
				
			  }catch(IOException de)
			    {
				  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
				  de.printStackTrace();
			    }
		}
	    keyboard2.repaint();
	    }
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
});

return webp;
	}
	
	//内部类，按钮的处理
	class ClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==link)
			{
				System.out.print("linking");
				endj=false;
				 try{
				ip="169.254.31.101";
				System.out.print(ip);
				
				clientStart(ip,2528); 
				  }catch(Exception d)
				   {
					  d.printStackTrace();
				   }
			}
			if(e.getSource()==disconnect)
			{
				String msg="88";
				endj=true;
				textu.append(ip+"下线\n");
				textu.append(""+timer.intYear+"年"+timer.intMonth+"月"+timer.intDate+"日"+timer.intHour+"点"+timer.intMinute+"分"+timer.intSecond+"秒\n");
				  maintext4.setValue(maintext4.getMaximum()); 
				try{
					  dos.writeUTF(msg);
					  dos.flush();
					
				  }catch(IOException d)
				    {
					  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
					  d.printStackTrace();
				    }
			}
			if(e.getSource()==send)
			{
				String msg=textf.getText();
				textf.setText("");
				texta.append(msg+"\n");
				 maintext3.setValue(maintext3.getMaximum()); 
				try{
					  dos.writeUTF(msg);
					  dos.flush();
					
				  }catch(IOException d)
				    {
					  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
					  d.printStackTrace();
				    }
			}
		}
	}
	
	 public void clientStart(String ip, int port)throws Exception
	  {
		  Socket s=new Socket(ip,port);
		  
		  dos=new DataOutputStream(new 
				  BufferedOutputStream(s.getOutputStream()));
		  
		  textu.append(ip+"上线\n");
		  textu.append(""+timer.intYear+"年"+timer.intMonth+"月"+timer.intDate+"日"+timer.intHour+"点"+timer.intMinute+"分"+timer.intSecond+"秒\n");
		  maintext4.setValue(maintext4.getMaximum()); 
		  try{
			  dos.writeUTF("i"+ip);
			  dos.flush();
			
		  }catch(IOException e)
		    {
			  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
			  e.printStackTrace();
		    }
		  
		  new MySocketReadServer(s).start();
	  }
	
	
	 class MySocketReadServer extends Thread
	  {
		  private Socket s;
		  int temnum;
		  
		  public MySocketReadServer(Socket s)
		  {
			  this.s=s;
		  }
		  
		  public void run()
		  {
			  try{
				  DataInputStream dis=new DataInputStream(new 
						  BufferedInputStream(s.getInputStream()));
				 boolean t=true;
				  while(true)
				  {
					   String msg=dis.readUTF();
					  if(endj)break;
					  if(msg.length()>0)
					  {
						  if(msg.substring(0,1).equals("W")&&msg.substring(1,2).equals("D"))
						  {
							  temnum=Integer.parseInt(msg.substring(2));
							  keyboard2.whiteKeys[temnum-1].press();
							  keyboard2.repaint();
							  t=false;
						  }
						  if(msg.substring(0,1).equals("W")&&msg.substring(1,2).equals("U"))
						  {
							  temnum=Integer.parseInt(msg.substring(2));
							  keyboard2.whiteKeys[temnum-1].release();
							  keyboard2.repaint();
							  t=false;
						  }
						  if(msg.substring(0,1).equals("B")&&msg.substring(1,2).equals("D"))
						  {
							  temnum=Integer.parseInt(msg.substring(2));
							  keyboard2.blackKeys[temnum-1].press();
							  keyboard2.repaint();
							  t=false;
						  }
						  if(msg.substring(0,1).equals("B")&&msg.substring(1,2).equals("U"))
						  {
							  temnum=Integer.parseInt(msg.substring(2));
							  keyboard2.blackKeys[temnum-1].release();
							  keyboard2.repaint();
							  t=false;
						  }
						  if(msg.substring(0,1).equals("i"))
						  {
							  textu.append(msg.substring(1)+"上线"+"\n");
							  textu.append(""+timer.intYear+"年"+timer.intMonth+"月"+timer.intDate+"日"+timer.intHour+"点"+timer.intMinute+"分"+timer.intSecond+"秒\n");
							  maintext4.setValue(maintext4.getMaximum()); 
							  t=false;
						  }
						  if(msg.substring(0,2).equals("88"))
						  {
							  textu.append(msg.substring(2)+"下线"+"\n");
							  textu.append(String.valueOf(timer.intYear)+"年"+
									  String.valueOf(timer.intMonth)+"月"+
									  String.valueOf(timer.intDate)+"日"+
									  String.valueOf(timer.intHour)+"点"+
									  String.valueOf(timer.intMinute)+"分"+
									  String.valueOf(timer.intSecond)+"秒\n");
							  maintext4.setValue(maintext4.getMaximum()); 
							  t=false;
						  }
						  if(t)
						  {
							  texta.append(msg+"\n");
							  maintext3.setValue(maintext3.getMaximum()); 
							
						  }
						  
						  t=true;
						 
					  }
					  
					
				  }
				  
				  textu.append(ip+"已退出聊天室");
			  }catch(Exception e){
				  System.out.println(ip+"已退出聊天室");
			  }
		  }
	  }
	
	//内部类，时钟的处理
	 class clock extends Thread
	 {
		    int   intYear ; 
			int   intMonth ; 
			int   intDate ; 
			int   intHour ; 
			int   intMinute ; 
			int   intSecond  ; 
		 
		 clock()
		 {
			 super();
		 }
		 
		 public void run()
		 {
			 
			 while(true)
			{
			try
			{
			   Calendar cccc=Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
			   intYear   =   cccc.get(cccc.YEAR); 
			   intMonth   =   cccc.get(cccc.MONTH); 
			   intDate   =   cccc.get(cccc.DATE); 
			   intHour   =   cccc.get(cccc.HOUR_OF_DAY); 
			   intMinute   =   cccc.get(cccc.MINUTE); 
			   intSecond   =   cccc.get(cccc.SECOND); 
			   intMonth++;
			
			   time.setText(intYear+"年"+intMonth+"月"+intDate+"日"+intHour+"点"+intMinute+"分"+intSecond+"秒");
			   sleep(1000);
			}catch(InterruptedException e){
				 e.printStackTrace();
			}
			   
		 }
		 }
	 }
	 
	 
	
	//内部类，键盘的处理
	class Keyboard extends JPanel
	{
		final int OCTAVES=4;//四个音阶
		Key[] whiteKeys=new Key[7*this.OCTAVES];
	    Key[] blackKeys=new Key[5*this.OCTAVES];
	    Key pressedKey;
	    
	    public Keyboard()
	    {
	    	this.setLayout(new BorderLayout());
	    	this.setPreferredSize(new Dimension(7*OCTAVES*Key.width,
	    			Key.height+1));
	    	int firstKeyNum=60-6*this.OCTAVES;//用于确定音色（那128个）
	    	int whiteIDs[]={0,2,4,5,7,9,11};
	    	int blackIDs[] = { 0, 1, 3, 0, 6, 8, 10 };
	    	String[] alphas = { "Z", "X", "C", "V", "B", "N", "M", "A", "S",
					"D", "F", "G", "H", "J", "Q", "W", "E", "R", "T", "Y", "U",
					"1", "2", "3", "4", "5", "6", "7" };
			String[] aplhas = { "f1", "f2", "f3", "f4", "f5", "f6", "f7", "8",
					"9", "0", "-", "=", "I", "O", "P", "[", "]", "K", "L",
					";" };
			
			int  position=0;
			int count=0;
			int whiteKeyIndex=0;
			int blackKeyIndex=0;
			for(int i=0;i<this.OCTAVES;++i)
			{
				int keyNum=i*12+firstKeyNum;
				for(int j=0;j<7;j++,position+=Key.width)
				{
					whiteKeys[whiteKeyIndex++]=new Key(position,0,keyNum
							+whiteIDs[j],Color.WHITE,alphas[i*7+j],whiteKeyIndex);
				    if(j==0||j==3)continue;
				    else{
				    	blackKeys[blackKeyIndex++]=new Key(position-Key.width/4
				    			,0,keyNum+blackIDs[j],Color.BLACK,aplhas[count++],blackKeyIndex);
				        }
				
				}
			}
			
			
			this.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e)
				{
					pressedKey=getKey(e.getPoint());
					if(pressedKey==null)
					{
						return;
					}
					webp.requestFocus();
					pressedKey.press();
					String msg;
					if(pressedKey.height==120)
					{
					msg="WD"+pressedKey.ratio;
					  try{
						  dos.writeUTF(msg);
						  dos.flush();
						
					  }catch(IOException de)
					    {
						  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
						  de.printStackTrace();
					    }
					}
					if(pressedKey.height<120)
					{
						msg="BD"+pressedKey.ratio;
						  try{
							  dos.writeUTF(msg);
							  dos.flush();
							
						  }catch(IOException de)
						    {
							  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
							  de.printStackTrace();
						    }
					}
					
					repaint();
				}
				public void mouseReleased(MouseEvent e)
				{
					if(pressedKey!=null)
					{
						pressedKey.release();
						String msg; 
						if(pressedKey.height==120)
							{
							msg="WU"+pressedKey.ratio;
							 try{
								  dos.writeUTF(msg);
								  dos.flush();
								
							  }catch(IOException de)
							    {
								  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
								  de.printStackTrace();
							    }
							}
							if(pressedKey.height<120)
							{
								msg="BU"+pressedKey.ratio;
								 try{
									  dos.writeUTF(msg);
									  dos.flush();
									
								  }catch(IOException de)
								    {
									  JOptionPane.showMessageDialog(msouth, "发送失败，请重新尝试");
									  de.printStackTrace();
								    }
							}
						repaint();
					}
					
				}
			});
	    }
			public Key getMyKey(int s)
			{
				switch(s)
				{
				case KeyEvent.VK_Z:return whiteKeys[0]; 
				case KeyEvent.VK_X:return whiteKeys[1]; 
				case KeyEvent.VK_C:return whiteKeys[2]; 
				case KeyEvent.VK_V:return whiteKeys[3]; 
				case KeyEvent.VK_B:return whiteKeys[4]; 
				case KeyEvent.VK_N:return whiteKeys[5]; 
				case KeyEvent.VK_M:return whiteKeys[6]; 
				case KeyEvent.VK_A:return whiteKeys[7]; 
				case KeyEvent.VK_S:return whiteKeys[8]; 
				case KeyEvent.VK_D:return whiteKeys[9]; 
				case KeyEvent.VK_F:return whiteKeys[10]; 
				case KeyEvent.VK_G:return whiteKeys[11]; 
				case KeyEvent.VK_H:return whiteKeys[12]; 
				case KeyEvent.VK_J:return whiteKeys[13]; 
				case KeyEvent.VK_Q:return whiteKeys[14]; 
				case KeyEvent.VK_W:return whiteKeys[15]; 
				case KeyEvent.VK_E:return whiteKeys[16]; 
				case KeyEvent.VK_R:return whiteKeys[17]; 
				case KeyEvent.VK_T:return whiteKeys[18]; 
				case KeyEvent.VK_Y:return whiteKeys[19]; 
				case KeyEvent.VK_U:return whiteKeys[20]; 
				case KeyEvent.VK_1:return whiteKeys[21]; 
				case KeyEvent.VK_2:return whiteKeys[22]; 
				case KeyEvent.VK_3:return whiteKeys[23]; 
				case KeyEvent.VK_4:return whiteKeys[24]; 
				case KeyEvent.VK_5:return whiteKeys[25]; 
				case KeyEvent.VK_6:return whiteKeys[26]; 
				case KeyEvent.VK_7:return whiteKeys[27];
				
				case KeyEvent.VK_F1:return blackKeys[0];
				case KeyEvent.VK_F2:return blackKeys[1];
				case KeyEvent.VK_F3:return blackKeys[2];
				case KeyEvent.VK_F4:return blackKeys[3];
				case KeyEvent.VK_F5:return blackKeys[4];
				case KeyEvent.VK_F6:return blackKeys[5];
				case KeyEvent.VK_F7:return blackKeys[6];
				case KeyEvent.VK_8:return blackKeys[7];
				case KeyEvent.VK_9:return blackKeys[8];
				case KeyEvent.VK_0:return blackKeys[9];
				case 45:return blackKeys[10];
				case 61:return blackKeys[11];
				case KeyEvent.VK_I:return blackKeys[12];
				case KeyEvent.VK_O:return blackKeys[13];
				case KeyEvent.VK_P:return blackKeys[14];
				case 91:return blackKeys[15];
				case 93:return blackKeys[16];
				case KeyEvent.VK_K:return blackKeys[17];
				case KeyEvent.VK_L:return blackKeys[18];
				case 59:return blackKeys[19];
				}
				return null;
			}
			public Key getKey(Point point)
			{
				for(int i=0;i<blackKeys.length;++i)
				{
					if(blackKeys[i].contains(point))
						return blackKeys[i];
				}
				for(int i=0;i<whiteKeys.length;++i)
				{
					if(whiteKeys[i].contains(point))
				        return whiteKeys[i];
				}
				return null;
			}
			public void paint(Graphics g)
			{
				Graphics2D g2d=(Graphics2D) g;
				for(int i=0;i<whiteKeys.length;++i)
					whiteKeys[i].draw(g2d);
				for(int i=0;i<blackKeys.length;++i)
					blackKeys[i].draw(g2d);
			}
	    
	}
	
	//内部类，单个按键的处理
	class Key extends Rectangle
	{
		final static int width=25;
		final static int height=120;
		private boolean keydown=false;
		int noteNumber;
		int ratio;
		private Color color;
		String alpha;
		boolean Isdoing()
		{
			return keydown;//判断是否按下，按下则屏蔽以支持长按
		}
		
		public Key(int x,int y,int num,Color color,String alpha,int i)
		{
			//如果是黑键，长宽除以二。
			super(x,y,color.equals(Color.WHITE)?width:width/2,
					color.equals(Color.white)?height:height/2);
		    this.color=color;
		    this.noteNumber=num;
		    this.alpha=alpha;
		    this.ratio=i;
		}
		//音符为noteNUMBER，衰减速度为velocity
		public void press()
		{
			keydown=true;
			channel.noteOn(noteNumber, velocity);
			
		}
		public void release()
		{
			keydown=false;
			channel.noteOff(noteNumber, velocity);
		}
		//按键的绘制
		public void draw(Graphics2D g2d)
		{
			g2d.setColor(keydown?Color.blue:color);
			g2d.fill(this);
			g2d.setColor(Color.BLACK);
			g2d.draw(this);
			if(color==Color.BLACK)
			{
				g2d.setColor(Color.WHITE);
				g2d.drawString(alpha,this.x+2,this.y+15);
			}
			else
			{
				g2d.drawString(alpha, this.x+2, this.y+95);
			}
		}
		
	}
	
	void construct()
	{
		a=new JFrame("网络版键盘");
		Webkeyinput b=new Webkeyinput();
		a.getContentPane().add(b.webpart());
		a.setBounds(300, 100,900, 450);
	    a.setVisible(true);
	}
	
	/*public static void main(String []args)
	{
		
		Webkeyinput b=new Webkeyinput();
		b.construct();
	}*/
	
	class MyPanel2 extends JPanel {
        public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g);
        Image img = Toolkit.getDefaultToolkit().getImage("01.jpg");
        
        
        g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        
       }
	}
	
	public static class BasicButton extends JButton {
		private static final long serialVersionUID = 1L;
		public static final Color BUTTON_COLOR1 = new Color(125, 161, 237);
		public static final Color BUTTON_COLOR2 = new Color(91, 118, 173);
		public static final Color BUTTON_BAK_COLOR1_1 = new Color(108, 135, 210, 179);
		public static final Color BUTTON_BAK_COLOR1_2 = new Color(108, 135, 210, 255);
		public static final Color BUTTON_BAK_COLOR2_1 = new Color(180, 230, 250, 179);
		public static final Color BUTTON_BAK_COLOR2_2 = new Color(180, 230, 250, 255);
		public static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
		private boolean hover;
		public BasicButton(String text){ 
			super(text);
			setBorderPainted(false);
			setFocusPainted(false);
			setContentAreaFilled(false);
			setForeground(BUTTON_FOREGROUND_COLOR);
			addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					hover = true;
					repaint();
				}
				public void mouseExited(MouseEvent e){
					hover = false;
					repaint();
				}
			});
		}
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			int h = getHeight();
			int w = getWidth();
			float tran = 1F;
			if (!hover) {
				tran = 0.7F;
			}
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			GradientPaint p1;
			GradientPaint p2;
			if (getModel().isPressed()) {
				p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100));
				p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 100));
			}
			else {
				p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0));
				p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
			}
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));
			Shape clip = g2d.getClip();
			RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, h, h);
			g2d.clip(r2d);
			GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F, h, BUTTON_COLOR2, true);
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, w, h);
			if (hover) {
				RoundRectangle2D.Float r2d2 = new RoundRectangle2D.Float(5, 2, w- 10, h / 2 - 1, h / 2, h / 2);
				g2d.clip(r2d2);
				GradientPaint gp2 = new GradientPaint(0.0F, 0.0F, BUTTON_BAK_COLOR2_2, 0.0F, h / 2, BUTTON_BAK_COLOR1_2, true);
				g2d.setPaint(gp2);
				g2d.fillRect(5, 2, w - 10, h / 2);
			}
			g2d.setClip(clip);
			g2d.setPaint(p1);
			g2d.drawRoundRect(0, 0, w - 1, h - 1, h, h);
			g2d.setPaint(p2);
			g2d.drawRoundRect(1, 1, w - 3, h - 3, h - 2, h - 2);
			g2d.dispose();
			super.paintComponent(g);
		}
	}
}

