package util;
// This file is verified
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static gui.MainApplication.sout;

public class DBUtil {
    private boolean connectionEstablished = false;
    private boolean connectionInitialized = false;
    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;

    private String DBName = "MediaData";
    private String DBPath = "data\\";
    private String DBArg = "jdbc:h2:" + DBPath + DBName + ";";
    private String DBUsername = "sa";
    private String DBPassword = "";

    public static final String createTablesSql = System.getProperty("user.dir")+"\\CreateTables.sql";
    public static final File createTablesSqlFile = new File(createTablesSql);

    // Only exists for testing until Spring is implemented
    public DBUtil() {
        initiateConnect();
    }

    public DBUtil(String dbName, String dbPath, String dbArg, String dbUsername, String dbPassword) {
        this.DBName = dbName;
        this.DBPath = dbPath;
        this.DBArg  = dbArg + dbPath + dbName + ";";
        this.DBUsername = dbUsername;
        this.DBPassword = dbPassword;
        initiateConnect();
    }

    public String getDBPath() {
        return DBPath + DBName;
    }

    public boolean isLocked() {
        File lock = new File(System.getProperty("user.dir") + "\\" + getDBPath() + ".lock.db");
        return lock.exists();
    }

    public boolean isEstablished() {
        return connectionEstablished;
    }
    public boolean isInitialized() {
        return connectionInitialized;
    }

    /**
     * Tries to commence connection with the database.
     */
    public void initiateConnect() {
        if(isLocked()) {
            sout("Database is Locked!");
            return;
        }
        sout("Creating connection to database: " + DBArg);
        //try to connect, expect failure if database is non-existant
        if (tryToConnect(false)) {
            //The database exists! Ensure the tables exist
            if(queryTest("SELECT * FROM Sync")) {
                connectionInitialized = true;
            } else {
                //Files table not found, rebuild database
                if(!resetTables()) {
                    sout("Failed to rebuild the database!");
                } else {
                    sout("Database initialized!");
                }
            }
        } else {
            //file doesn't exist or cannot be accessed
            if(!tryToConnect(true)) {
                if(!resetTables()) {
                    sout("Failed to initialize the database!");
                }
            } else {
                sout("Failed to create the database!");
            }
        }
    }

    /**
     * Forms connection with the H2 database.
     * @param forceExist
     *          If true, creates database in case it doesn't exist.
     * @return connection success.
     */
    public boolean tryToConnect(boolean forceExist) {
        if(isEstablished()) {
            sout("Cannot connect to a connected database!");
            return false;
        }
        //connect to the database
        try {
            if (forceExist) {
                connection = DriverManager.getConnection(DBArg, DBUsername, DBPassword); //connect normally, creates if doesn't exist by default
            } else {
                connection = DriverManager.getConnection(DBArg+"ifexists=true", DBUsername, DBPassword); // ensure it exists, fail otherwise
                statement = connection.createStatement();
                connectionEstablished = true;
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            if(World.SQLDEBUG) e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param arg
     * @return
     */
    public ResultSet query(String arg) {
        if(!isEstablished()) {
            sout("Cannot query on a disconnected database!");
            return null;
        }
        if (arg.isEmpty() || arg == null) {
            sout("Cannot execute an empty SQL query!");
            return null;
        }
        try {
            resultSet = statement.executeQuery(arg);
        } catch (SQLException e) {
            if(World.SQLDEBUG){
                sout("Error executing query [" + arg + "]!");
                e.printStackTrace();
            }
            resultSet = null;
        }
        return resultSet;
    }

    /**
     * Used to see if table exists, query to a non-existent table throws an exception.
     * @param arg
     * @return
     */
    public boolean queryTest(String arg) {
        if(!isEstablished()) {
            sout("Cannot query on a disconnected database!");
            return false;
        }
        if (arg.isEmpty() || arg == null) {
            sout("Cannot execute an empty SQL query!");
            return false;
        }
        try {
            resultSet = statement.executeQuery(arg);
            return true;
        } catch (SQLException e) {
            if(World.SQLDEBUG) e.printStackTrace();
            resultSet = null;
            return false;
        }
    }

    /**
     *
     * @param arg
     * @return
     */
    public boolean execute(String arg) {
        if(!isEstablished()) {
            sout("Cannot execute on a disconnected database!");
            return false;
        }
        if (arg.isEmpty() || arg == null) {
            sout("Cannot execute an empty SQL query!");
            return false;
        }
        boolean success = false;
        try {
            sout(arg);
            statement.execute(arg);
            return true;
        } catch (SQLException e) {
            if(World.SQLDEBUG) e.printStackTrace();
        }
        return success;
    }

    /**
     * Attempts to disconnect from the H2 database.
     * @return if success.
     */
    public boolean disconnect() {
        if(!isEstablished()){
            sout("Cannot disconnect from an unconnected database!");
            return false;
        }
        try {
            if(resultSet != null) resultSet.close();
            if(statement != null) statement.close();
            if(connection != null) connection.close();
            connectionEstablished = false;
        } catch (SQLException e) {
            if(World.SQLDEBUG) e.printStackTrace();
            sout("Could not close a component of DBUtil!");
            return false;
        }
        return true;
    }

    public boolean executeFile(File file) {
        return execute("RUNSCRIPT FROM '" + file.getAbsolutePath() + "'");
    }

    public boolean resetTables() {
        if(!isEstablished()) {
            sout("Cannot reset disconnected database!");
            return false;
        }
        execute("DROP ALL OBJECTS");
        connectionInitialized = false;
        if(!executeFile(createTablesSqlFile)) { //make the database tables and structure
            sout("Could not construct the database!");
            return false;
        }
        connectionInitialized = true;
        return true;
    }

//    public boolean Remove() {
//        //*
//        if(!isEstablished()) {
//            tryToConnect(false);
//        }
//        execute("DROP ALL OBJECTS DELETE FILES");
//        disconnect();
//        //Remove the now empty database directory
//        new File(System.getProperty("user.dir")+"\\"+DBPath).delete();
//        return true;
//    }

    public void finalize() {
        if(isEstablished()) {
            disconnect();
        }
    }
}