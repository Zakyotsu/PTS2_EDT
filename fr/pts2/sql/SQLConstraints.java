package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.pts2.Utils;
import fr.pts2.enums.Constraints;

public class SQLConstraints {

	private static Connection c = SQLConnector.getConnection();

	public static String[] getConstraintsFromWeek(String username, int week) {
		username = username.toUpperCase();
		String[] constraints = new String[24];
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM constraints WHERE (id='"
					+ SQLAPI.retrieveUserID(username) + "' AND week='" + week + "');");
			int i = 0;

			while (rs.next()) {
				constraints[i] = "C" + rs.getInt("week") + "_" + rs.getInt("day") + "_" + rs.getInt("intervals") + "_"
						+ rs.getString("constraints");
				Utils.log("User ID: " + SQLAPI.retrieveUserID(username) + " constraint: " + constraints[i]);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}

	public static void createOrUpdateConstraint(String username, int week, int day, int interval,
			Constraints constraint) {
		username = username.toUpperCase();
		try {
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM constraints WHERE" + "(id=" + SQLAPI.retrieveUserID(username)
					+ " AND week=" + week + " AND day=" + day + " AND intervals=" + interval + ");");

			// In case the user modify an edited constraint back to available, delete the constraint.
			if (constraint == Constraints.AVAILABLE) {
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
				Utils.log("Updated constraint: S" + week + "_" + day + " " + interval + " " + constraint.toString());
				// If it doesn't exist, create the constraint.
			} else {
				st.executeUpdate("INSERT INTO constraints(id, week, day, intervals, constraints) VALUES("
						+ SQLAPI.retrieveUserID(username) + "," + week + "," + day + "," + interval + ",'"
						+ constraint.toString() + "');");
				Utils.log("Added constraint: S" + week + "_" + day + " " + interval + " " + constraint.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
