package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.pts2.enums.ConstraintAvailability;
import fr.pts2.enums.ConstraintType;
import fr.pts2.utils.Constraint;
import fr.pts2.utils.SQLConnector;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;

public class SQLFixedConstraints {

	private static Connection c = SQLConnector.getConnection();

	public static void addFixedConstraint(User user, int day, int interval, ConstraintAvailability constraint) {
		try {
			c.createStatement().executeUpdate("INSERT INTO fixed_constraints(id,day,intervals,constraints) "
											+ "VALUES(" + SQLAPI.retrieveUserID(user.getTrigram()) + "," + day + "," + interval + ",'" + constraint.toString() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFixedConstraint(User user, int day, int interval) {
		try {
			c.createStatement().executeUpdate("DELETE FROM fixed_constraints "
											+ "WHERE(id=" + SQLAPI.retrieveUserID(user.getTrigram()) + " AND day=" + day + " AND intervals=" + interval + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Constraint> getFixedConstraints(User user) {
		ArrayList<Constraint> constraints = new ArrayList<>();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM fixed_constraints WHERE (id=" + SQLAPI.retrieveUserID(user.getTrigram()) + ");");
			while (rs.next()) {
				constraints.add(new Constraint(ConstraintType.CONSTRAINT,
						ConstraintAvailability.fromString(rs.getString("constraints")),
						rs.getInt("day"),
						rs.getInt("intervals")));
				
				for(Constraint c : constraints) {
					Utils.log("User ID: " + SQLAPI.retrieveUserID(user.getTrigram()) + ", " + c.toString());
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}
	
	public static String[] getConstraintsFromSchool() {
		String[] constraints =  new String[24];
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM fixed_constraints WHERE (id='0');");
			int i = 0;
			while(rs.next()) {
				constraints[i] = "SC_" + rs.getInt("day") + "_" + rs.getInt("intervals") + "_" + rs.getString("constraints");
				Utils.log("School fixed constraint: " + constraints[i]);
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}
}
