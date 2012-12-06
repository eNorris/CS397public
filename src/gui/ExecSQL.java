package gui;

import java.io.File;

import util.Util;

public class ExecSQL implements Runnable{
	public void run(){
		File lockfile = new File(Util.relPath("/data/sqllock"));
		
		while(lockfile.exists()){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// FIXME - do stuff
		File sqlExecFile = new File(Util.relPath("/data/query.sql"));
		if(!sqlExecFile.exists())
			return;
		
		
	}
}
