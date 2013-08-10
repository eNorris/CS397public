package santhoshTree;

import util.SQLBuilder;
import util.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static gui.MainApplication.sout;

public class InstantiateTree {
    public static void checkTreeFromDatabase() {
        ResultSet paths = World.dbc.query(SQLBuilder.QUERY_PATHS);
        HashMap<String, Boolean> hashedPaths = new HashMap<String, Boolean>();
        try {
            while (paths.next()) {
                hashedPaths.put(paths.getString("Path"), true);
                sout(paths.getString("Path"));
            }
        } catch (SQLException e) {
            sout("InstantiateTree threw a SQLException reading ResultSet");
        }

        // TODO : Fill tree here (pass in tree if necessary)
    }
}
