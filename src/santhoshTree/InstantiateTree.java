package santhoshTree;

import util.SQLBuilder;
import util.World;

import javax.swing.tree.TreePath;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static gui.MainApplication.sout;

public class InstantiateTree {
    public static ArrayList<TreePath> checkTreeFromDatabase() {
        ResultSet paths = World.dbc.query(SQLBuilder.QUERY_PATHS);
        HashMap<String, Boolean> hashedPaths = new HashMap<String, Boolean>();
        ArrayList<TreePath> treePaths = new ArrayList<TreePath>();
        try {
            while (paths.next()) {
                hashedPaths.put(paths.getString("Path"), true);
                sout(paths.getString("Path"));
            }
        } catch (SQLException e) {
            sout("InstantiateTree threw a SQLException reading ResultSet");
        }

//        for (String path : hashedPaths.keySet()) {
//            File file = new File(path);
//            Stack<File> fileStack = new Stack<File>();
//            Object[] objectList = {};
//            while (file!=null) {
//                fileStack.push(file);
//                file = file.getParentFile();
//            }
//            FileSystemModel model = new FileSystemModel(fileStack.pop());
//            while (!fileStack.isEmpty()) {
//                model=model.getIndexOfChild();
//            }
//            treePaths.add(treePath);
//        }

        for (String path : hashedPaths.keySet()) {
            File file = new File(path);
            Stack<File> fileStack = new Stack<File>();
            Object[] objectList = {};
            while (file!=null) {
                fileStack.push(file);
                file = file.getParentFile();
            }
            TreePath treePath = new TreePath(fileStack.pop());
            while (!fileStack.isEmpty()) {
                treePath=treePath.pathByAddingChild(fileStack.pop());

            }
            treePaths.add(treePath);
        }

        // TODO : Fill tree here (pass in tree if necessary)
        return treePaths;
    }
}
