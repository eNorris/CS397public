package util;

import gui.Core;
import gui.ScheduleFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import santhoshTree.CheckTreeManager;

public class World {
	public static Config config = new Config();
	public static SpaceTimeInt space = new SpaceTimeInt();
	public static Random rand = new Random();
	public static ArrayList<File> crawlerDirs = new ArrayList<File>();
	public static CheckTreeManager treeManager = null;
	
	public static ScheduleFrame scheduleFrame = null;
	public static Core core = null;
	public static DBUtil dbc = null;
	
	public void finalize(){
		if(dbc != null)
			dbc.Disconnect();
	}
}
