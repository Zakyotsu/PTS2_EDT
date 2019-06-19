package fr.pts2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

	private static Connection connection;
	private String username = "";
	private String password = "";
	private String url;
	
	public SQLConnector(String host, String port, String database, String username, String password) {
		this.username = username;
		this.password = password;
		
		this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=true&verifyServerCertificate=false&useSSL=true";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {}
		connect();
	}
	
	private void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isConnected() {
		try {
			if (connection == null || connection.isClosed()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Connection getConnection() {
		return connection;
	}
}
