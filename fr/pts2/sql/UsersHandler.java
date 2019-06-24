package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.pts2.utils.SQLConnector;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;

public class UsersHandler {

	private static Connection c = SQLConnector.getConnection();

	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users;");

			while (rs.next()) {
				users.add(new User(rs.getString("name"), rs.getString("lastname"), rs.getString("trigram"), LoginHandler.retrieveUserID(rs.getString("trigram"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static void createUser(User u) {
		try {
			Statement st = c.createStatement();
			st.executeUpdate("INSERT INTO users(name, lastname, trigram, password) VALUES('" + u.getName() + "','" + u.getLastname() + "','"
					+ u.getTrigram() + "', 'MTIz');");
			Utils.log("Added user: " + u.getName() + "_" + u.getLastname() + "_" + u.getTrigram());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUser(String name, String lastname, String trigram) {
		try {
			Statement st = c.createStatement();
			st.executeUpdate("DELETE FROM users WHERE" + "(name='" + name
			+ "' AND lastname='" + lastname + "' AND trigram='" + trigram + "');");
			Utils.log("Deleted user: " + name + "_" + lastname + "_" + trigram);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
