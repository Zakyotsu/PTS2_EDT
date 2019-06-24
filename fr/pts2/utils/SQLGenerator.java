package fr.pts2.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLGenerator {
	
	private static Connection c = SQLConnector.getConnection();

	public static void checkTables() {
		try {
			DatabaseMetaData dbm = c.getMetaData();
			Statement st = c.createStatement();
			
			if(!dbm.getTables(null, null, "users", null).next()) {
				st.executeUpdate("CREATE TABLE users(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16) NOT NULL, lastname VARCHAR(16) NOT NULL, password VARCHAR(64) NOT NULL, trigram VARCHAR(10) NOT NULL);");
			}
			
			if(!dbm.getTables(null, null, "constraints", null).next()) {
				st.executeUpdate("CREATE TABLE constraints(id INT NOT NULL, week INT NOT NULL, day INT NOT NULL, intervals INT NOT NULL, constraints VARCHAR(16) NOT NULL);");
				st.executeUpdate("ALTER TABLE constraints ADD CONSTRAINT FK_CONSTRAINT_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
			
			if(!dbm.getTables(null, null, "temp_constraints", null).next()) {
				st.executeUpdate("CREATE TABLE temp_constraints(id INT NOT NULL, name VARCHAR(100) NOT NULL, day INT NOT NULL, intervals INT NOT NULL, beginning INT NOT NULL, ending INT NOT NULL, constraints VARCHAR(16) NOT NULL);");
				st.executeUpdate("ALTER TABLE temp_constraints ADD CONSTRAINT FK_TEMPCONSTRAINTS_USERS_ID FOREIGN KEY(id) REFERENCES users(id);");
			}
			
			if(!dbm.getTables(null, null, "weekbuilded", null).next()) {
				st.executeUpdate("CREATE TABLE weekbuilded(week INT NOT NULL, builded BOOLEAN NOT NULL);");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}