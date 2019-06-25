package fr.pts2;

import fr.pts2.csv.CSVStage;
import fr.pts2.login.LoginStage;
import fr.pts2.sql.SQLConnector;
import fr.pts2.sql.SQLGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	// Main stage launch
	@Override
	public void start(Stage stage) throws Exception {
		LoginStage.showStage();
	}

	public static void main(String[] args) {
		new SQLConnector("localhost", "3306", "pts2", "root", "");
		SQLGenerator.checkTables();
		
		CSVStage csv = new CSVStage();
		if (args.length > 0 && args[0].equalsIgnoreCase("-e")) {
			if (args.length > 1 && !args[1].isEmpty()) {
				csv.generateForAllUsers(args[1]);
			} else
				csv.generateForAllUsers(Constants.DEFAULT_FOLDER_EXPORT.getAbsolutePath());
			return;
		}
		
		launch(args);
	}
}
