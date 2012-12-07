package gui;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;

import util.SpringEq;
import util.Util;
import util.World;

import dataContainers.AudioFile;
import dataContainers.MediaFile;
import dataContainers.MediaLibrary;

public class Wall extends JPanel{

	private static final long serialVersionUID = -5495354692927105826L;
	public MediaLibrary currentLib = new MediaLibrary(this);
	public MediaLibrary searchLib = new MediaLibrary(this);
	
//	public static boolean showSearchLib = false;
	public static String showSearchLib = "";
	
	private boolean m_moving = false;
	private SpringEq m_springEq = new SpringEq();
	protected Wall self = this;
	
	protected boolean hasLoadedSearch = false;
	
	public Core owner = null;
	
	public static Wall singleton = null;
	
	Wall(Core owner){
		singleton = this;
		this.owner = owner;
		setOpaque(false);
		
		final JPanel m_self = this;
		
		//World.dbc.ResetTables();
		//World.dbc.Execute("INSERT INTO File VALUES('C:\\code\\CS397_2\\CS397private\\library\\', 'test.mp3', 'A', '45', 'exe', 'exe')");
		
		currentLib.constructFromDB();
		
		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent ev) {}
			public void mouseEntered(MouseEvent ev) {}
			public void mouseExited(MouseEvent ev) {}
			
			public void mousePressed(MouseEvent ev) {
				
//				currentLib.get(0).popUpInfo(self, ev.getX(), ev.getY());
//				currentLib.get(0).popUp(self, ev.getX(), ev.getY());
				
//				infoPopup.activate(ev.getX(), ev.getY());
				
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
			}
		});
		
		
		addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent ev) {
				if(m_moving){
					currentLib.space.universalUpdate(ev.getX(), ev.getY());
					repaint();
				}
			}

			public void mouseMoved(MouseEvent ev) {
				// TODO - uncomment when InfoPopUp is done
//				for(MediaFile f : currentLib){
//					f.spaceListener.update(ev.getX(), ev.getY());
//				}
			}
		});
		
		// Start the timer
		World.space.bigBang();
		
		// Initialize the spring equation
		m_springEq.mass = 1.0;
		m_springEq.spring = 1.0;
		m_springEq.dampen = m_springEq.getCriticalDampening();
//		m_springEq.A = 10.0;
	}
	
	public void paint(Graphics g){
		Util.drawGradientBackground(this, g);

		super.paint(g);
		
		if(!showSearchLib.equals("")){
			if(!hasLoadedSearch)
				searchLib.searchLib(currentLib, showSearchLib);
			searchLib.distribute(this.getHeight());
			searchLib.draw(g);
		}else{
			currentLib.distribute(this.getHeight());
			currentLib.draw(g);
		}
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
	
	public void loadFiles(String directory){
		File directoryFile = new File(directory);
		File[] files = directoryFile.listFiles();
		
		for(int i = 0; i < files.length; i++)
			System.out.print("Got " + files[i] + "\n");
		
		for(int i = 0; i < files.length; i++){
			AudioFile a = new AudioFile(files[i], currentLib);
			a.loadImg(Util.relPath("graphics/pic1.bmp"));
			currentLib.add(a);
		}
	}
	
}





















