package util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static gui.MainApplication.getUsername;

public class SQLBuilder {
    public static final String QUERY_PATHS = "Select * from Paths";

    /**
     * Writes a SQL query to insert a user and their password into the User table.
     * @param username The user's alias.
     * @param password The user's password.
     * @return a generated SQL query for submitting to the database.
     */
    public static String addUser(final String username, final String password) {
        String sql = "";
        if (username.isEmpty() || username == null) {
            return sql;
        }
        if (password == null) {
            return sql;
        }
        sql = "Insert into User Values ('"
                + username + "', '"
                + password + "');";
        return sql;
    }

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
