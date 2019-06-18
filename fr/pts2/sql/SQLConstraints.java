package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.pts2.enums.ConstraintAvailability;
import fr.pts2.enums.ConstraintType;
import fr.pts2.utils.Constraint;
import fr.pts2.utils.Utils;

public class SQLConstraints {

	private static Connection c = SQLConnector.getConnection();

	public static String isWeekBuilded(int week) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM weekbuilded WHERE week='" + week + "';");
			
			if(rs.next()) {
				return rs.getBoolean("builded") ? "(construite)" : "(non construite)";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "(non construite)";
	}
	
	public static ArrayList<Constraint> getConstraintsFromWeek(String username, int week) {
		ArrayList<Constraint> constraints = new ArrayList<>();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM constraints WHERE (id='"
					+ SQLAPI.retrieveUserID(username.toUpperCase()) + "' AND week='" + week + "');");
			
			while (rs.next()) {
				constraints.add(new Constraint(ConstraintType.CONSTRAINT,
						ConstraintAvailability.fromString(rs.getString("constraints")),
						rs.getInt("day"),
						rs.getInt("intervals")));
				
				for(Constraint c : constraints) {
					Utils.log("User ID: " + SQLAPI.retrieveUserID(username.toUpperCase()) + ", " + c.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}

	public static void createOrUpdateConstraint(String username, int week, int day, int interval, ConstraintAvailability constraint) {
		username = username.toUpperCase();
		try {
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM constraints WHERE" + "(id=" + SQLAPI.retrieveUserID(username)
					+ " AND week=" + week + " AND day=" + day + " AND intervals=" + interval + ");");

			// In case the user edits a constraint back to available, delete the constraint.
			if (constraint == ConstraintAvailability.AVAILABLE) {
				if (rs.next()) {
					st.executeUpdate("DELETE FROM constraints WHERE" + "(id=" + SQLAPI.retrieveUserID(username)
							+ " AND week=" + week + " AND day=" + day + " AND intervals=" + interval + ");");
					Utils.log("A constraint has been deleted because the user setted it back to AVAILABLE.");
					return;
				} else return;
			}

			// If the constraints already exists, update it.
			if (rs.next()) {
				st.executeUpdate("UPDATE constraints SET constraints='" + constraint.toString() + "' WHERE" + "(id="
						+ SQLAPI.retrieveUserID(username) + " AND week=" + week + " AND day=" + day + " AND intervals="
						+ interval + ");");
				Utils.log("Updated constraint: S" + week + "_" + day + "_" + interval + "_" + constraint.toString());
				// If it doesn't exist, create the constraint.
			} else {
				st.executeUpdate("INSERT INTO constraints(id, week, day, intervals, constraints) VALUES("
						+ SQLAPI.retrieveUserID(username) + "," + week + "," + day + "," + interval + ",'"
						+ constraint.toString() + "');");
				Utils.log("Added constraint: S" + week + "_" + day + "_" + interval + "_" + constraint.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
