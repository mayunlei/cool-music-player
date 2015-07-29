/**
 * 本文档包含以下内容
 * 一个五线谱类，一个音符类
 * 作者马云雷
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;

import org.jfugue.*;


@SuppressWarnings("serial")
public class Staff extends JPanel {

	//private NoteLabel note=new NoteLabel();
	static String [] infu={"C","C","D","E","F","G","A","B"};//这个用于将数字表示的乐谱转换成JFugue的可识别的输入
	static String [] fenyinfu={"w","h","q","i","s"};//代表几分音符
	static int []  system_yinpu={60,62,64,65,67,69,71,72,74,76,77,79,81,83};//这是midi 两个八度没有升降号的输入
	private org.jfugue.Player pl=new org.jfugue.Player();// 这是JFugue的
	private Pattern pa=new Pattern();// 这是JFugue的 
	private int row;//定义音符的行数
	private int collum;//定义音符的列数
	NoteLabel []note;//音符矩阵
	JLabel [] rising_falling_tune;//升降号矩阵
	JPanel []group_panel;//音符和升降号 结合在一起
	JPanel notes=new JPanel();//音符所放的panel
	JScrollPane scroll_notes=new JScrollPane(notes);//待滚动条的panel
	JPanel choice=new JPanel();//输入选择 ，主要选择几分音符以及各种命令
	JRadioButton []choices=new JRadioButton[6];//6个单选按钮，选择几分音符
	ButtonGroup group=new ButtonGroup();//
	JButton save=new JButton("保存");
	JButton play=new JButton("播放");
	JButton translate =new JButton("转换成midi文件");
	int type;//保存几分音符
	private Synthesizer synthesizer = null;//midi合成器
	private MidiChannel channel;//midi通道
	private Instrument instruments[];//乐器
	private JComboBox instru= new JComboBox();//选择乐器的组合框
	private String instrument="I[0]";//指示乐器的参数。
	private static int velocity = 70;//衰减
	private playerPanel playerPane=new playerPanel();
	public Staff()            
	{
		/*
		 * 以下代码初始化几分音符，初始为全音符
		 * */
		type=0;
		choices[0]=new JRadioButton("全音符",true);
	    choices[1]=new JRadioButton("二分音符",false);
	    choices[2]=new JRadioButton("四分音符",false);
	    choices[3]=new JRadioButton("八分音符",false);
	    choices[4]=new JRadioButton("十六分音符",false);
	    choices[5]=new JRadioButton("空白",false);
	    choice.setLayout(new GridLayout(1,10));
	    
	    /*
	     * 以下内容初始化midi
	     * */
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		MidiChannel[] channels = synthesizer.getChannels();
		for (int i = 0; i < channels.length; i++) {
			if (channels[i] != null) {
				channel = channels[i];
				break;
			}
		}
		instruments = synthesizer.getAvailableInstruments();
		if (instruments.length == 0) {
			System.err.println("no instruments avaliable");
			System.exit(1);
		}
		channel.programChange(instruments[0].getPatch().getProgram());
		for (int i = 0; i < Math.min(128, instruments.length); i++) {
			instru.addItem(instruments[i]);
		}
		
		
		
//		以下内容是组合框的消息响应函数，更改乐器
		instru.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				instrument="I["+instru.getSelectedIndex()+"]";
			}			
		});
		
		
		
		//初始化选项面板
	    for(int i=0;i<6;i++)
	    {
	    	choice.add(choices[i]);
	    	group.add(choices[i]);
	    	
//	    	为单选按钮添加详细响应函数
	    	choices[i].addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {

					// TODO Auto-generated method stub
					for(int i=0;i<6;i++)
					{
						if(arg0.getSource()==choices[i])
						{
							type=(int) Math.pow(2, i);
							if(i==0) type=0;
						}
					}
				}	    		
	    	});
	    }
	    
	    choice.add(save);
	    choice.add(play);
	    choice.add(translate);
	    translate.setEnabled(false);
	    play.setEnabled(false);
	    save.setIcon(new ImageIcon("save.png"));
	    play.setIcon(new ImageIcon("Play.png"));
		row=10;
		collum=10;
		group_panel=new JPanel[row*collum];
		note=new NoteLabel[row*collum];
		rising_falling_tune=new JLabel[row*collum];
		setLayout(new BorderLayout());
		JPanel north=new JPanel();
		north.setLayout(new GridLayout(2,1));
		add(north,BorderLayout.NORTH);
		north.add(instru);
		north.add(playerPane);
		
		add(scroll_notes,BorderLayout.CENTER);
		add(choice,BorderLayout.SOUTH);
		notes.setLayout(new GridLayout(row,collum));
		
//		保存按钮的消息响应函数
		save.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				// TODO Auto-generated method stub
				play.setEnabled(true);
				translate.setEnabled(true);
				File file=new File("wuxianpu.txt");
				if(!file.exists())
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				try {
					FileWriter writer=new FileWriter(file);
					for(int i=0;i<row;i++)
					{
						for(int j=0;j<collum;j++)
						{
							int temp=0;
							if(rising_falling_tune[i*collum+j].getText().equals("#"))
								temp=1;
							else if(rising_falling_tune[i*collum+j].getText().equals("b"))
								temp=2;
							int value=note[i*collum+j].getValue()*10+temp;
							writer.write(""+value+"\n");
						}
					}
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		});
		translate.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String filename="";
				filename=JOptionPane.showInputDialog(translate ,"请输入要保存的文件名" );
				if(filename.equals(""))
				{
					JOptionPane.showInputDialog(null,"无效的文件名");
					return ;
				}
				filename+=".midi";
				File file=new File(filename);
				if(file.exists())
				{
					JOptionPane.showMessageDialog(null," 文件已存在");
					return ;
				}
				try {
					pl.saveMidi(pa, file);
					Runtime.getRuntime().exec("cmd /c start explorer "+file.getPath());

				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
//		播放的消息响应函数
		play.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				File file=new File("wuxianpu.txt");
				if(!file.exists())
				{
					JOptionPane.showMessageDialog(null,"没有发现文件");
					return ;
				}
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String temp;
					String total = "";
					while((temp=reader.readLine())!=null)
					{
						int value=Integer.parseInt(temp);
						int rising_fall=value%10;
						value=value/10;
						int t=value/100;
						int h=value/10%10;
						int s=value%10;
						String r_f="";
						if(rising_fall==1)
							r_f="#";
						else if(rising_fall==2)
							r_f="b";
						if(t==0&&h==0&s==0)
							break;
						int t1;
						
						 if(t==0)
							 t1=0;
						 else 
							 t1=(int) (Math.log(t)/Math.log(2));
						// System.out.print(""+t+" "+t1+" ");
						 temp=infu[s]+r_f+(h+5)+fenyinfu[t1]+" ";
						//System.out.print(infu[s]+r_f+(h+5)+fenyinfu[t1]+"\n");
						total+=temp; 
						/*pa.setMusicString(infu[s]+(h+1)
								 +fenyinfu[t1]);
						 
						 pl.play(pa);*/
						
					}
					System.out.print(instrument+" "+total);
					pa.setMusicString(instrument+ " "+total);
					pl.play(pa);
					reader.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
		});
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<collum;j++)
			{
				group_panel[i*collum+j]=new JPanel();
				note[i*collum+j]=new NoteLabel(0,0,0);
				rising_falling_tune[i*collum+j]=new JLabel(" ");
				rising_falling_tune[i*collum+j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
				group_panel[i*collum+j].setLayout(new GridLayout());
				group_panel[i*collum+j].add(note[i*collum+j] );
				group_panel[i*collum+j].add(rising_falling_tune[i*collum+j]);
				notes.add(group_panel[i*collum+j]);
				rising_falling_tune[i*collum+j].addMouseListener(new MouseListener(){

					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						if(((JLabel)arg0.getSource()).getText().equals("#"))
							((JLabel)arg0.getSource()).setText("b");
						else if(((JLabel)arg0.getSource()).getText().equals("b"))
							((JLabel)arg0.getSource()).setText(" ");
						else if(((JLabel)arg0.getSource()).getText().equals(" "))
							((JLabel)arg0.getSource()).setText("#");
						for(int i=0;i<row*collum;i++)
						 {
							if(note[i].getValue()==0) continue;
							 if(((JLabel)arg0.getSource())==rising_falling_tune[i])
							 {
								 int t=note[i].getType();
								 int h=note[i].getHigh_Low();
								 int s=note[i].getScale_degree();
								 int r_f=0;
								 if(rising_falling_tune[i].getText().equals("#"))
									 r_f=1;
								 else if(rising_falling_tune[i].getText().equals("b"))
									 r_f=-1;
								 channel.noteOn(system_yinpu[h*7+s-1]+r_f, velocity);
								// System.out.print(system_yinpu[h*7+s-1]+r_f);
							 }
						 }
							
					}

					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
			    note[i*collum+j].addMouseListener(new MouseListener(){

					public void mouseClicked(MouseEvent arg0) {
						if(type==32)
						{
							((NoteLabel)arg0.getSource()).set(0,0,0);
							return;
						}
						// TODO Auto-generated method stub
						 int y=arg0.getY();
						 int rectify=0;
						 if(y>60)
							 ((NoteLabel)arg0.getSource()).set(type,0,1);
						 else if(y>57+rectify)
								 ((NoteLabel)arg0.getSource()).set(type,0,2);
						 else if(y>54+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,0,3);
						 else if(y>50+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,0,4);
						 else if(y>46+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,0,5);
						 else if(y>40+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,0,6);
						 else if(y>35+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,0,7);
						 else if(y>32+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,1,1);
						 else if(y>28+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,1,2);
						 else if(y>25+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,1,3);
						 else if(y>22+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,1,4);
						 else if(y>15+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,1,5);
						 else if(y>7+rectify)
							 ((NoteLabel)arg0.getSource()).set(type,1,6);
						 else 
							 ((NoteLabel)arg0.getSource()).set(type,1,7);
						//System.out.print(" "+((JLabel) arg0.getSource()).getY()+" "+arg0.getY()+" "+((JLabel) arg0.getSource()).getWidth()+"\n");
						 int t=((NoteLabel)arg0.getSource()).getType();
						 int h=((NoteLabel)arg0.getSource()).getHigh_Low();
						 int s=((NoteLabel)arg0.getSource()).getScale_degree();
						 for(int i=0;i<row*collum;i++)
						 {
							 if(((NoteLabel)arg0.getSource())==note[i])
							 {
								 int r_f=0;
								 if(rising_falling_tune[i].getText().equals("#"))
									 r_f=1;
								 else if(rising_falling_tune[i].getText().equals("b"))
									 r_f=-1;
								 channel.noteOn(system_yinpu[h*7+s-1]+r_f, velocity);
							 }
						 }
						
						/* System.out.print(""+t+" "+h+" "+s);
						 int t1;
						 if(t==0)
							 t1=0;
						 else 
							 t1=(int) (Math.log10(t)/Math.log(10));
						 pa.setMusicString(infu[s]+(h+1)
								 +fenyinfu[t1]);
						 pl.play(pa);*/
						
					}

					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			    	 
			    });
			}
		}
		 
	}
}
/**
 * 音符类
 * @author MaYunlei
 *
 */
 class NoteLabel extends JLabel
{	 
	 public NoteLabel( int t,int h, int s)
	 {
		 set(t,h,s);
	 }
	 
	 public int getType()
	 {
		 return type;
	 }
	 public int getHigh_Low()
	 {
		 return high_low;
	 }
	 public int getScale_degree()
	 {
		 return scale_degree;
	 }
	 public int getValue()
	 {
		 return type*100+high_low*10+scale_degree;
	 }
	 public void  set(int t,int h, int s)
	 {
		 
		 type=t;
		 high_low=h;
		 scale_degree=s;
		 String temp=Staff.class.getResource("").toString();
		 temp=temp.substring(temp.indexOf("/")+1, temp.lastIndexOf("bin")-1);
		
		 image=new ImageIcon(temp+"/wuxianpu/"+t+h+s+".jpg");
		 //
		 setIcon(image );
		 //setText(""+type+high_low+scale_degree+" ");
	 }	 
	 ImageIcon image;
	 private int type;//全音符，二分音符，四分音符
	 private int high_low;   //表明是第几个八度
	 private int scale_degree; //音阶
}