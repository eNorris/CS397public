package util;
// This file is verified
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static gui.MainApplication.sout;

public class MiscSQL {
    public static HashMap<String, Boolean> optimizePathModifications(ArrayList<File> files) {
        final Boolean initBool = false;
        // TODO : Has the flaw that it might fail if two users have the same path. Add unique Path limiter
        ResultSet sqlResult = World.dbc.query("Select * from Paths");
        HashMap<String, Boolean> currentPaths = new HashMap<String, Boolean>();
        try {
            while (sqlResult.next()) {
                currentPaths.put(sqlResult.getString("Path"), initBool);
            }
        } catch (SQLException e) {
            sout("MiscSQL threw a SQLException");
        }
        for (File file : files) {
            String path = file.getAbsolutePath();
            if (currentPaths.containsKey(path)) {
                currentPaths.remove(path);
            } else {
                currentPaths.put(path, !initBool);
            }
        }
        return currentPaths;
    }

    @Deprecated
    public static ArrayList<File> resolvePathConflicts(ArrayList<File> files) {
        final Boolean initBool = false;
        ResultSet sqlResult = World.dbc.query("Select * from Paths");
        HashMap<String, Boolean> currentPaths = new HashMap<String, Boolean>();
        try {
            while (sqlResult.next()) {
                currentPaths.put(sqlResult.getString("Path"), initBool);
            }
        } catch (SQLException e) {
            sout("MiscSQL threw a SQLException");
        }
        for (File file : files) {
            String path = file.getAbsolutePath();
            if (currentPaths.containsKey(path)) {
                currentPaths.remove(path);
            } else {
                currentPaths.put(path, !initBool);
            }
        }
        for (String path : currentPaths.keySet()) {
            files.add(new File(path));
        }
        return files;
    }

    public static ArrayList<File> verifyFilesExist(ArrayList<File> files) {
        for (File file : files) {
            if (!file.exists()) {
                files.remove(file);
            }
        }
        return files;
    }
}
