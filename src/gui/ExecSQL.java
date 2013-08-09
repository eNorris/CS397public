package gui;
// This file is verified
import util.Util;
import util.World;

import java.io.File;

import static gui.MainApplication.sout;

public class ExecSQL implements Runnable{
    public void run(){
        sout("Waiting for launch SQL to be generated");
        File sqlFile = new File(Util.relPath("/sql.sql"));
        while(!sqlFile.exists()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sout("The launch SQL has been generated; proceeding to parse [" + sqlFile.getName() + "]");

        if(World.dbc.executeFile(sqlFile)) {
            sqlFile.delete();
        } else {
            sout("Failed to execute sql file: " + sqlFile.getAbsolutePath());
        }

        System.out.println("@ExecSQL::run(): executing final task before death - updating wall from database");
        Wall.singleton.currentLib.constructFromDB();
        Wall.singleton.repaint();
    }
}
