package fr.ihm.pts2.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLAPI {

	public static void checkTables(Connection c) {
		try {
			DatabaseMetaData dbm = c.getMetaData();
			Statement st = c.createStatement();
			
			if(!dbm.getTables(null, null, "users", null).next()) {
				st.executeUpdate("CREATE TABLE users(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16) NOT NULL, lastname VARCHAR(16) NOT NULL, role VARCHAR(10) NOT NULL);");
			}
			
			if(!dbm.getTables(null, null, "constraints", null).next()) {
				st.executeUpdate("CREATE TABLE constraints(id INT NOT NULL PRIMARY KEY, week INT NOT NULL, day INT NOT NULL, intervals INT NOT NULL, constraints VARCHAR(1) NOT NULL);");
				st.executeUpdate("ALTER TABLE constraints ADD CONSTRAINT FK_CONSTRAINT_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean userExists(Connection c, String username) {
		username = username.toUpperCase();
		try {
			if(c.createStatement().executeQuery("SELECT * FROM users WHERE (lastname='" + username + "');").next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkPassword(Connection c, String username, String password) {
		username = username.toUpperCase();
		try {
			if(c.createStatement().executeQuery("SELECT * FROM users WHERE (lastname='" + username + "');").next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String[] getUserStrings(Connection c, String username) {
		username = username.toUpperCase();
		String[] names = new String[2];
		try {
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM users WHERE (lastname='" + username + "');");
			
			if(rs.next()) {
				names[0] = rs.getString("name");
				names[1] = rs.getString("lastname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
}
