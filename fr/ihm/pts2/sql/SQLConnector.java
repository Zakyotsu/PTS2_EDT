package fr.ihm.pts2.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

	private static Connection connection;
	private static String host = "";
	private static String port = "";
	private static String database = "";
	private static String username = "";
	private static String password = "";
	private static String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=true&verifyServerCertificate=false&useSSL=true";
	
	public SQLConnector(String host, String port, String database, String username, String password) {
		SQLConnector.host = host;
		SQLConnector.port = port;
		SQLConnector.database = database;
		SQLConnector.username = username;
		SQLConnector.password = password;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {}
		connect();
	}
	
	public static void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean isConnected() {
		try {
			if ((connection == null) || (connection.isClosed()) || !(connection.isValid(5))) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Connection getDatabase() {
		return connection;
	}
}
