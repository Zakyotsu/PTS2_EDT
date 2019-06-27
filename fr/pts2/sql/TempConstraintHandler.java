package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.pts2.enums.Availability;
import fr.pts2.enums.ConstraintType;
import fr.pts2.utils.TempConstraint;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;

public class TempConstraintHandler {

	private static Connection c = SQLConnector.getConnection();

	public static void addTempConstraint(User user, TempConstraint tc) {
		try {
			c.createStatement().executeUpdate("INSERT INTO temp_constraints(id,name,day,intervals,beginning,ending,constraints) "
											+ "VALUES('" 
											+ LoginHandler.retrieveUserID(user.getTrigram())
											+ "','" + tc.getConstraintName()
											+ "','" + tc.getDay()
											+ "','" + tc.getInterval()
											+ "','" + tc.getBeginningWeek()
											+ "','" + tc.getEndingWeek()
											+ "','" + tc.getAvailability()
											+ "');");
			
			Utils.logSQL("Added temp constraint: " + tc.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteTempConstraint(User user, TempConstraint tc) {
		try {
			c.createStatement().executeUpdate("DELETE FROM temp_constraints "
											+ "WHERE(id='" + LoginHandler.retrieveUserID(user.getTrigram()) 
											+ "' AND name='" + tc.getConstraintName()
											+ "' AND day='" + tc.getDay()
											+ "' AND intervals='" + tc.getInterval()
											+ "' AND beginning='" + tc.getBeginningWeek()
											+ "' AND ending='" + tc.getEndingWeek()
											+ "');");
			
			Utils.logSQL("Deleted temp constraint: " + tc.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<TempConstraint> getTempConstraints(User user) {
		ArrayList<TempConstraint> tempConstraints = new ArrayList<>();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM temp_constraints WHERE (id=" + LoginHandler.retrieveUserID(user.getTrigram()) + ");");
			while (rs.next()) {
				TempConstraint tc = new TempConstraint(ConstraintType.TEMP_CONSTRAINT,
						Availability.fromString(rs.getString("constraints")),
						rs.getString("name"),
						rs.getInt("day"),
						rs.getInt("intervals"),
						rs.getInt("beginning"),
						rs.getInt("ending"));
				
				tempConstraints.add(tc);
				Utils.logSQL("Temp constraint retrieved: " + tc.toString());
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempConstraints;
	}
	
	public static ArrayList<TempConstraint> getTempConstraintsFromSchool(int week) {
		ArrayList<TempConstraint> constraints = new ArrayList<>();
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM temp_constraints WHERE (id='0');");
			
			while (rs.next()) {
				TempConstraint tc = new TempConstraint(ConstraintType.SCHOOL_CONSTRAINT,
						Availability.fromString(rs.getString("constraints")),
						rs.getString("name"),
						rs.getInt("day"),
						rs.getInt("intervals"),
						rs.getInt("beginning"),
						rs.getInt("ending"));
				constraints.add(tc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return constraints;
	}
}
