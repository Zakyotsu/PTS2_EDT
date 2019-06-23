package fr.pts2.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pts2.utils.SQLConnector;

public class WeekBuildedHandler {

	private static Connection c = SQLConnector.getConnection();

	public static void setWeekBuilded(int week, boolean isBuilded) {
		//Convert the boolean into 0 and 1
		int value = 0;
		switch(Boolean.toString(isBuilded).toLowerCase()) {
			case "true":
				value = 1;
				break;
		}
		
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM weekbuilded WHERE (week='" + week + "');");
			if(rs.next()) {
				c.createStatement().executeUpdate("UPDATE weekbuilded SET builded='" + value + "' WHERE" + "(week=" + week + ");");
			} else {
				c.createStatement().executeUpdate("INSERT INTO weekbuilded(week, builded) VALUES('" + week + "', '" + value + "');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isWeekBuilded(int week) {
		try {
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM weekbuilded WHERE (week='" + week + "');");
			if (rs.next())
				return rs.getBoolean("builded");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}