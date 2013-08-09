package util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static gui.MainApplication.getUsername;

public class SQLBuilder {
    /**
     * Writes a SQL query to change the contents of the Paths table to match the selected paths.
     * @param paths a hashmap of files and a boolean (true to add, false to remove).
     * @return a built SQL query.
     */
    public static String modifyPaths(HashMap<String, Boolean> paths) {
        String sql = "";
        for (String path : paths.keySet()) {
            if (!new File(path).exists()) {
                paths.put(path, false);
            }
            if (paths.get(path)) {
                sql += "Insert into Paths (Username, Path) Values('"
                        + getUsername() + "', '"
                        + path
                        + "');";
            } else {
                sql += "Delete from Paths Where Path='"
                        + path
                        + "';";
            }
        }
        return sql;
    }

    @Deprecated
    public static String insertIntoPaths(ArrayList<File> paths) {
        String sql = "";
        for (File path : paths) {
            sql += "Insert into Paths (Username, Path) Values('"
                    + getUsername() + "', '"
                    + path.getAbsolutePath()
                    + "');";
        }
        return sql;
    }

    @Deprecated
    public static String deleteFromPaths(ArrayList<File> paths) {
        String sql = "";
        for (File file : paths) {
            sql += "Delete from Paths Where Path='"
                    + file.getAbsolutePath()
                    + "';";
        }
        return sql;
    }
}
