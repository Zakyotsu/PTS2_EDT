package fr.pts2;

import java.awt.Desktop;
import java.io.IOException;

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

	public static void main(String[] args) throws IOException {
		new SQLConnector("52.29.239.198", "3306", "sql7296880", "sql7296880", "5679byG67R");
		SQLGenerator.checkTables();
		
		CSVStage csv = new CSVStage();
		if (args.length > 0 && args[0].equalsIgnoreCase("-e")) {
			if(!Constants.DEFAULT_FOLDER_EXPORT.exists()) Constants.DEFAULT_FOLDER_EXPORT.mkdirs();
			csv.generateForAllUsers(Constants.DEFAULT_FOLDER_EXPORT.getAbsolutePath());
			Desktop.getDesktop().open(Constants.DEFAULT_FOLDER_EXPORT);
			return;
		}
		
		launch(args);
	}
}
