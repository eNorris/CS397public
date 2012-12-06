package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import util.World;

/**
 * The main panel that fills the Application version of the program
 * 
 * @author Edward
 *
 */
public class Core extends JPanel{

	/** Generated via Eclipse */
	private static final long serialVersionUID = -5499945898085501882L;
	
	public Wall wall = new Wall(this);
	public CoreMenu coreMenu = new CoreMenu(this);

	/**
	 * Default Constructor <br><br>
	 * 
	 * Sets the layout to BorderLayout, fills WEST with a FileTree and CENTER with a Wall
	 */
	public Core(){
		World.core = this;
		
		setLayout(new BorderLayout());
		
		FileTree filetree = new FileTree();
//		Wall wall = new Wall();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filetree, wall);
		//splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		add(splitPane, BorderLayout.CENTER);
		
		//add(new FileTree(), BorderLayout.WEST);
		//add(new Wall(), BorderLayout.CENTER);
		
		
	}
	
	/**
	 * Paints the components
	 * 
	 * @param g The Graphics object that will draw the screen
	 */
	public void paint(Graphics g){
		super.paint(g);
	}
	
}
