//package util;
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class DBUtil {
//	private static boolean m_connected = false;
//	private static Connection m_connection = null;
//	private static ResultSet m_resultSet = null;
//	private static Statement m_statement = null;
//
//	public static final String DBName = "MediaData";
//	public static final String DBPath = "data\\";
//	public static final String DBArg = "jdbc:h2:" + DBPath + DBName + ";";
//	public static final String DBUsername = "sa";
//	public static final String DBPassword = "";
//
//	public static final String CreateTablesSql = System.getProperty("user.dir")+"\\CreateTables.sql";
//
//	public DBUtil(){
//		System.out.print("Creating connection to database: " + DBArg + "\n");
//		//try to connect, expect failure if database is non-existant
//		if(Connect()){
//			//The database exists! Ensure the tables exist
//			if(!QueryTest("SELECT * FROM Sync")){
//				//Files table not found, rebuild database
//				//System.out.print("Database needs rebuilt!\n");
//				if(!ResetTables()){
//					System.out.print("Failed to rebuild the database!\n");
//				}
//			}
//			Disconnect();
//		} else {
//			//file doesn't exist or cannot be accessed
//			//System.out.print("Database does not exist!\n");
//			if(!ResetTables()){
//				System.out.print("Failed to initialize the database!\n");
//			}
//		}
//	}
//
//	public boolean Connected(){
//		return m_connected;
//	}
//
//	public boolean Connect(boolean ForceExist){
//		if(m_connected){
//			System.out.print("Cannot connect to a connected database!\n");
//			return false; //cannot connect to a connected DB
//		}
//		//connect to the database
//		try {
//			if(ForceExist)
//				m_connection = DriverManager.getConnection(DBArg, DBUsername, DBPassword); //connect normally, creates if doesn't exist by default
//			else
//				m_connection = DriverManager.getConnection(DBArg+"ifexists=true", DBUsername, DBPassword); // ensure it exists, fail otherwise
//			m_statement = m_connection.createStatement();
//			m_connected = true;
//			return true;
//		} catch (SQLException e) {
//			//e.printStackTrace();
//			//System.out.print("Error connecting to the database!\n");
//		}
//		return false;
//	}
//
//	public boolean Connect(){
//		return Connect(false);
//	}
//
//
//	//to use a ResultSet...
//	//while (resultSet.next()) {
//	// System.out.println("EMPLOYEE NAME:" + resultSet.getString("EMPNAME"));
//	//}
//	public ResultSet Query(String arg){
//		if(!m_connected){
//			System.out.print("Cannot query on a disconnected database!\n");
//			return null; //cannot query a disconnected DB
//		}
//		try {
//			m_resultSet = m_statement.executeQuery(arg);
//		} catch (SQLException e) {
//			//e.printStackTrace();
//			System.out.print("Error executing query \"" + arg + "\"!\n");
//			m_resultSet = null;
//		}
//		return m_resultSet;
//	}
//
//	//Used to see if table exists, query to a non-existant table throws an exception
//	public boolean QueryTest(String arg){
//		if(!m_connected){
//			System.out.print("Cannot query on a disconnected database!\n");
//			return false; //cannot query a disconnected DB
//		}
//		try {
//			m_resultSet = m_statement.executeQuery(arg);
//			return true;
//		} catch (SQLException e) {
//			//e.printStackTrace();
//			//System.out.print("Failure running query: \"" + arg + "\"\n"); //expected if no table
//			m_resultSet = null;
//			return false;
//		}
//	}
//
//	public boolean Execute(String arg){
//		if(!m_connected){
//			System.out.print("Cannot execute on a disconnected database!\n");
//			return false; //cannot execute on a disconnected DB
//		}
//		boolean success = false;
//		try {
//			success = m_statement.execute(arg);
//			success = true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			//System.out.print("Failure executing command: \"" + arg + "\"\n");
//			success = false;
//		}
//		return success;
//	}
//
//	public boolean Disconnect(){
//		if(!m_connected)
//		{
//			System.out.print("Cannot disconnect from disconnected database!\n");
//			return false; //Cannot disconnect from disconnected DB
//		}
//		//disconnect from the database
//		try {
//			if(m_resultSet != null) m_resultSet.close();
//			if(m_statement != null) m_statement.close();
//			if(m_connection != null) m_connection.close();
//			m_connected = false;
//		} catch (SQLException e) {
//			//e.printStackTrace();
//			System.out.print("Could not close a component of DBUtil!\n");
//			return false;
//		}
//		return true;
//	}
//
//	public boolean ExecuteFile(String arg) {
//		return Execute("RUNSCRIPT FROM '"+arg+"'");
//	}
//
//	public boolean ResetTables() {
//		boolean initconnected = m_connected;
//		if(initconnected || Connect(true)) { //try to make the database//System.out.print("Building the database structure.\n");
//			Execute("DROP ALL OBJECTS");
//			if(!ExecuteFile(CreateTablesSql)){ //make the database tables and structure
//				System.out.print("Could not construct the database!\n");
//				if(!initconnected)
//					Disconnect();
//				return false;
//			}
//			if(!initconnected)
//				Disconnect();
//			return true;
//		}
//		System.out.print("Failed to connect in order to build the database structure\n");
//		return false;
//	}
//
//	public boolean Remove(){
//		//*
//		if(!m_connected) Connect();
//		Execute("DROP ALL OBJECTS DELETE FILES");
//		Disconnect();
//		new File(System.getProperty("user.dir")+"\\"+DBPath).delete();
//		//*/
//		/*
//if(m_connected) Disconnect();
////System.out.print("Removing the database file: "+System.getProperty("user.dir")+"\\"+DBPath+DBName+".h2.db"+"\n");
//new File(System.getProperty("user.dir")+"\\"+DBPath+DBName+".h2.db").delete();
////System.out.print("Removing the database file: "+System.getProperty("user.dir")+"\\"+DBPath+DBName+".trace.db"+"\n");
//new File(System.getProperty("user.dir")+"\\"+DBPath+DBName+".trace.db").delete();
////System.out.print("Removing the database folder: "+System.getProperty("user.dir")+"\\"+DBPath+"\n");
//new File(System.getProperty("user.dir")+"\\"+DBPath).delete();
////*/
//		return true;
//	}
//}

package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static boolean m_connected = false;
	private static boolean m_initialized = false;
	private static Connection m_connection = null;
	private static ResultSet m_resultSet = null;
	private static Statement m_statement = null;

	public static final String DBName = "MediaData";
	public static final String DBPath = "data\\";
	public static final String DBArg = "jdbc:h2:" + DBPath + DBName + ";";
	public static final String DBUsername = "sa";
	public static final String DBPassword = "";

	public static final String CreateTablesSql = System.getProperty("user.dir")+"\\CreateTables.sql";

	public DBUtil() {
		if(Locked()) {
			System.out.print("Database is Locked!\n");
			return;
		}
		System.out.print("Creating connection to database: " + DBArg + "\n");
		//try to connect, expect failure if database is non-existant
		if(Connect()) {
			//The database exists! Ensure the tables exist
			if(QueryTest("SELECT * FROM Sync")) {
				m_initialized = true;
			} else {
				//Files table not found, rebuild database
				if(!ResetTables()) {
					System.out.print("Failed to rebuild the database!\n");
				} else {
					System.out.print("Database initialized!\n");
				}
			}
		} else {
			//file doesn't exist or cannot be accessed
			if(!Connect(true)) {
				if(!ResetTables()) {
					System.out.print("Failed to initialize the database!\n");
				}
			} else {
				System.out.print("Failed to create the database!\n");
			}
		}
	}

	public boolean Locked() {
		//System.out.print(System.getProperty("user.dir")+"\\"+DBPath+DBName+".lock.db\n");
		File f = new File(System.getProperty("user.dir")+"\\"+DBPath+DBName+".lock.db");
		return f.exists();
	}

	public boolean Connected() {
		return m_connected;
	}
	public boolean Initialized() {
		return m_initialized;
	}

	public boolean Connect(boolean ForceExist) {
		if(m_connected) {
			System.out.print("Cannot connect to a connected database!\n");
			return false; //cannot connect to a connected DB
		}
		//connect to the database
		try {
			if(ForceExist)
				m_connection = DriverManager.getConnection(DBArg, DBUsername, DBPassword); //connect normally, creates if doesn't exist by default
			else
				m_connection = DriverManager.getConnection(DBArg+"ifexists=true", DBUsername, DBPassword); // ensure it exists, fail otherwise
			m_statement = m_connection.createStatement();
			m_connected = true;
			return true;
		} catch (SQLException e) {
			if(World.SQLDEBUG) e.printStackTrace();
		}
		return false;
	}

	public boolean Connect() {
		return Connect(false);
	}


	//to use a ResultSet...
	//while (resultSet.next()) {
	// System.out.println("COLUMN_NAME: " + resultSet.getString("COLUMN_NAME") + "\n");
	//}
	public ResultSet Query(String arg) {
		if(!m_connected) {
			System.out.print("Cannot query on a disconnected database!\n");
			return null;
		}
		try {
			m_resultSet = m_statement.executeQuery(arg);
		} catch (SQLException e) {
			if(World.SQLDEBUG){
				System.out.print("Error executing query \"" + arg + "\"!\n");
				e.printStackTrace();
			}
			m_resultSet = null;
		}
		return m_resultSet;
	}

	//Used to see if table exists, query to a non-existant table throws an exception
	public boolean QueryTest(String arg) {
		if(!m_connected) {
			System.out.print("Cannot query on a disconnected database!\n");
			return false;
		}
		try {
			m_resultSet = m_statement.executeQuery(arg);
			return true;
		} catch (SQLException e) {
			if(World.SQLDEBUG) e.printStackTrace();
			m_resultSet = null;
			return false;
		}
	}

	public boolean Execute(String arg) {
		if(!m_connected) {
			System.out.print("Cannot execute on a disconnected database!\n");
			return false;
		}
		boolean success = false;
		try {
			success = m_statement.execute(arg);
			success = true;
		} catch (SQLException e) {
			if(World.SQLDEBUG) e.printStackTrace();
			//System.out.print("Failure executing command: \"" + arg + "\"\n");
			success = false;
		}
		return success;
	}

	public boolean Disconnect() {
		if(!m_connected){
			System.out.print("Cannot disconnect from disconnected database!\n");
			return false;
		}
		//disconnect from the database
		try {
			if(m_resultSet != null) m_resultSet.close();
			if(m_statement != null) m_statement.close();
			if(m_connection != null) m_connection.close();
			m_connected = false;
		} catch (SQLException e) {
			if(World.SQLDEBUG) e.printStackTrace();
			System.out.print("Could not close a component of DBUtil!\n");
			return false;
		}
		return true;
	}

	public boolean ExecuteFile(String arg) {
		return Execute("RUNSCRIPT FROM '"+arg+"'");
	}

	public boolean ResetTables() {
		if(!m_connected) { 
			System.out.print("Cannot reset disconnected database!\n");
			return false;
		}
		//try to make the database//System.out.print("Building the database structure.\n");
		Execute("DROP ALL OBJECTS");
		m_initialized = false;
		if(!ExecuteFile(CreateTablesSql)) { //make the database tables and structure
			System.out.print("Could not construct the database!\n");
			return false;
		}
		m_initialized = true;
		return true;
	}

	public boolean Remove() {
		//*
		if(!m_connected) Connect();
		Execute("DROP ALL OBJECTS DELETE FILES");
		Disconnect();
		//Remove the now empty database directory
		new File(System.getProperty("user.dir")+"\\"+DBPath).delete();
		return true;
	}
	public void finalize() {
		if(m_connected) {
			Disconnect();
		}
	}
}