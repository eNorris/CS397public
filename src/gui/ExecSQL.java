package gui;

import java.io.File;

import util.Util;
import util.World;

public class ExecSQL implements Runnable{
	public void run(){
		File lockfile = new File(Util.relPath("/scripts/sqllock"));
		
		while(lockfile.exists()){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		File sqlExecFile = new File(Util.relPath("/scripts/sql.sql"));
		if(!sqlExecFile.exists())
			return;
		
		if(!World.dbc.ExecuteFile(sqlExecFile.getAbsolutePath())){
			System.out.print("Failed to execute sql file: " + sqlExecFile.getAbsolutePath() + "\n");
		}
		
		
	}
}
