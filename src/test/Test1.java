package test;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.Test;

import dataContainers.AudioFile;
import dataContainers.MediaFile;
import dataContainers.MediaLibrary;

import util.DBUtil;
import util.Util;
import util.World;

public class Test1 {

//	@Test
//	public void test() {
//		System.out.print("Go!");
//		
//		fail("Not yet implemented");
//	}
	
	@Test
	public void checkRelativeLocations(){
		String path = Util.relPath("/audio.png");
		// Check to set that we get a path
		assertNotNull(path);
		System.out.print("Path = " + path + "\n");
	}
	
	@Test
	public void checkLoadGraphic(){
		Image img = null;
		img = Util.loadImgRes("/audio.bmp");
	
		// check for existance
		assertNotNull(img);
		
		// check for legality
		assertFalse(img.getWidth(null) == -1);
	}
	
	@Test
	public void checkLoadAudio(){
		World.dbc = new DBUtil();
		if(World.dbc != null && !World.dbc.Connected()){
			if(!World.dbc.Connect()){
				fail("Could not connect to database");
//				JFrame frame = new JFrame();
//				JOptionPane.showMessageDialog(frame,
//						"Could not connect to database: " + DBUtil.DBPath + DBUtil.DBName + "\nIs the application already running?",
//						"Database Connection Error",
//						JOptionPane.ERROR_MESSAGE);
//				return;
			}
		}
		System.out.print("Connected to database: " + DBUtil.DBPath + DBUtil.DBName + "\n\n");
	}
	
	@Test
	public void checkBuildAudio(){
		MediaLibrary testLib = new MediaLibrary();
		ResultSet results = null;
		AudioFile file = AudioFile.createFromDB(testLib, results);
	}
	
	@Test
	public void checkMediaLibrary(){
		MediaLibrary testLib = new MediaLibrary();
		assertTrue(testLib.size() == 0);
		
		MediaLibrary sub = new MediaLibrary();
		testLib.addLibrary(sub);
		
		assertTrue(testLib.m_subLibrary.size() == 1);
		
		MediaFile file = new MediaFile(new File("C:\\"), testLib);
		testLib.add(file);
		
		assertTrue(testLib.size() == 1);
	}
	
	@Test
	public void checkFileTypes(){
		
	}

}
