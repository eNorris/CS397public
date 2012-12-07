package gui;

import java.io.File;

import util.Util;
import util.World;

public class ExecSQL implements Runnable{
	public void run(){
		File lockfile = new File(Util.relPath("/scripts/sqllock"));
System.out.print("@ExecSQL::run(): created new lockfile\n");
		
		while(lockfile.exists()){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
System.out.print("@ExecSQL::run(): the lockfile was removed: proceeding to parse 'sql.sql'\n");
		
		File sqlExecFile = new File(Util.relPath("scripts/sql.sql"));
		if(!sqlExecFile.exists()){
System.out.print("@ExecSQL::run(): could not execute: " + Util.relPath("scripts/sql.sql") + "\n");
			return;
		}
		
		if(!World.dbc.ExecuteFile(sqlExecFile.getAbsolutePath())){
			System.out.print("Failed to execute sql file: " + sqlExecFile.getAbsolutePath() + "\n");
		}
		
System.out.print("@ExecSQL::run(): executing final task before death - updating wall from database");
		Wall.singleton.currentLib.constructFromDB();
		Wall.singleton.repaint();
	}
}
