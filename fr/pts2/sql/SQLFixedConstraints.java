package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pts2.Utils;
import fr.pts2.enums.Constraints;

public class SQLFixedConstraints {

	private static Connection c = SQLConnector.getConnection();

	public static void addFixedConstraint(String username, int day, int interval, Constraints constraint) {
		try {
			c.createStatement().executeUpdate("INSERT INTO fixed_constraints(id,day,intervals,constraints) "
											+ "VALUES(" + SQLAPI.retrieveUserID(username) + "," + day + "," + interval + ",'" + constraint.toString() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFixedConstraint(String username, int day, int interval) {
		try {
			c.createStatement().executeUpdate("DELETE FROM fixed_constraints "
											+ "WHERE(id=" + SQLAPI.retrieveUserID(username) + " AND day=" + day + " AND intervals=" + interval + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getFixedConstraints(String username) {
		username = username.toUpperCase();
		String[] constraints =  new String[24];
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM fixed_constraints WHERE (id=" + SQLAPI.retrieveUserID(username) + ");");
			int i = 0;
			while(rs.next()) {
				constraints[i] = "FC_" + rs.getInt("day") + "_" + rs.getInt("intervals") + "_" + rs.getString("constraints");
				Utils.log("User ID: " +  SQLAPI.retrieveUserID(username) + " fixed constraint: " + constraints[i]);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}
	
}
