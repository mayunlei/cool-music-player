import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class Help extends JFrame{	
	private String con;
	private JTextArea body = new JTextArea();
	private JScrollPane zone = new JScrollPane(body);
	private JButton confirm = new JButton("È·¶¨");
	
	
	Help(String title, String content){
		super(title);
		this.setLayout(null);
		this.con = content;
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(new Rectangle((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()-500)/2,
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-600)/2,500,600));
		this.add(zone);
		this.add(confirm);
		this.body.setEditable(false);
		this.body.setLineWrap(true);
		this.body.setWrapStyleWord(true);
		this.body.setText(con);
		zone.setBounds(new Rectangle(30,50,435,425));
		confirm.setBounds(new Rectangle(300,510,90,30));
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((JFrame) confirm.getParent().getParent().getParent().getParent()).dispose();
			}
		});
	}
}
