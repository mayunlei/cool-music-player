import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import java.net.InetAddress;
import java.net.Socket;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.io.*;
import java.util.*;
/*
 * 小小作曲家键盘输入部分
 * 功能描述：独立完成键盘输入发音的全部功能
 * 调用：直接添加进JFrame类即可。a.add(new Keyinput())
 */

public class Keyinput 
{
	private static Synthesizer synthesizer = null;
	private static MidiChannel channel;
	private static Instrument instruments[];
	private static JComboBox instrumentChoice;
	private static int velocity = 70;
	static Keyboard keyboard = null;
	
	private static JTextArea opern;//乐谱文本区域
	private static JScrollPane maintext;
	private static JScrollBar maintext2;
	private static JButton record;//在乐谱文本区域下方的功能控制键
	private static JButton pause;
	private static JButton stop;
	private static JButton clear;
	private JButton open;
	private static JButton save;
	private static JButton playe;
	private static JButton output;
	private static JButton changestave;
	private static JButton changenotation;
	private static JSlider volumn;
	private static JSlider velocitys;
	
	private static JLabel show;//音符实时显示
	
	private static write writer;//多线程提取音符
	private static ArrayList opernlist;//动态数组，存歌曲信息
	private static read reader;
	private static boolean flagp=true;//判断标志，用于实现单键不同条件下的多功能
	private static boolean flagc=true;
	private static int temnum=0;
	
	private static int volum=100;//音量
	private static int veloc=100;//速度
	private static Hashtable labels=new Hashtable(2);
	private static Hashtable labels2=new Hashtable(2);
	//JTextField j = new JTextField("请在此处输入:-)");
	private static JPanel keypanel;
	private boolean hastyped=false;//保存保护
	private static JPanel kbPane;
	//一下这些变量有可能与外类交换
	private static String filename=null;
	
	private static Webkeyinput web;
	
	JPanel keyp;
	Keyinput()
	{
		web=new Webkeyinput();
		web.construct();
	}
	
	JPanel Keyboardp()
	{
		keypanel=new JPanel();
		keypanel.setSize(new Dimension(900,300));
		keypanel.setOpaque(false);
		//元件初始化
		writer=new write();
		reader=new read();
		opernlist=new ArrayList();
		keypanel.setFocusable(true);
		keypanel.setLayout(new BorderLayout());
		//this.setLayout(null);
		//this.setSize(700, 600);
		//cccccccccccccccccccccccccccccccccccccccccccccccc我所用的全部PANEL与元件
		JPanel instrumentPane = new JPanel(new FlowLayout());//下拉框
		instrumentPane.setOpaque(false);
		kbPane=new JPanel(new BorderLayout());//键盘
		kbPane.setOpaque(false);
		JPanel opernp=new JPanel();//用来装opern与其下方的功能控制键
		opernp.setOpaque(false);
		JPanel controlp=new JPanel();//用来装功能控制键
		controlp.setOpaque(false);
		JPanel westp=new JPanel();//用来装西侧模块
		westp.setOpaque(false);
		JPanel p=new JPanel();//用来装键盘
		p.setOpaque(false);
		westp.setLayout(new BorderLayout());
		JPanel centerp=new JPanel();//用来装中间模块
		centerp.setOpaque(false);
		centerp.setLayout(new GridLayout(3,1,10,15));
		JPanel eastp=new JPanel();//用来装东侧模块
		eastp.setOpaque(false);
		JPanel centert=new JPanel();
		centert.setOpaque(false);
		JLabel vol=new JLabel("记录速度");
		JLabel vel=new JLabel("播放速度");
		JLabel music=new JLabel("当前按键:");
		
		//设置字体
		Font font=new Font("宋体",Font.BOLD,13);
		Font font2=new Font("宋体",Font.BOLD,16);
		
		//两个滑块条
		volumn=new JSlider(1);
		volumn.setMajorTickSpacing(50);
		labels.put(new Integer(0),new JLabel("高"));
		labels.put(new Integer(100),new JLabel("低"));
		volumn.setMaximum(100);
		volumn.setMinimum(0);
		volumn.setLabelTable(labels);
		volumn.setPaintLabels(true);
	
		
		velocitys=new JSlider(1);
		velocitys.setMajorTickSpacing(50);
		velocitys.setMaximum(100);
		velocitys.setMinimum(0);
		labels2.put(new Integer(0),new JLabel("高"));
		labels2.put(new Integer(100),new JLabel("低"));
		velocitys.setLabelTable(labels2);
		velocitys.setPaintLabels(true);
	
		volumn.setPreferredSize(new Dimension(50,120));
		volumn.setOpaque(false);
		velocitys.setPreferredSize(new Dimension(50,120));
		velocitys.setOpaque(false);
		volumn.setFocusable(false);
		velocitys.setFocusable(false);
		volumn.addChangeListener(new ChangeListener(){
			
			 public void stateChanged(ChangeEvent e) {

				 JSlider temp=(JSlider)e.getSource();
				 volum=2*temp.getValue();
				 
			 }
		});
		velocitys.addChangeListener(new ChangeListener(){
			
			 public void stateChanged(ChangeEvent e) {

				 JSlider temp=(JSlider)e.getSource();
				 veloc=volum*2*temp.getValue()/100;
			 }
		});
		
		instrumentChoice = new JComboBox();
		
		opern=new JTextArea(3,20);
		opern.setFocusable(false);
		opern.setEditable(false);
		opern.setText(" ");
		
		opernp.setLayout(new BorderLayout());
		record=new JButton("记录");//按键初始化，同时屏蔽其对按键的监控
		record.setFocusable(false);
		record.setFont(font);
		record.addActionListener(new ActionListner());
		
		pause=new JButton("暂停");
		pause.setFocusable(false);
		pause.setFont(font);
		pause.addActionListener(new ActionListner());
		
		stop=new JButton("停止");
		stop.setFocusable(false);
		stop.setFont(font);
		stop.addActionListener(new ActionListner());
		
		clear=new JButton("清除");
		clear.addActionListener(new ActionListner());
		clear.setFocusable(false);
		clear.setFont(font);
		/*
		open=new JButton("打开");
		open.setFocusable(false);
		open.addActionListener(new ActionListner());
		*/
		save=new JButton("文件保存");
		save.setFocusable(false);
		save.setFont(font2);
		save.addActionListener(new ActionListner());
		
		playe=new JButton("播放");
		//playe.setFocusable(false);
		playe.addActionListener(new ActionListner());
		playe.setFont(font);
		
		show=new JLabel("    ");
		show.setFocusable(false);
		show.setFont(font);
		
		output=new JButton("音频输出");
		output.setBounds(10, 10, 100, 30);
		output.setFocusable(false);
		output.setFont(font2);
		output.addActionListener(new ActionListner());
		
		changestave=new JButton("网络连接");
		changestave.setBounds(10, 50, 100, 30);
		changestave.setFocusable(false);
		changestave.setFont(font2);
		changestave.addActionListener(new ActionListner());
		
		changenotation=new JButton("文件保存");
		changenotation.setBounds(10, 100, 100, 30);
		changenotation.setFocusable(false);
		changenotation.setFont(font2);
		changenotation.addActionListener(new ActionListner());
		
		//开始装入PANEL
		//音色选择下拉框cccccccccccccccccccc下拉框装入PANEL
		instrumentChoice.setMaximumRowCount(5);
		instrumentChoice.setPreferredSize(new Dimension(300,30));
		instrumentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		instrumentPane.add(instrumentChoice);
		instrumentPane.add(music);
		instrumentPane.add(show);
		instrumentPane.setBorder(new TitledBorder("声音切换"));
		//instrumentPane.setFont(font);
	    westp.add(instrumentPane);
		//	instrumentPane.setBounds(10, 10, 400, 50);
	//	this.add(instrumentPane);
		
		//键盘ccccccccccccccccccccccccccccc键盘装入PANEL
		kbPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		keyboard=new Keyboard();
		kbPane.add(keyboard);
		//kbPane.setBackground(Color.gray);
		p.add(kbPane);
		//p.setBackground(Color.gray);
		//kbPane.setBounds(10, 300,600, 200);
		keypanel.add(p,BorderLayout.SOUTH);
		//曲谱文件cccccccccccccccccccccccccc区域装入PANEL
		opern.setLineWrap(true);
		opern.setWrapStyleWord(true);
		maintext=new JScrollPane(opern);
		maintext2=maintext.getVerticalScrollBar();
		opernp.add(maintext);
		opernp.setBorder(new TitledBorder("曲谱"));
		
		controlp.add(record);
		controlp.add(pause);
		controlp.add(stop);
		controlp.add(clear);
		//controlp.add(open);
		//controlp.add(save);
		controlp.add(playe);
		opernp.add(controlp,BorderLayout.SOUTH);
		
		
		westp.add(opernp,BorderLayout.SOUTH);
		//opernp.setBounds(10, 60, 400, 200);
		keypanel.add(westp,BorderLayout.WEST);	
		
		//文件输入输出部分ccccccccccccccccccc装入PANEL
		centerp.add(output);
		centerp.add(changestave);
		centerp.add(save);
		centerp.setPreferredSize(new Dimension(150,150));
		centert.add(centerp);
		centert.setBorder(new TitledBorder("文件操作"));
		keypanel.add(centert,BorderLayout.CENTER);
		//调节滑动棒ccccccccccccccccccccccc装入PANEL
		eastp.add(volumn);
		eastp.add(velocitys);
	    eastp.add(vol);
	    eastp.add(vel);
	    
		eastp.setPreferredSize(new Dimension(150,150));
		eastp.setBorder(new TitledBorder("速度调节"));
		keypanel.add(eastp,BorderLayout.EAST);
		
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
		keypanel.addKeyListener(new KeyListener()
		{
			Key pressedKey;
			public void keyPressed(KeyEvent e)
			{
				    int a;
					a=e.getKeyCode();
					pressedKey=keyboard.getMyKey(a);
				if(pressedKey==null)return;
			    if(pressedKey.Isdoing())pressedKey=null;
				if(pressedKey==null)return;
				
				else{
				show.setText(outtrans(pressedKey.noteNumber));
				pressedKey.press();
			    keyboard.repaint();
				}
			}
			
			public void keyReleased(KeyEvent e)
			{
				    int a;
					a=e.getKeyCode();
					pressedKey=keyboard.getMyKey(a);
					
			    if(pressedKey!=null)
			    {
			    pressedKey.release();
			  //  show.setText("    ");
			    keyboard.repaint();
			    }
			}
			public void keyTyped(KeyEvent e)
			{
				
			}
		});
	return keypanel;
	}
	
	void play()//播放函数
	{
		for(int i=0;i<opernlist.size();++i)
		{
			if(i>0){
			if(!opernlist.get(i).equals(opernlist.get(i-1)))
			
			System.out.print(opernlist.get(i));
			else
			System.out.print(" ");
			}
			else 
			System.out.println(opernlist.get(i));
		}
		
	}
	
	static String outtrans(int a)//输出转化，规范音符的输出
	{
		switch(a)
		{
		case 36:return "  1.";
		case 37:return " #1.";
		case 38:return "  2.";
		case 39:return " #2.";
		case 40:return "  3.";
		case 41:return "  4.";
		case 42:return " #4.";
		case 43:return "  5.";
		case 44:return " #5.";
		case 45:return "  6.";
		case 46:return " #6.";
		case 47:return "  7.";
		case 48:return "   1";
		case 49:return "  #1";
		case 50:return "   2";
		case 51:return "  #2";
		case 52:return "   3";
		case 53:return "   4";
		case 54:return "  #4";
		case 55:return "   5";
		case 56:return "  #5";
		case 57:return "   6";
		case 58:return "  #6";
		case 59:return "   7";
		case 60:return "  1'";
		case 61:return " #1'";
		case 62:return "  2'";
		case 63:return " #2'";
		case 64:return "  3'";
		case 65:return "  4'";
		case 66:return " #4'";
		case 67:return "  5'";
		case 68:return " #5'";
		case 69:return "  6'";
		case 70:return " #6'";
		case 71:return "  7'";
		case 72:return " 1''";
		case 73:return "#1''";
		case 74:return " 2''";
		case 75:return "#2''";
		case 76:return " 3''";
		case 77:return " 4''";
		case 78:return "#4''";
		case 79:return " 5''";
		case 80:return "#5''";
		case 81:return " 6''";
		case 82:return "#6''";
		case 83:return " 7''";
		}
		return "_";
	}
	
	/////////////////////////////////////////////////////////////////
	//////////开始内部类//////////////////////////////////////////
	////////////////////////////////////////////////////////////
	//内部类，多线程，实现播放，每0.1秒判断一次操作
	static class read extends Thread
	{
		int i=0;
		int j=0;
		read()
		{
			super();
			
		}
		public void run()
		{
			
			if(opernlist.size()>0)
			{
				 record.setEnabled(false);
				 playe.setEnabled(false);
				 clear.setEnabled(false);
				// open.setEnabled(false);
				 save.setEnabled(false);
			do
			{
				try
			    {
				 sleep(veloc);
				
				if(i>0){
					if(!opernlist.get(i).equals(opernlist.get(i-1)))
					{
						channel.noteOff(Integer.parseInt((String)opernlist.get(i-1)), velocity);
						channel.noteOn(Integer.parseInt((String)opernlist.get(i)), velocity);
					}
					}
					else 
					{
						channel.noteOn(Integer.parseInt((String)opernlist.get(i)), velocity);
                        				
					}
					++i;
					if(j!=0)
						{
						channel.noteOff(Integer.parseInt((String)opernlist.get(i-1)), velocity);
						break;
						}
					if(i==opernlist.size())
					{
						channel.noteOff(Integer.parseInt((String)opernlist.get(i-1)), velocity);
						break;
					}
				}
			catch(InterruptedException d){
					 d.printStackTrace();
			      }
			}while(i<opernlist.size());
			record.setEnabled(true);
			 playe.setEnabled(true);
			 clear.setEnabled(true);
		//	 open.setEnabled(true);
			 save.setEnabled(true);
		}
		}
		
	}
	//内部类，多线程，实现记录同步，每0.1秒采样一次
	static class write extends Thread
	{
		
		int p=1;
        int f=0;
        int j=0;
        int t=0;
		write()
		{
		super();
		}
        
		public void run()
		{
			do
			{
				f=0;
			try
			    {
			   for(int i = 0; i < keyboard.whiteKeys.length; i++)
			   {
				if (keyboard.whiteKeys[i].Isdoing())
				{
					opernlist.add(String.valueOf(keyboard.whiteKeys[i].noteNumber));
					if(opernlist.size()>1)
					{
						if(!opernlist.get(opernlist.size()-1).equals(opernlist.get(opernlist.size()-2)))
						opern.append(outtrans(keyboard.whiteKeys[i].noteNumber));
						else
						opern.append("~");
						
					}
					maintext2.setValue(maintext2.getMaximum()); 
					f=1;
				}
			   
			   }
			   for(int i = 0; i < keyboard.blackKeys.length; i++)
			   {
				if (keyboard.blackKeys[i].Isdoing())
				{
					opernlist.add(String.valueOf(keyboard.blackKeys[i].noteNumber));
					if(opernlist.size()>1)
					{
					if(!opernlist.get(opernlist.size()-1).equals(opernlist.get(opernlist.size()-2)))
						opern.append(outtrans(keyboard.blackKeys[i].noteNumber));
						else
						opern.append("~");
					
					
					}
					maintext2.setValue(maintext2.getMaximum()); 
					f=1;
				}
			   }
			   if(f==0)
			   {
				   opernlist.add("0");
				   opern.append("_");
				   
			   }
			   ++t;
			   if(t==20)
			   {
				   t=0;
				   opern.append("||");
			   }
			   sleep(volum);
			    }catch(InterruptedException d){
					 d.printStackTrace();
			      }
			}while(p!=0&&j==0);
		}
	}
	
	
	//内部类，鼠标点按键的处理
	static class ActionListner implements ActionListener
	{
		 public void actionPerformed(ActionEvent e)
		 {
			 if(e.getSource()==record)//记录键，如果按得是暂停，则接着录，若是停止则从头录
			 {
				 flagc=true;//按键切换值
				 
				 playe.setEnabled(false);
				 clear.setEnabled(false);
				// open.setEnabled(false);
				 save.setEnabled(false);
				// record.setEnabled(false);
				 record.setForeground(Color.red);
				 
				if(flagp)opernlist.removeAll(opernlist);
				{
					writer=new write();
					writer.start();
				}
			 }
			 if(e.getSource()==clear)
			 {
				opern.setText("");
				opernlist.removeAll(opernlist);
			 }
			 if(e.getSource()==pause)//可分别对记录与播放两种状态操作
			 {
				 flagp=false;
				
				 playe.setEnabled(true);
				 clear.setEnabled(true);
				 //open.setEnabled(true);
				 save.setEnabled(true);
				 record.setForeground(Color.black);
				 
				 if(flagc)
				 writer.j=1;
				 else
				 {
					 record.setEnabled(true);
					 reader.j=1;
					 temnum=reader.i;
					
				 }
				 
			 }
			 if(e.getSource()==stop)
			 {
				 flagp=true;
				
				 playe.setEnabled(true);
				 clear.setEnabled(true);
				 //open.setEnabled(true);
				 save.setEnabled(true);
				 record.setForeground(Color.black);
				 if(flagc)
				 writer.j=1;//interrupt?
				 else	 
				 {
					 record.setEnabled(true);
					 reader.j=1;
					 temnum=0;
				 }
				// opernlist.removeAll(opernlist);
			 }
			 if(e.getSource()==playe)
			 {
				 flagc=false;
				 reader=new read();
				 reader.i=temnum;
				 //play();//interrupt?
				
			     reader.start();
			 }
			 
			 if(e.getSource()==changestave)
			 {
				 web.a.setVisible(true);
			 }
			 
			 if(e.getSource()==save)
			 {
				
				    String temptext;
		    		if(filename==null)
					{
						filename=JOptionPane.showInputDialog("请输入文件名","text1");
						filename=filename+".txt";
					}
		    		if(!filename.equals("null.txt"))
		    		{
		    		    try
		    		    {		    			  
		    			  FileWriter fout=new FileWriter(filename);
		    				BufferedWriter bw=new BufferedWriter(fout);
		    				
		    				PrintWriter pw=new PrintWriter(bw);
		    					temptext=opern.getText(); 
		    				   int length=temptext.length();
		    				   for(int i=0;i<length;++i)
		    				   {
		    					pw.print(temptext.charAt(i));
		    				    if(i%60==0)
		    					pw.println("\r\n");
		    				   }
		    				
		    				pw.close();
		    				bw.close();
		    				fout.close();
		    				JOptionPane.showMessageDialog(null, "已保存");

		    		}catch(IOException ioe){
		    			System.err.println(e);
		    		}
		    		catch(Exception ex)
		    		{
		    			  ex.printStackTrace(); 
		    		}
		    		}
		    		else filename=null;
			 }
			 
			 
		 }
	}
	
	//内部类，键盘的处理
	static class Keyboard extends JPanel
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
							+whiteIDs[j],Color.WHITE,alphas[i*7+j]);
				    if(j==0||j==3)continue;
				    else{
				    	blackKeys[blackKeyIndex++]=new Key(position-Key.width/4
				    			,0,keyNum+blackIDs[j],Color.BLACK,aplhas[count++]);
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
					keypanel.requestFocus();
					pressedKey.press();
					repaint();
				}
				public void mouseReleased(MouseEvent e)
				{
					if(pressedKey!=null)
					{
						pressedKey.release();
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
	static class Key extends Rectangle
	{
		final static int width=25;
		final static int height=120;
		private boolean keydown=false;
		int noteNumber;
		private Color color;
		String alpha;
		boolean Isdoing()
		{
			return keydown;//判断是否按下，按下则屏蔽以支持长按
		}
		
		public Key(int x,int y,int num,Color color,String alpha)
		{
			//如果是黑键，长宽除以二。
			super(x,y,color.equals(Color.WHITE)?width:width/2,
					color.equals(Color.white)?height:height/2);
		    this.color=color;
		    this.noteNumber=num;
		    this.alpha=alpha;
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
	
	
	
	
	
	
	
	
	
	
	
	

	public class Webkeyinput {

		//声音处理部分
		private Synthesizer synthesizer = null;
		private MidiChannel channel;
		private Instrument instruments[];
		private JComboBox instrumentChoice;
		//private static int velocity = 70;
		Keyboard keyboard2 = null;
		
		//网络连接部分
		DataOutputStream dos=null;
		String ip;
		JLabel time;
		clock timer;
		JFrame a;
		
		//按键可视化部分
		JPanel webp;
		JPanel kbPane;
		JPanel instrumentPane;
		
		JPanel msouth;
		JPanel east;
		JPanel easts;
		JPanel center;
		JPanel west;
		
		JTextArea texta;
		JTextArea textu;
		JTextField textf;
		JButton send;
		JButton link;
		JButton disconnect;
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
			kbPane=new JPanel();
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
			
			send=new JButton("发送");
			send.addActionListener(new ClickListener());
			link=new JButton("连接");
			link.addActionListener(new ClickListener());
			disconnect=new JButton("断开");
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
					InetAddress addr=InetAddress.getLocalHost();
					ip=addr.getHostAddress();
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
					texta.append("\n"+msg);
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
								  texta.append("\n"+msg);
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
			a=new JFrame();
			Webkeyinput b=new Webkeyinput();
			a.getContentPane().add(b.webpart());
			a.setBounds(300, 100,900, 450);
		    //a.setVisible(true);
		}
		
		
		
		
	}
	
	
	
	
	
	
	/*public static void main(String []args)
	{
		JFrame a=new JFrame();
		a.setContentPane(new MyPanel());
		Keyinput b=new Keyinput();
		JPanel t=new JPanel();
		t.setOpaque(false);
		t.add(b.Keyboardp());//ccccccccccccccc最后又加了一层PANEL
		a.getContentPane().add(b.Keyboardp());
		a.setBounds(300, 100,900, 400);
	    a.setVisible(true);
	}*/
	
	
}

class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    super.paintComponent(g);
    Image img = Toolkit.getDefaultToolkit().getImage("9.jpg");
    g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
   }
  }
