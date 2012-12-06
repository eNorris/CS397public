package test;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.sql.ResultSet;

import org.junit.Test;

import dataContainers.AudioFile;
import dataContainers.MediaFile;
import dataContainers.MediaLibrary;

import util.DBUtil;
import util.Util;
import util.World;

public class Test1 {
	
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
			}
		}
		System.out.print("Connected to database: " + DBUtil.DBPath + DBUtil.DBName + "\n\n");
		
		MediaLibrary testLib = new MediaLibrary();
		testLib.constructFromDB();
		
		assertNotNull(testLib);
		
		World.dbc.Execute("INSERT INTO File VALUES('C:\\code\\CS397_2\\CS397private\\library', 'test.mp3', 'A', '5001', 'mp3', 'mp3')");
		
		assertFalse(testLib.size() == 0);
		
		World.dbc.Disconnect();
	}
	
	@Test
	public void checkDB(){
		DBUtil testDB = new DBUtil();
		assertNotNull(testDB);
		
		ResultSet result = testDB.Query("SELECT * FROM File");
		assertNotNull(result);
		testDB.Disconnect();
	}
	
	@Test
	public void checkBuildAudio(){
		MediaLibrary testLib = new MediaLibrary();
		ResultSet results = null;
		AudioFile file = AudioFile.createFromDB(testLib, results);
		assertNull(file);
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
