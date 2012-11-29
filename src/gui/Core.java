package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

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

	/**
	 * Default Constructor <br><br>
	 * 
	 * Sets the layout to BorderLayout, fills WEST with a FileTree and CENTER with a Wall
	 */
	public Core(){
		World.core = this;
		
		setLayout(new BorderLayout());
		
		add(new FileTree(), BorderLayout.WEST);
		add(new Wall(), BorderLayout.CENTER);
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
