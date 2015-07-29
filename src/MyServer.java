import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class MyServer {

	private Vector vect =new Vector();
	
	public static void main(String[] args)
	{
		MyServer server=new MyServer();
		try{
		
			server.startServer(2528);
		}catch(Exception e)
		 {
		  System.out.println("����������ʧ��");
		  e.printStackTrace();
		 }
	}
		public void startServer(int port)throws Exception
		{
			ServerSocket ss=new ServerSocket(port);
			System.out.println("���������������ȴ��ͻ�����");
			while(true)
			{
				Socket s=ss.accept();
				vect.add(s);
				System.out.println("�ͻ������ӳɹ�:"+s);
				new MySocketOpt(s).start();
			}
		}
	   class MySocketOpt extends Thread
	   {
		   private Socket s;
		   
		   public MySocketOpt(Socket s)
		   {
			   this.s=s;
		   }
		   
		   public void run()
		   {
			   try{
				   DataInputStream dis=new DataInputStream(
						   new BufferedInputStream(s.getInputStream()));
				   String iip="";	  
				   while(true){
							   String msg=dis.readUTF();
							 
							   if(msg.substring(0,1).equals("i"))
								 iip=msg.substring(1);   
								   
							 //  System.out.println("���������յ�:"+msg);
							   Socket temp=new Socket();
						     for(int i=0;i<vect.size();++i)
						     {
							   temp=(Socket)vect.get(i);
							       if(temp!=s){
								   DataOutputStream dos=new 
								   DataOutputStream(new BufferedOutputStream(temp.getOutputStream()));
								   if(!"88".equals(msg))
								   {
								   dos.writeUTF(msg);
							       dos.flush();
								   }
							       if("88".equals(msg))
							       {
							    	   dos.writeUTF(msg+iip);
								       dos.flush();
							       }
							       }
							       
							       
						     }
						     if("88".equals(msg))break;
						   }
				   
			   }catch(Exception e)
			   {
				   System.out.println(s+"���˳�������");
				   vect.remove(s);
			   }
			   System.out.println(s+"���˳�������");
			   vect.remove(s);
		   }
	   }
	
}
