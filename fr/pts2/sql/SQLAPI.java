package fr.pts2.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import fr.pts2.utils.SQLConnector;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;

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
				st.executeUpdate("CREATE TABLE constraints(id INT NOT NULL, week INT NOT NULL, day INT NOT NULL, intervals INT NOT NULL, constraints VARCHAR(16) NOT NULL);");
				st.executeUpdate("ALTER TABLE constraints ADD CONSTRAINT FK_CONSTRAINT_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
			
			if(!dbm.getTables(null, null, "fixed_constraints", null).next()) {
				st.executeUpdate("CREATE TABLE fixed_constraints(id INT NOT NULL, day INT NOT NULL, intervals INT NOT NULL, constraints VARCHAR(16) NOT NULL);");
				st.executeUpdate("ALTER TABLE fixed_constraints ADD CONSTRAINT FK_FIXEDCONSTRAINTS_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
			
			if(!dbm.getTables(null, null, "weekbuilded", null).next()) {
				st.executeUpdate("CREATE TABLE weekbuilded(week INT NOT NULL, builded BOOLEAN NOT NULL);");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean userExists(String username) {
		username = username.toUpperCase();
		try {
			if(c.createStatement().executeQuery("SELECT * FROM users WHERE (trigram='" + username + "');").next()) {
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
				
				ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (trigram='" + username + "');");
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
	
	public static User getUser(int uuid) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (id=" + uuid + ");");
			
			if(rs.next()) {
				return new User(rs.getString("name"), rs.getString("lastname"), rs.getString("trigram"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int retrieveUserID(String trigram) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (trigram='" + trigram + "');");
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			Utils.logErr("Couldn't retrieve user id for trigram: " + trigram + ".");
		}
		return 0;
	}
}
