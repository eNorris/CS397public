package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import util.DBUtil;
import util.World;

/**
 * Notes:
 * 
 * Does the crawler support single files or just directories?
 * 
 * 
 * 
 * @author Edward
 *
 */


public class MainApplication extends JFrame{
	
	private static final long serialVersionUID = 4648172894076113183L;

	MainApplication(){
//		World.dbc = new DBUtil(DBUtil.DBPath, DBUtil.DBUsername, DBUtil.DBPassword);
//		World.dbc.Connect(DBUtil.DBPath, DBUtil.DBUsername, DBUtil.DBPassword);
	}
	
	public static void main(String[] args){
		System.out.print("Debug Console...\n");

		World.dbc = new DBUtil();
		if(!World.dbc.Connect()){
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame,
					"Could not connect to database: " + DBUtil.DBPath + DBUtil.DBName + "\nIs the application already running?",
					"Database Connection Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		System.out.print("Connected to database: " + DBUtil.DBPath + DBUtil.DBName + "\n\n");

		JFrame frame = new JFrame();
		
		Core core = new Core();
		frame.getContentPane().add(core);
		
		frame.setJMenuBar(new CoreMenu());
		
		frame.setTitle("Media Manager");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	public void finalize(){
		if(World.dbc != null)
			World.dbc.Disconnect();
	}
}
