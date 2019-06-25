package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.pts2.enums.Availability;
import fr.pts2.enums.ConstraintType;
import fr.pts2.utils.Constraint;
import fr.pts2.utils.SQLConnector;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;

public class ConstraintHandler {
	
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
	
	public static ArrayList<Constraint> getConstraintsFromWeek(User user, int week) {
		ArrayList<Constraint> constraints = new ArrayList<>();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM constraints WHERE (id='"
					+ LoginHandler.retrieveUserID(user.getTrigram()) + "' AND week='" + week + "');");
			
			while (rs.next()) {
				constraints.add(new Constraint(ConstraintType.CONSTRAINT,
						Availability.fromString(rs.getString("constraints")), rs.getInt("day"), rs.getInt("intervals")));
				for(Constraint c : constraints) {
					Utils.logSQL("Constraint retrieved: " + c.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}

	public static void createOrUpdateConstraint(User user, int week, int day, int interval, Availability constraint, boolean isFixed) {
		try {
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM constraints WHERE" + "(id=" + LoginHandler.retrieveUserID(user.getTrigram())
					+ " AND week=" + week + " AND day=" + day + " AND intervals=" + interval + ");");

			if(!isFixed) {
				// In case the user edits a constraint back to available, delete
				// the constraint.
				if (constraint == Availability.AVAILABLE) {
					if (rs.next()) {
						st.executeUpdate("DELETE FROM constraints WHERE" + "(id="
								+ LoginHandler.retrieveUserID(user.getTrigram()) + " AND week=" + week + " AND day="
								+ day + " AND intervals=" + interval + ");");
						Utils.log("A constraint has been deleted because the user setted it back to AVAILABLE.");
						return;
					} else return;
				}
			}

			// If the constraints already exists, update it.
			if (rs.next()) {
				st.executeUpdate("UPDATE constraints SET constraints='" + constraint.toString() + "' WHERE" + "(id="
						+ LoginHandler.retrieveUserID(user.getTrigram()) + " AND week=" + week + " AND day=" + day + " AND intervals="
						+ interval + ");");
				Utils.logSQL("Updated constraint: S" + week + "_" + day + "_" + interval + "_" + constraint.toString());
				// If it doesn't exist, create the constraint.
			} else {
				st.executeUpdate("INSERT INTO constraints(id, week, day, intervals, constraints) VALUES("
						+ LoginHandler.retrieveUserID(user.getTrigram()) + "," + week + "," + day + "," + interval + ",'"
						+ constraint.toString() + "');");
				Utils.logSQL("Added constraint: S" + week + "_" + day + "_" + interval + "_" + constraint.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
