package fr.pts2.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import fr.pts2.Role;
import fr.pts2.Utils;

public class SQLAPI {
	
	private static Connection c = SQLConnector.getConnection();

	public static void checkTables() {
		try {
			DatabaseMetaData dbm = c.getMetaData();
			Statement st = c.createStatement();
			
			if(!dbm.getTables(null, null, "users", null).next()) {
				st.executeUpdate("CREATE TABLE users(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16) NOT NULL, lastname VARCHAR(16) NOT NULL, password VARCHAR(64) NOT NULL, role VARCHAR(10) NOT NULL);");
			}
			
			if(!dbm.getTables(null, null, "constraints", null).next()) {
				st.executeUpdate("CREATE TABLE constraints(id INT NOT NULL, week INT NOT NULL, day INT NOT NULL, intervals INT NOT NULL, constraints VARCHAR(1) NOT NULL);");
				st.executeUpdate("ALTER TABLE constraints ADD CONSTRAINT FK_CONSTRAINT_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
			
			if(!dbm.getTables(null, null, "fixed_constraints", null).next()) {
				st.executeUpdate("CREATE TABLE fixed_constraints(id INT NOT NULL, week INT NOT NULL, day INT NOT NULL, intervals INT NOT NULL, constraints VARCHAR(1) NOT NULL);");
				st.executeUpdate("ALTER TABLE fixed_constraints ADD CONSTRAINT FK_FIXEDCONSTRAINTS_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean userExists(String username) {
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
	
	public static boolean isPasswordGood(String username, String password) {
		username = username.toUpperCase();
		try {
			if(userExists(username)) {
				byte[] encoded = Base64.getEncoder().encode(password.getBytes());
				
				ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (lastname='" + username + "');");
				if(rs.next()) {
					if(rs.getString("password").equalsIgnoreCase(new String(encoded))) {
						return true;
					}
				}
			} else Utils.logErr("Couldn't retrieve password: User doesn't exist!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String[] getUserStrings(String username) {
		username = username.toUpperCase();
		String[] names = new String[2];
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT name, lastname FROM users WHERE (lastname='" + username + "');");
			
			if(rs.next()) {
				names[0] = rs.getString("name");
				names[1] = rs.getString("lastname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	public static int retrieveUserID(String username) {
		username = username.toUpperCase();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (lastname='" + username + "');");
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			Utils.logErr("Couldn't retrieve user id for " + username +"!");
		}
		return 0;
	}
	
	public static String[] getConstraintsFromWeek(Connection c, String username, int week) {
		username = username.toUpperCase();
		String[] constraints =  new String[24];
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM constraints WHERE (id='" + retrieveUserID(username) + "' AND week='" + week + "');");
			int i = 0;
			
			while(rs.next()) {
				constraints[i] = "S"+ rs.getInt("week") + "_" + rs.getInt("day") + "_" + rs.getInt("intervals") + "_" + rs.getString("constraints");
				Utils.log("User ID: " +  retrieveUserID(username) + " fixed constraint: " + constraints[i]);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}
	
	public static Role getRoleFromID(String username) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT role FROM users WHERE (id='" + retrieveUserID(username) + "');");
			
			if(rs.next()) {
				switch(rs.getString("role")) {
				case "ADMIN":
					return Role.ADMIN;
					
				case "PROF":
					return Role.PROF;
					
				case "RESP":
					return Role.RESP;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Role.DEFAULT;
	}
} 
