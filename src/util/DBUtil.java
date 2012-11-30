package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private boolean m_connected = false;
	private Connection m_connection = null;
	private ResultSet m_resultSet = null;
	private Statement m_statement = null;
	
	public static final String DBPath = "jdbc:h2:/CS397/MediaData;";
	public static final String DBUsername = "sa";
	public static final String DBPassword = "";
	
	public DBUtil(){
	}
	
	public DBUtil(String arg, String uname, String pass){
		try {
			m_connection = DriverManager.getConnection(arg+"IFEXISTS=TRUE", uname, pass);
			m_connection.close();
		} catch (SQLException e1) {
			//file doesn't exist or cannot be accessed
			Connect(arg,uname,pass); //will make the database
			ExecuteFile("C:\\code\\CS397_2\\CS397private\\CreateTables.sql");
//			ExecuteFile("/CS397/CreateTables.sql"); //makes the database tables and structure
			Disconnect();
		}
	}
	
	public boolean Connect(String arg, String uname, String pass){
		if(m_connected)
			return false; //cannot connect to a connected DB
		//connect to the database
		try {
			m_connection = DriverManager.getConnection(arg, uname, pass);
			m_statement = m_connection.createStatement();
			m_connected = true;
			return true;
		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
		}
		return false;
	}
	
	
	//to use a ResultSet...
	//while (resultSet.next()) {
	//	System.out.println("EMPLOYEE NAME:" + resultSet.getString("EMPNAME"));
	//}
	public ResultSet Query(String arg){
		if(!m_connected)
			return null; //cannot query a disconnected DB
		
		try {
			m_resultSet = m_statement.executeQuery(arg);
		} catch (SQLException e) {
			e.printStackTrace();
			m_resultSet = null;
		}
		
		return m_resultSet;
	}
	
	public boolean Execute(String arg){
		if(!m_connected){
			System.out.print("Cannot execute on a disconnected DB\n");
			return false; //cannot execute on a disconnected DB
		}
		boolean success = false;
		try {
			success = m_statement.execute(arg);
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public boolean Disconnect(){
		if(!m_connected)
		{
			System.out.print("Cannot disconnect from disconnected DB\n");
			return false; //Cannot disconnect from disconnected DB
		}
		//disconnect from the database
		try {
			if(m_resultSet != null) m_resultSet.close();
			if(m_statement != null) m_statement.close();
			if(m_connection != null) m_connection.close();
			m_connected = false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void ExecuteFile(String arg) {
		Execute("RUNSCRIPT FROM '"+arg+"'");
	}
}
