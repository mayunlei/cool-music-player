/**
 * ����������������
 * ʵ�ּ���������洴��
 * ���ߺ�����
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.jfugue.*;

public class JianPu {
	static final int MUSIC_LENGTH = 240;//��Ҫ¼������ֳ��ȣ�������
	static final int BEAT_CONTAIN = 4;//ÿ����Ԫ��������ʮ��������������������
	
	static String musiccut = "";//�����ַ�����
	static String instrument = "I[0]";//������
	
	static ArrayList<JLabel> BEAT_LABEL = new ArrayList<JLabel>();//�������뵥Ԫ��
	static ArrayList<JTextField> BEAT_CONTAIN_TEXT = new ArrayList<JTextField>();//����������
	
	//�����༭��ʱ��Ҫ�ı���
	static int i = 0;
	static int j = 0;
	static int k = 0;
	static int checkflag = 1;
	
	static JPanel panel1 = new JPanel();//������Panel��
	static JPanel north = new JPanel();//����panel
	static JPanel txtopen = new JPanel();
	static JPanel midiopen = new JPanel();
	static File txtinput;
	static JButton openfile1 = new JButton("��.jtxt");
	static JLabel file1name = new JLabel();
	static JButton playfile1 = new JButton("����");
	static JButton savefile1 = new JButton("����");
	static JTextArea txtbody = new JTextArea();
	static String txtcontent = "";
	static JScrollPane txtzone = new JScrollPane(txtbody);
	static JButton openfile2 = new JButton("��.midi");
	static JLabel file2name = new JLabel();
	static JSlider file2slider = new JSlider(SwingConstants.HORIZONTAL,0,100,0);
	static JButton playfile2 = new JButton("����");
	static JButton exportfile2 = new JButton("����Ϊ�ַ���");
	static final JFileChooser jfc1 = new JFileChooser();
	static final JFileChooser jfc2 = new JFileChooser();
	static JPanel south = new JPanel();//����panel
	static JPanel southwest = new JPanel();
	static JPanel southcenter = new JPanel();
	static JPanel southeast = new JPanel();
	static JLabel timbre1lab = new JLabel("1����");
	static JLabel timbre2lab = new JLabel("2����");
	static JLabel timbre3lab = new JLabel("3����");
	static JLabel timbre4lab = new JLabel("4����");
	static JLabel ocupy1 = new JLabel("");
	static JLabel ocupy2 = new JLabel("");
	static JLabel ocupy3 = new JLabel("");
	static JLabel ocupy4 = new JLabel("");
	static JLabel ocupy5 = new JLabel("");
	static JTextArea timbre1body = new JTextArea();
	static JTextArea timbre2body = new JTextArea();
	static JTextArea timbre3body = new JTextArea();
	static JTextArea timbre4body = new JTextArea();
	static JScrollPane timbre1zone = new JScrollPane(timbre1body);
	static JScrollPane timbre2zone = new JScrollPane(timbre2body);
	static JScrollPane timbre3zone = new JScrollPane(timbre3body);
	static JScrollPane timbre4zone = new JScrollPane(timbre4body);
	static JButton timbrechange = new JButton("����");
	static JButton timbreplay = new JButton("����");
	static JButton timbresaves = new JButton("����Ϊ*V*.jtxt");
	static JButton timbresave = new JButton("����Ϊ.midi");
	static JPanel east = new JPanel();//����panel
	static JPanel west = new JPanel();//����panel
	static JPanel wests = new JPanel();
	static JTextArea connectbody = new JTextArea();
	static JScrollPane connectzone = new JScrollPane(connectbody);
	static JButton connectplay = new JButton("����");
	static JButton connectclear = new JButton("���");
	static JButton connectsaveasstring = new JButton("����Ϊ.jtxt");
	static JButton connectsaveasmidi = new JButton("����Ϊ.midi");
	static JScrollPane jianpuinzone = new JScrollPane();//�м�panel���༭��
	static JPanel jianpuin = new JPanel();//�༭����panel
	static JPanel controlpane = new JPanel();//������panel
	static JButton play = new JButton("����");//���Ű�ť 
	static PopupMenu playmenu = new PopupMenu();//���������˵�
	static PopupMenu txtplaymenu = new PopupMenu();//���������˵�
	static PopupMenu conplaymenu = new PopupMenu();//���������˵�
	static JButton saveasstring = new JButton("����Ϊ.jtxt");//���水ť
	static JButton saveasmidi = new JButton("����Ϊ.midi");//���水ť
	static JButton cleareditzone = new JButton("���¼����");
	static JLabel InstrumentSelect = new JLabel("ѡ������");//����ѡ��label
	static JComboBox Instruments = new JComboBox();//����ѡ��������
	static MenuItem timbre1 = new MenuItem("����Ϊ1����");
	static MenuItem timbre2 = new MenuItem("����Ϊ2����");
	static MenuItem timbre3 = new MenuItem("����Ϊ3����");
	static MenuItem timbre4 = new MenuItem("����Ϊ4����");
	static MenuItem connect0 = new MenuItem("��ӵ��ϳ�����");
	static MenuItem txttimbre1 = new MenuItem("����1����");
	static MenuItem txttimbre2 = new MenuItem("����2����");
	static MenuItem txttimbre3 = new MenuItem("����3����");
	static MenuItem txttimbre4 = new MenuItem("����4����");
	static MenuItem txtconnect0 = new MenuItem("����ϳ�����");
	static MenuItem contimbre1 = new MenuItem("����1����");
	static MenuItem contimbre2 = new MenuItem("����2����");
	static MenuItem contimbre3 = new MenuItem("����3����");
	static MenuItem contimbre4 = new MenuItem("����4����");
	static String timbre1s = "";
	static String timbre2s = "";
	static String timbre3s = "";
	static String timbre4s = "";
	static String timbrewhole = "";
	static String connects = "";
	static boolean changingflag = false;
	
	static Player midipl = new Player();
	static Pattern midipa = new Pattern();
	static boolean threadstopflag = false;
	static boolean isplaying = false;
	static boolean pausechangeflag = false;
	static long len;
	static boolean positionchangeflag = false;
	static int position = 0;
	
	
	//Ϊ�˱�������������еĵ��ã��ú�������һ��Panel����
	public static JPanel jp(){
		panel1.setLayout(new BorderLayout());
		panel1.add(jianpuinzone, BorderLayout.CENTER);
		panel1.add(east, BorderLayout.EAST);
		panel1.add(north, BorderLayout.NORTH);
		panel1.add(south, BorderLayout.SOUTH);
		panel1.add(west, BorderLayout.WEST);
		
		jianpuinzone.setBorder(BorderFactory.createTitledBorder("¼����"));
		jianpuin.setLayout(null);
		jianpuin.setPreferredSize(new Dimension(8*110, (MUSIC_LENGTH/8)*40+20));
		jianpuin.validate();
		
		west.setBorder(BorderFactory.createTitledBorder("�ϳ�����"));
		west.add(wests);
		wests.setPreferredSize(new Dimension(200,320));
		connectbody.setEditable(false);
		connectbody.setLineWrap(true);
		connectbody.setWrapStyleWord(true);
		conplaymenu.setEnabled(false);
		connectsaveasstring.setEnabled(false);
		connectsaveasmidi.setEnabled(false);
		connectplay.setEnabled(false);
		wests.add(connectzone);
		wests.add(connectplay);
		wests.add(connectclear);
		wests.add(connectsaveasstring);
		wests.add(connectsaveasmidi);
		connectclear.setPreferredSize(new Dimension(120,20));
		connectclear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				connects = "";
				connectbody.setText(connects);
				connectsaveasstring.setEnabled(false);
				connectsaveasmidi.setEnabled(false);
				connectplay.setEnabled(false);
			}
		});
		connectplay.add(conplaymenu);
		conplaymenu.add(contimbre1);
		contimbre1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre1s ="V0 " + connects;
				timbre1body.setText(timbre1s);
				timbreplay.setEnabled(true);
			}
		});
		conplaymenu.add(contimbre2);
		contimbre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre2s ="V1 " + connects;
				timbre2body.setText(timbre2s);
				timbreplay.setEnabled(true);
			}
		});
		conplaymenu.add(contimbre3);
		contimbre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre3s ="V2 " + connects;
				timbre3body.setText(timbre3s);
				timbreplay.setEnabled(true);
			}
		});
		conplaymenu.add(contimbre4);
		contimbre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre4s ="V3 " + connects;
				timbre4body.setText(timbre4s);
				timbreplay.setEnabled(true);
			}
		});
		connectplay.setPreferredSize(new Dimension(120,20));
		connectplay.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 1){
					new Player().play(connects);
					conplaymenu.setEnabled(true);
					connectsaveasstring.setEnabled(true);
					connectsaveasmidi.setEnabled(true);
				}
				if(e.getButton() == 3){
					conplaymenu.show(connectplay, e.getX(), e.getY());
				}
			}
		});
		connectsaveasstring.setPreferredSize(new Dimension(120,20));
		connectsaveasstring.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				StringSave(connects);
			}
		});
		connectsaveasmidi.setPreferredSize(new Dimension(120,20));
		connectsaveasmidi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MidiSave(connects);
			}
		});
		connectzone.setPreferredSize(new Dimension(200,200));
		
		south.setBorder(BorderFactory.createTitledBorder("������"));
		south.setLayout(new BorderLayout());
		south.add(southwest, BorderLayout.WEST);
		south.add(southcenter, BorderLayout.CENTER);
		south.add(southeast, BorderLayout.EAST);
		southwest.setLayout(new GridLayout(4,2,5,5));
		southcenter.setLayout(new GridLayout(4,2,5,5));
		southwest.add(timbre1lab);
		southwest.add(ocupy1);
		southwest.add(timbre2lab);
		southwest.add(ocupy2);
		southwest.add(timbre3lab);
		southwest.add(ocupy3);
		southwest.add(timbre4lab);
		southwest.add(ocupy4);
		southcenter.add(timbre1zone);
		southcenter.add(timbre2zone);
		southcenter.add(timbre3zone);
		southcenter.add(timbre4zone);
		timbre1body.setEditable(false);
		timbre2body.setEditable(false);
		timbre3body.setEditable(false);
		timbre4body.setEditable(false);
		timbreplay.setEnabled(false);
		timbresave.setEnabled(false);
		timbresaves.setEnabled(false);
		southeast.setPreferredSize(new Dimension(140,100));
		southeast.add(timbrechange);
		southeast.add(timbreplay);
		southeast.add(timbresaves);
		southeast.add(timbresave);
		timbrechange.setPreferredSize(new Dimension(120,20));
		timbrechange.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(changingflag){
					timbre1body.setEditable(false);
					timbre2body.setEditable(false);
					timbre3body.setEditable(false);
					timbre4body.setEditable(false);
					timbreplay.setEnabled(true);
					changingflag = false;
					timbre1s = timbre1body.getText();
					timbre2s = timbre2body.getText();
					timbre3s = timbre3body.getText();
					timbre4s = timbre4body.getText();
					timbrechange.setText("����");
				}
				else{
					timbre1body.setEditable(true);
					timbre2body.setEditable(true);
					timbre3body.setEditable(true);
					timbre4body.setEditable(true);
					timbreplay.setEnabled(false);
					timbresave.setEnabled(false);
					timbresaves.setEnabled(false);
					changingflag = true;
					timbrechange.setText("�ύ");
				}
			}
		});
		timbreplay.setPreferredSize(new Dimension(120,20));
		timbreplay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbrewhole = timbre1s + " " + timbre2s + " " + timbre3s + " " + timbre4s + " ";
				new Player().play(timbrewhole);
				timbresave.setEnabled(true);
				timbresaves.setEnabled(true);
			}
		});
		timbresave.setPreferredSize(new Dimension(120,20));
		timbresave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MidiSave(timbrewhole);
			}
		});
		timbresaves.setPreferredSize(new Dimension(120,20));
		timbresaves.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TimbreStringSave(timbre1s, timbre2s, timbre3s, timbre4s);
			}
		});
		timbre1body.setLineWrap(true);
		timbre1body.setWrapStyleWord(true);
		timbre2body.setLineWrap(true);
		timbre2body.setWrapStyleWord(true);
		timbre3body.setLineWrap(true);
		timbre3body.setWrapStyleWord(true);
		timbre4body.setLineWrap(true);
		timbre4body.setWrapStyleWord(true);
		timbre1zone.setPreferredSize(new Dimension(100,30));
		timbre2zone.setPreferredSize(new Dimension(100,30));
		timbre3zone.setPreferredSize(new Dimension(100,30));
		timbre4zone.setPreferredSize(new Dimension(100,30));
		
		
		north.add(txtopen);
		north.add(midiopen);
		north.setBorder(BorderFactory.createTitledBorder("�ļ���ȡ"));
		north.setLayout(new GridLayout(2,1));
		txtplaymenu.add(txttimbre1);
		txttimbre1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre1s ="V0 " + txtcontent;
				timbre1body.setText(timbre1s);
				timbreplay.setEnabled(true);
			}
		});
		txtplaymenu.add(txttimbre2);
		txttimbre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre2s ="V1 " + txtcontent;
				timbre2body.setText(timbre2s);
				timbreplay.setEnabled(true);
			}
		});
		txtplaymenu.add(txttimbre3);
		txttimbre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre3s ="V2 " + txtcontent;
				timbre3body.setText(timbre3s);
				timbreplay.setEnabled(true);
			}
		});
		txtplaymenu.add(txttimbre4);
		txttimbre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre4s ="V3 " + txtcontent;
				timbre4body.setText(timbre4s);
				timbreplay.setEnabled(true);
			}
		});
		txtplaymenu.add(txtconnect0);
		txtconnect0.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				connects += txtcontent + " ";
				connectbody.setText(connects);
				connectplay.setEnabled(true);
				conplaymenu.setEnabled(false);
				connectsaveasstring.setEnabled(false);
				connectsaveasmidi.setEnabled(false);
			}
		});
		txtplaymenu.setEnabled(false);
		txtopen.add(openfile1);
		txtopen.add(file1name);
		txtopen.add(txtzone);
		txtopen.add(playfile1);
		txtopen.add(savefile1);
		txtbody.setEditable(false);
		file1name.setPreferredSize(new Dimension(80,20));
		savefile1.setEnabled(false);
		savefile1.setPreferredSize(new Dimension(120,20));
		savefile1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				txtcontent = txtbody.getText();
				txtbody.setText(txtcontent);
				txtinput.delete();
				try {
					txtinput.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(txtinput));
					pw.println(txtcontent);
					pw.close();
				} catch (IOException et1) {
				}				
			}
		});
		playfile1.setEnabled(false);
		playfile1.setPreferredSize(new Dimension(120,20));
		playfile1.add(txtplaymenu);
		playfile1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == 1){
					new Player().play(txtcontent);
				}
				if(e.getButton() == 3){
					txtplaymenu.show(playfile1, e.getX(), e.getY());
				}
			}
		});
		txtbody.setLineWrap(true);
		txtbody.setWrapStyleWord(true);
		txtzone.setPreferredSize(new Dimension(500,50));
		txtopen.setBorder(BorderFactory.createTitledBorder("TXT"));
		openfile1.setPreferredSize(new Dimension(120,20));
		openfile1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				txtcontent = "";
				jfc1.showOpenDialog(panel1);
				txtinput = jfc1.getSelectedFile();
				if(txtinput != null){
					try {
						Scanner in = new Scanner(txtinput);
						while(in.hasNextLine()){
							txtcontent += in.nextLine();
						}
						in.close();
						file1name.setText(txtinput.getName());
						txtbody.setText(txtcontent);
						playfile1.setEnabled(true);
						savefile1.setEnabled(true);
						txtbody.setEditable(true);
						txtplaymenu.setEnabled(true);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		midiopen.add(openfile2);
		midiopen.add(file2name);
		midiopen.add(file2slider);
		midiopen.add(playfile2);
		midiopen.add(exportfile2);
		exportfile2.setEnabled(false);
		exportfile2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				connects = midipa.getMusicString()+" ";
				connectbody.setText(connects);
				connectplay.setEnabled(true);
			}
		});
		file2slider.setEnabled(false);
		file2slider.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {
				positionchangeflag = true;
			}
			public void mouseMoved(MouseEvent arg0) {
			}
		});
		playfile2.setEnabled(false);
		file2name.setPreferredSize(new Dimension(80,20));
		file2slider.setPreferredSize(new Dimension(500,50));
		playfile2.setPreferredSize(new Dimension(120,20));
		exportfile2.setPreferredSize(new Dimension(120,20));
		playfile2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(pausechangeflag){
					isplaying = true;
				}
				else{
					isplaying = false;
				}
			}
		});
		midiopen.setBorder(BorderFactory.createTitledBorder("MIDI"));
		openfile2.setPreferredSize(new Dimension(120,20));
		openfile2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				threadstopflag = true;
				jfc2.showOpenDialog(panel1);
				try {
					midipa = midipl.loadMidi(jfc2.getSelectedFile());
				} catch (IOException e1) {
					threadstopflag = true;
					playfile2.setEnabled(false);
					file2slider.setEnabled(false);
					exportfile2.setEnabled(false);
					file2name.setText("��д����");
					return;
				} catch (InvalidMidiDataException e1) {
					threadstopflag = true;
					playfile2.setEnabled(false);
					file2slider.setEnabled(false);
					exportfile2.setEnabled(false);
					file2name.setText("��ʽ����");
					return;
				} catch (Exception e1){
					threadstopflag = true;
					playfile2.setEnabled(false);
					file2slider.setEnabled(false);
					exportfile2.setEnabled(false);
					file2name.setText("δѡ���ļ�");
					return;
				}
				file2name.setText(jfc2.getSelectedFile().getName());
				len = midipl.getSequenceLength(midipl.getSequence(midipa))/1000;
				file2slider.setMaximum((int) len);
				threadstopflag = false;
				new Control().start();
				new TPlay().start();
				new Cfs().start();
				new Cfc().start();
				playfile2.setEnabled(true);
				file2slider.setEnabled(true);
				exportfile2.setEnabled(true);
			}
		});
		
		
		east.setLayout(new BorderLayout());
		east.setBorder(BorderFactory.createTitledBorder("������"));
		east.add(controlpane, BorderLayout.NORTH);
		controlpane.setLayout(new FlowLayout());
		controlpane.add(InstrumentSelect);
		InstrumentSelect.setPreferredSize(new Dimension(190,20));
		controlpane.add(Instruments);
		controlpane.add(ocupy5);
		ocupy5.setPreferredSize(new Dimension(120,20));
		controlpane.add(play);
		playmenu.add(timbre1);
		timbre1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre1s ="V0 " + musiccut;
				timbre1body.setText(timbre1s);
				timbreplay.setEnabled(true);
			}
		});
		playmenu.add(timbre2);
		timbre2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre2s ="V1 " + musiccut;
				timbre2body.setText(timbre2s);
				timbreplay.setEnabled(true);
			}
		});
		playmenu.add(timbre3);
		timbre3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre3s ="V2 " + musiccut;
				timbre3body.setText(timbre3s);
				timbreplay.setEnabled(true);
			}
		});
		playmenu.add(timbre4);
		timbre4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				timbre4s ="V3 " + musiccut;
				timbre4body.setText(timbre4s);
				timbreplay.setEnabled(true);
			}
		});
		playmenu.add(connect0);
		connect0.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				connects += musiccut + " ";
				connectbody.setText(connects);
				connectplay.setEnabled(true);
				conplaymenu.setEnabled(false);
				connectsaveasstring.setEnabled(false);
				connectsaveasmidi.setEnabled(false);
			}
		});
		playmenu.setEnabled(false);
		controlpane.add(saveasstring);
		controlpane.add(saveasmidi);
		controlpane.add(cleareditzone);
		controlpane.setPreferredSize(new Dimension(200,240));
		Instruments.setEditable(false);
		cleareditzone.setPreferredSize(new Dimension(120,20));
		cleareditzone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int index = 1;
				while(!BEAT_CONTAIN_TEXT.get(index).getText().equals("")){
					BEAT_CONTAIN_TEXT.get(index).setText("");
					BEAT_CONTAIN_TEXT.get(index-1).setText("");
					BEAT_CONTAIN_TEXT.get(index).setBackground(Color.WHITE);
					index += 2;
				}
				BEAT_CONTAIN_TEXT.get(index).setBackground(Color.WHITE);
				musiccut = "";
				saveasstring.setEnabled(false);
				saveasmidi.setEnabled(false);
				playmenu.setEnabled(false);
			}
		});
		//����ѡ�����������䣺������п��õ�����
		Instruments.setPreferredSize(new Dimension(190,20));
		for(int i = 0; i < 128; i++){
			Instruments.addItem(""+i+": "+org.jfugue.Instrument.INSTRUMENT_NAME[i].toUpperCase());
		}
		
		//����ѡ���������¼������¼����������ѡ��Ĺ���
		Instruments.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				instrument = "I["+Instruments.getSelectedIndex()+"]";
			}
		});
		
		play.add(playmenu);
		play.setPreferredSize(new Dimension(120,20));
		//���Ű�ť���¼�
		//���¼������������������ɣ��Լ������ַ��������
		play.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == 1){
					musiccut = instrument;
					checkflag = 1;
					while(BEAT_CONTAIN_TEXT.get(checkflag+2).getText().length() != 0){
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("-")){
							return;
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("0")){
							musiccut += " R";
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("1")){
							musiccut += " C";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("2")){
							musiccut += " D";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("3")){
							musiccut += " E";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("4")){
							musiccut += " F";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("5")){
							musiccut += " G";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("6")){
							musiccut += " A";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
						if(BEAT_CONTAIN_TEXT.get(checkflag).getText().equals("7")){
							musiccut += " B";
							musiccut += BEAT_CONTAIN_TEXT.get(checkflag-1).getText();
							ZoneCheck();
							LongCheck();
						}
					}
					new Player().play(musiccut);
					saveasstring.setEnabled(true);
					saveasmidi.setEnabled(true);
					playmenu.setEnabled(true);
				}
				if(e.getButton() == 3){
					playmenu.show(play, e.getX(), e.getY());
				}
			}
		});
		//���������ַ���
		saveasstring.setPreferredSize(new Dimension(120,20));
		saveasstring.setEnabled(false);
		saveasstring.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				StringSave(musiccut);
			}
		});
		//������Ƶ�ļ�
		saveasmidi.setPreferredSize(new Dimension(120,20));
		saveasmidi.setEnabled(false);
		saveasmidi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MidiSave(musiccut);
			}
		});
		
		
		//���ɱ༭��
		for(i = 0; i < (MUSIC_LENGTH/8); i++){
			for(j = 0; j < 8; j++){
				BEAT_LABEL.add(new JLabel());
				BEAT_LABEL.get(i*8 + j).setBorder(BorderFactory.createLineBorder(Color.GRAY));
				BEAT_LABEL.get(i*8 + j).setBounds(new Rectangle(10+j*110,10+i*40,110,40));
				BEAT_LABEL.get(i*8 + j).setText(""+ (i*8 + j + 1));
				BEAT_LABEL.get(i*8 + j).setForeground(Color.BLUE);
				jianpuin.add(BEAT_LABEL.get(i*8 + j));
				for(k = 0; k < BEAT_CONTAIN*2; k++){
					BEAT_CONTAIN_TEXT.add(new JTextField());
					BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).setBounds(new Rectangle(10*k+30,10,10,20));
					//�����������¼��
					if(k%2 == 1){
						//�������򽹵��¼����۽�ʱ�����ɫ��ƣ���������Լ������ַ�����
						BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).addFocusListener(new FocusListener(){
							public void focusGained(FocusEvent e) {
								JTextField tmp = (JTextField) e.getSource();
								int index = BEAT_CONTAIN_TEXT.indexOf(tmp);
								tmp.setBackground(Color.YELLOW);
								tmp.setText("");
								BEAT_CONTAIN_TEXT.get(index - 1).setText("");
							}
							public void focusLost(FocusEvent e) {
							}
						});
						//������������¼���������ڹ������λ��֮ǰ�Ƿ��п���������������ǰ��ת��ѭ���˹���
						BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).addCaretListener(new CaretListener(){
							public void caretUpdate(CaretEvent e) {
								JTextField tmp = (JTextField) e.getSource();
								int index = BEAT_CONTAIN_TEXT.indexOf(tmp);
								while(index - 2 >= 0){
									if(BEAT_CONTAIN_TEXT.get(index - 2).getText().length() == 0){
										BEAT_CONTAIN_TEXT.get(index).setBackground(Color.WHITE);
										index -= 2;
									}
									else break;
								}
								BEAT_CONTAIN_TEXT.get(index).requestFocus(true);
							}
						});
						//���������̼����¼�������¼����Ҫ����
						BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).addKeyListener(new KeyAdapter(){
							public void keyPressed(KeyEvent e){
								JTextField tmp = (JTextField) e.getSource();
								int index = BEAT_CONTAIN_TEXT.indexOf(tmp);
								if((e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
										&& index - 2 >= 0){
									BEAT_CONTAIN_TEXT.get(index).setBackground(Color.WHITE);
									BEAT_CONTAIN_TEXT.get(index - 2).requestFocus(true);
								}
								if(e.getKeyCode() == KeyEvent.VK_8){
									BEAT_CONTAIN_TEXT.get(index - 1).setText("b");
								}
								if(e.getKeyCode() == KeyEvent.VK_9){
									BEAT_CONTAIN_TEXT.get(index - 1).setText("#");
								}
							}
							public void keyReleased(KeyEvent e){
								JTextField tmp = (JTextField) e.getSource();
								int index = BEAT_CONTAIN_TEXT.indexOf(tmp);
								if(e.getKeyCode() == KeyEvent.VK_8){
									BEAT_CONTAIN_TEXT.get(index).requestFocus(true);
									BEAT_CONTAIN_TEXT.get(index).setText("");
								}
								else if(e.getKeyCode() == KeyEvent.VK_9){
									BEAT_CONTAIN_TEXT.get(index).requestFocus(true);
									BEAT_CONTAIN_TEXT.get(index).setText("");
								}
								else if(e.getKeyCode() == KeyEvent.VK_SPACE){
									BEAT_CONTAIN_TEXT.get(index).setText("-");
									if(index + 2 <= MUSIC_LENGTH*2*BEAT_CONTAIN){
										BEAT_CONTAIN_TEXT.get(index + 2).requestFocus(true);
									}
								}
								else if(e.getKeyCode() >= 48 && e.getKeyCode() <= 55){
									BEAT_CONTAIN_TEXT.get(index).setBackground(Color.GREEN);
									if(index + 2 <= MUSIC_LENGTH*2*BEAT_CONTAIN){
										BEAT_CONTAIN_TEXT.get(index + 2).requestFocus(true);
									}
								}
								else if(e.getKeyCode() >= 112 && e.getKeyCode() <= 118){
									BEAT_CONTAIN_TEXT.get(index).setText("" + (e.getKeyCode()-111));
									BEAT_CONTAIN_TEXT.get(index).setBackground(Color.CYAN);
									if(index + 2 <= MUSIC_LENGTH*2*BEAT_CONTAIN){
										BEAT_CONTAIN_TEXT.get(index + 2).requestFocus(true);
									}
								}
								else if(e.getKeyCode() >= 65 && e.getKeyCode() <= 66){
									BEAT_CONTAIN_TEXT.get(index).setText("" + (e.getKeyCode()-59));
									BEAT_CONTAIN_TEXT.get(index).setBackground(Color.MAGENTA);
									if(index + 2 <= MUSIC_LENGTH*2*BEAT_CONTAIN){
										BEAT_CONTAIN_TEXT.get(index + 2).requestFocus(true);
									}
								}
								else if(e.getKeyCode() >= 67 && e.getKeyCode() <= 71){
									BEAT_CONTAIN_TEXT.get(index).setText("" + (e.getKeyCode()-66));
									BEAT_CONTAIN_TEXT.get(index).setBackground(Color.MAGENTA);
									if(index + 2 <= MUSIC_LENGTH*2*BEAT_CONTAIN){
										BEAT_CONTAIN_TEXT.get(index + 2).requestFocus(true);
									}
								}
								else{
									BEAT_CONTAIN_TEXT.get(index).requestFocus(true);
									BEAT_CONTAIN_TEXT.get(index).setText("");									
								}
							}
						});
					}
					//ż�������ɱ༭��������ʾ�������ţ���ɫΪǳ��ɫ
					else{
						BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).setBackground(Color.LIGHT_GRAY);
						BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k).setEditable(false);
					}
					//��ÿ������¼���������������뵥Ԫ
					BEAT_LABEL.get(i*8 + j).add(BEAT_CONTAIN_TEXT.get(BEAT_CONTAIN*2*(i*8 + j) + k));
				}
			}
		}
		//�ػ�¼����
		jianpuin.repaint();
		jianpuinzone.setViewportView(jianpuin);
		
		//���ظý���
		return panel1;
	}
	
	private static class Control extends Thread{
		public void run(){
			while(!threadstopflag){
				if(midipl.isPlaying()){
					playfile2.setText("��ͣ");
					exportfile2.setEnabled(false);
					pausechangeflag = false;
					if(!isplaying){
						midipl.pause();
					}
				}
				else{
					playfile2.setText("����");
					exportfile2.setEnabled(true);
					pausechangeflag = true;
					if(isplaying && midipl.isPaused()){
						midipl.resume();
					}
				}
				try {
					sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class TPlay extends Thread{
		public void run(){
			while(!threadstopflag){
				if(pausechangeflag && isplaying){
					midipl.play(midipa);
					isplaying = false;
				}
				try {
					sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static class Cfs extends Thread{
		public void run(){
			while(!threadstopflag){
				while(midipl.isPlaying()){
					file2slider.setValue(position);
					position += 5;
					try {
						sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!midipl.isStarted()){
					position = 0;
				}
				try {
					sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class Cfc extends Thread{
		public void run(){
			while(!threadstopflag){
				if(positionchangeflag){
					midipl.jumpTo((file2slider.getValue()*1000));
					position = file2slider.getValue();
					positionchangeflag = false;
				}
				try {
					sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//�ú�����Ҫ���ڼ���û���һ�������ӳ��˶��٣�����¼��������
	private static void LongCheck(){
		if(checkflag+4 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+2).getText().equals("-")){
			if(checkflag+6 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+4).getText().equals("-")){
				if(checkflag+8 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+6).getText().equals("-")){
					if(checkflag+10 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+8).getText().equals("-")){
						if(checkflag+12 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+10).getText().equals("-")){
							if(checkflag+14 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+12).getText().equals("-")){
								if(checkflag+16 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+14).getText().equals("-")){
									if(checkflag+18 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+16).getText().equals("-")){
										if(checkflag+20 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+18).getText().equals("-")){
											if(checkflag+22 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+20).getText().equals("-")){
												if(checkflag+24 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+22).getText().equals("-")){
													if(checkflag+26 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+24).getText().equals("-")){
														if(checkflag+28 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+26).getText().equals("-")){
															if(checkflag+30 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+28).getText().equals("-")){
																if(checkflag+32 <= MUSIC_LENGTH*2*BEAT_CONTAIN && BEAT_CONTAIN_TEXT.get(checkflag+30).getText().equals("-")){
																	musiccut += "/1";
																	checkflag += 32;
																}
																else{
																	musiccut += "/0.9375";
																	checkflag += 30;
																}
															}
															else{
																musiccut += "/0.875";
																checkflag += 28;
															}
														}
														else{
															musiccut += "/0.8125";
															checkflag += 26;
														}
													}
													else{
														musiccut += "/0.75";
														checkflag += 24;
													}
												}
												else{
													musiccut += "/0.6875";
													checkflag += 22;
												}
											}
											else{
												musiccut += "/0.625";
												checkflag += 20;
											}
										}
										else{
											musiccut += "/0.5625";
											checkflag += 18;
										}
									}
									else{
										musiccut += "/0.5";
										checkflag += 16;
									}
								}
								else{
									musiccut += "/0.4375";
									checkflag += 14;
								}
							}
							else{
								musiccut += "/0.375";
								checkflag += 12;
							}
						}
						else{
							musiccut += "/0.3125";
							checkflag += 10;
						}
					}
					else{
						musiccut += "/0.25";
						checkflag += 8;
					}
				}
				else{
					musiccut += "/0.1875";
					checkflag += 6;
				}
			}
			else{
				musiccut += "/0.125";
				checkflag += 4;
			}
		}
		else{
			musiccut += "/0.0625";
			checkflag += 2;
		}
	}
	
	//�ú�����Ҫ���ڼ���û�¼�����������������
	private static void ZoneCheck(){
		if(BEAT_CONTAIN_TEXT.get(checkflag).getBackground().equals(Color.CYAN)){
			musiccut += "6";
		}
		else if(BEAT_CONTAIN_TEXT.get(checkflag).getBackground().equals(Color.GREEN)){
			musiccut += "5";
		}
		else{
			musiccut += "4";
		}
	}
	
	
	private static void StringSave(String s){
		String filename = (String) JOptionPane.showInputDialog(null, null, "�ļ���", 3, null, null, "cut1");
		if(filename == null) return;
		else{
			filename += ".jtxt";
			File tmp = new File(filename);
			if(tmp.exists()){
				tmp.delete();
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(s);
					pw.close();
				} catch (IOException e) {
				}
			}
			else{
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(s);
					pw.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private static void TimbreStringSave(String v0, String v1, String v2, String v3){
		String name = (String) JOptionPane.showInputDialog(null, null, "�ļ���", 3, null, null, "cut1");
		String filename = "";
		if(name == null) return;
		else{
			filename = name + "V1.jtxt";
			File tmp = new File(filename);
			if(tmp.exists()){
				tmp.delete();
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v0);
					pw.close();
				} catch (IOException e) {
				}
			}
			else{
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v0);
					pw.close();
				} catch (IOException e) {
				}
			}
			filename = name + "V2.jtxt";
			tmp = new File(filename);
			if(tmp.exists()){
				tmp.delete();
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v1);
					pw.close();
				} catch (IOException e) {
				}
			}
			else{
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v1);
					pw.close();
				} catch (IOException e) {
				}
			}
			filename = name + "V3.jtxt";
			tmp = new File(filename);
			if(tmp.exists()){
				tmp.delete();
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v2);
					pw.close();
				} catch (IOException e) {
				}
			}
			else{
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v2);
					pw.close();
				} catch (IOException e) {
				}
			}
			filename = name + "V4.jtxt";
			tmp = new File(filename);
			if(tmp.exists()){
				tmp.delete();
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v3);
					pw.close();
				} catch (IOException e) {
				}
			}
			else{
				try {
					tmp.createNewFile();
					PrintWriter pw = new PrintWriter(new FileWriter(tmp));
					pw.println(v3);
					pw.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private static void MidiSave(String s){
		String filename = (String) JOptionPane.showInputDialog(null, null, "�ļ���", 3, null, null, "cut1");
		if(filename == null) return;
		else{
			filename += ".midi";
			File tmp = new File(filename);
			if(tmp.exists()){
				tmp.delete();
				try {
					new Player().saveMidi(new Pattern(s), tmp);
				} catch (IOException e) {
				}
			}
			else{
				try {
					new Player().saveMidi(new Pattern(s), tmp);
				} catch (IOException e) {
				}
			}
		}
	}
}