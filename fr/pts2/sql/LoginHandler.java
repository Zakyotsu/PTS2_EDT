package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import fr.pts2.utils.User;
import fr.pts2.utils.Utils;

public class LoginHandler {

	private static Connection c = SQLConnector.getConnection();
	
	public static boolean isPasswordGood(String trigram, String password) {
		try {
			if(userExists(trigram)) {
				byte[] encoded = Base64.getEncoder().encode(password.getBytes());
				
				ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (trigram='" + trigram + "');");
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
	
	public static boolean userExists(String trigram) {
		try {
			if(c.createStatement().executeQuery("SELECT * FROM users WHERE (trigram='" + trigram + "');").next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static User getUserFromUUID(int uuid) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (id=" + uuid + ");");
			
			if(rs.next()) {
				return new User(rs.getString("name"), rs.getString("lastname"), rs.getString("trigram"), uuid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User getUserFromTrigram(String trigram) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users WHERE (trigram='" + trigram + "');");
			
			if(rs.next()) {
				return new User(rs.getString("name"), rs.getString("lastname"), rs.getString("trigram"), rs.getInt("id"));
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
	
	public static void changePassword(User user, String newPassword) {
		try {
			c.createStatement().executeUpdate("UPDATE users SET password='" + new String(Base64.getEncoder().encode(newPassword.getBytes())) + "' WHERE" + "(id="
					+ LoginHandler.retrieveUserID(user.getTrigram()) + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.log("Updated password: ************");
	}
}
