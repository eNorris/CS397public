package gui;

import util.DBUtil;
import util.World;

import javax.swing.*;

/**
 * TODO List:
 * Finish the scheduler
 * Create notification when the DB is updated and update currentLib in a non-ugly way
 * Make a real name for our application
 * Remove unneeded flies in the searchEngine package since it isn't used anymore
 * 
 * 
 * === NOTES: ===
 * 
 * I commented out a bunch of code that will still be in development for a while
 * 
 * The user can select single files or directories again for the crawler
 * 
 * Whenever you call Util.relPath(str), make sure str does NOT start with a / ("data/file.txt" instead of "/data/file.txt")
 * 
 * Added a global value called World.SQLDEBUG to turn SQL stack trace output on and off
 * 
 * Most of the meaningful database queries are either in MediaLibrary::constructFromDB() which makes the first
 *   query and calls the corresponding MediaFile creator. Each MediaFile type has a corresponding createFromDB()
 *   function
 * 
 * Make sure the perl script finds "scripts/sqllock" and deletes it when it's done
 * 
 * Also, as a side note, Wall is now a pseudo-singleton object. This means that anytime, anywhere you can call
 *   Wall.singleton to get the last Wall object created. Someday, maybe I'll make it a fully singleton object
 *   so only one Wall can ever exist at any time (which would make sense).
 */


public class MainApplication extends JFrame{
	
	private static final long serialVersionUID = 4648172894076113183L;
    private static String username = "TestUser";

    public static void sout(String out) {
        System.out.println(out);
    }
    public static String getUsername() {
        return username;
    }
	MainApplication(){}
	
	public static void main(String[] args){
        sout("Debug Console...");
        sout("Running in directory [" + System.getProperty("user.dir") + "]");

		World.dbc = new DBUtil();
		if(World.dbc != null && !World.dbc.isEstablished()){
			if(!World.dbc.tryToConnect(false)){
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,
						"Could not connect to database: " + World.dbc.getDBPath() + "\nIs the application already running?",
						"Database Connection Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		sout("Connected to database: " + World.dbc.getDBPath() + "\n\n");

		JFrame frame = new JFrame();
		
		Core core = new Core();
		frame.getContentPane().add(core);
		
		frame.setJMenuBar(new CoreMenu(core));
		
		frame.setTitle("Media Manager");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	public void finalize(){
		if(World.dbc != null)
			World.dbc.disconnect();
	}
}
