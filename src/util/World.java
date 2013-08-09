package util;

import gui.Core;
import gui.ScheduleFrame;
import santhoshTree.CheckTreeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class World {
	public static Config config = new Config();
	public static SpaceTimeInt space = new SpaceTimeInt();
	public static Random rand = new Random();
	public static ArrayList<File> crawlerDirs = new ArrayList<File>();
	public static CheckTreeManager treeManager = null;
	
	public static ScheduleFrame scheduleFrame = null;
	public static Core core = null;
	public static DBUtil dbc = null;
	
	public static boolean SQLDEBUG = true;
	
	public void finalize(){
		if(dbc != null)
			dbc.disconnect();
	}
}
