package open;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 * 
 */

/**
 * @author RuanGuangPing
 *	Listen for the selection on the table.
 */

public class ListListener implements ListSelectionListener{
	
	public static int rowSelected;
	public static String fileName = null;
	
	//connection
	static Connection con;
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void valueChanged(ListSelectionEvent e){
		System.out.println("Selected");
		
		//get where has been selected.
		ListSelectionModel lsm =  (ListSelectionModel) e.getSource();
		rowSelected = lsm.getMinSelectionIndex();
		try {
			DatabaseInterface.getFileName();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}