import javazoom.jl.decoder.JavaLayerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;

@SuppressWarnings("serial")
class playerPanel extends JPanel
{
	private JPanel south=new JPanel();
	private JButton open=new JButton("���ļ�");
	private JButton play=new JButton("����");
	private JButton parse=new JButton("��ͣ");
	private JButton stop=new JButton("ֹͣ");
	private JLabel  infor=new JLabel("������ ");
	private String path=new String("");
	JFileChooser choose=new JFileChooser();
	javazoom.jl.player.Player player;
	playThread pT;
	int parse_position=0;
	boolean isparse=false;
	boolean start_flag=false;
	File file;
	Timer timer ;
	public playerPanel()
	{
		
	 
		infor.setFont(new Font("����", Font.BOLD, 30));
		infor.setForeground(Color.RED);
		setLayout(new BorderLayout());
		add(south,BorderLayout.SOUTH);
		add(infor,BorderLayout.NORTH);
		south.add(open);
		south.add(play);
		south.add(parse);
		south.add(stop);
		south.setLayout(new GridLayout(1,4));
		timer=new Timer(1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(start_flag)
				{
					if(!isparse)
						infor.setText("����<"+file.getName()+">"
								+(player.getPosition()/1000/60)+":"+(player.getPosition()/1000%60));
				}
			}
			
		});
		open.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				choose.setFileFilter(new FileFilter(){

					@Override
					public boolean accept(File file) {
						// TODO Auto-generated method stub
						if(file.isDirectory())
							return true;
						String ex=file.getName().substring(file.getName().lastIndexOf(".")+1
								, file.getName().length());
						if(ex.equals("mp3"))
							return true;
						return false;
					}

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "��Ƶ�ļ�MP3";
					}
					
				});
				choose.setCurrentDirectory(new File("."));
					 
				int result = choose.showOpenDialog(null);
				if(result==JFileChooser.APPROVE_OPTION)
				{
					file=choose.getSelectedFile();
					if(!file.exists())
					{
						JOptionPane.showMessageDialog(null,"�Ҳ���ָ���ļ�");
						return;
					}
				     path=file.getPath();
				     infor.setText("��"+file.getName());
				}
				
			}
			
		});
		parse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!start_flag)
					return;
				if(!isparse)
				{
					isparse=true;
					parse.setText("����");
					pT.suspend(); 
					infor.setText("��ͣ"+file.getName());
				}
				else 
				{
					isparse=false;
					parse.setText("��ͣ");
					pT.resume();
					infor.setText("����"+file.getName());
				}
			}
			
		});
		play.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(start_flag)
					return;
				if(path.equals(""))
				{
					JOptionPane.showMessageDialog(null,"��ѡ����ļ�");
					return;
				}
				infor.setText("����"+file.getName());
				pT=new playThread();
				pT.start();
				start_flag=true;
				timer.start();
			 
				 
		       
			}
			
		});
		stop.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!start_flag)
					return;
				pT.stop();
				start_flag=false;
				infor.setText("������");
				timer.stop();
			}
			
		});
		
		
		
	}
	class playThread extends Thread
	{
		public void run()
		{
			try {
				player=new javazoom.jl.player.Player(new FileInputStream(new File(path) ));
				player.play();
				start_flag=false;
				infor.setText(" ");
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}