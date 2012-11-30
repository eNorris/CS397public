package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

import util.DBUtil;
import util.SpringEq;
import util.World;

import dataContainers.MediaFile;
import dataContainers.MediaLibrary;

public class Wall extends JPanel{

	private static final long serialVersionUID = -5495354692927105826L;
	public MediaLibrary currentLib = new MediaLibrary();
	
	private boolean m_moving = false;
	
	private SpringEq m_springEq = new SpringEq();
	

	
//	private int m_wallHeight = 0, m_wallWidth = 0;

	Wall(){
		setBackground(Color.BLUE);
		
		final JPanel m_self = this;

//		World.dbc = new DBUtil(DBUtil.DBPath, DBUtil.DBUsername, DBUtil.DBPassword);
//		World.dbc.Connect(DBUtil.DBPath, DBUtil.DBUsername, DBUtil.DBPassword);
		
		try {
			ResultSet result = World.dbc.Query("SELECT * FROM File");
			while (result != null && result.next()){
				//TODO: Load database with sample dater
				//TODO: Fix determining filetype
				String filetype = result.getString("Type");
				System.out.print("filetype found: " + filetype + "\n");
				currentLib.add(new MediaFile(result.getString("Path"), "/CS397/graphics/pic1.bmp", currentLib));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		World.dbc.Disconnect();
		
//		for(int i = 0; i < 150; i++){
//			
//			int randomness = World.rand.nextInt();
//			
//			// Randomly seed with some files
//			if(randomness % 3 == 0){
//				currentLib.add(new MediaFile("library/doc2.txt", "graphics/thumb1.bmp", currentLib));
//			}else if(randomness % 3 == 1){
//				currentLib.add(new AudioFile("library/test.mp3", "graphics/musicthumb.bmp", currentLib));
//			}else{
//				currentLib.add(new GraphicFile("library/testpic.png", "graphics/picthumb.bmp", currentLib));
//			}
//		}
		
		addMouseListener(new MouseListener(){
			
			public void mouseClicked(MouseEvent ev) {}
			public void mouseEntered(MouseEvent ev) {}
			public void mouseExited(MouseEvent ev) {}
			
			public void mousePressed(MouseEvent ev) {
				
				if(ev.getButton() == MouseEvent.BUTTON1){
					// left click => consume
					m_moving = true;
					
					// Reset space, everything is now relative to where I clicked the mouse
					currentLib.space.wormHole(ev.getX(), ev.getY());
					World.space.wormHole(ev.getX(), ev.getY());
					
				}else if(ev.getButton() == MouseEvent.BUTTON2){
					// midle click => do nothing
				}else{
					// right click => open context menu
					for(int i = 0; i < currentLib.size(); i++){
						if( currentLib.get(i).isInBounds(ev.getX(), ev.getY())){
							currentLib.get(i).popUp(m_self, ev.getX(), ev.getY());
						}
					}
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				m_moving = false;
//				bounder();
				
//for(SpaceTimeInt i : World.space.history){
//	System.out.print(i.toString() + "\n");
//}
//System.out.print("\n=========\n");
				
				
			}
		});
		
		
		addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent ev) {
				if(m_moving){
					currentLib.space.universalUpdate(ev.getX(), ev.getY());
		//			World.space.universalUpdate(ev.getX(), ev.getY());
					repaint();
				}
			}

			public void mouseMoved(MouseEvent arg0) {}
		});
		
		// places the media files on the screen
//		this.setVisible(true);
//System.out.print("Height = " + this.getHeight() + "\n");
		// FIXME - Moved this to paint()
//		currentLib.distribute();
		
		// Start the timer
		World.space.bigBang();
		
		// Initialize the spring equation
		m_springEq.mass = 1.0;
		m_springEq.spring = 1.0;
		m_springEq.dampen = m_springEq.getCriticalDampening();
		m_springEq.A = 10.0;
	}
	
	
	
	public void paint(Graphics g){
		super.paint(g);
		currentLib.distribute(this.getHeight());
		currentLib.draw(g);
	}
	
	public void bounder(){
		System.out.print("bounder!\n");
		currentLib.space.ix += 1;
		if(World.rand.nextInt(10) == 0){
			return;
		}
System.out.print("Should repaint now!");
		this.repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bounder();
//		bounder();
//System.out.print("bounder() @ t = " + World.space.t + ": ");
//		if(m_springEq.critdampen(World.space.t) < 1){
//System.out.print("Done with dampening.)" + m_springEq.critdampen(World.space.t) + ")\n");
//		}else{
//System.out.print("Recurse...(" + m_springEq.critdampen(World.space.t) + ")\n");
//			// FIXME - what about the other values?
//			currentLib.space.ix += m_springEq.critdampen(World.space.t);
//			World.space.temporalUpdate();
//			repaint();
//			if(World.rand.nextInt(30) != 0)
//				bounder();
//		}
	}
	
}





















