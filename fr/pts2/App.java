package fr.pts2;

import fr.pts2.login.LoginStage;
import fr.pts2.utils.CSVGenerator;
import fr.pts2.utils.SQLConnector;
import fr.pts2.utils.SQLGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	// Main stage launch
	@Override
	public void start(Stage stage) throws Exception {
		new SQLConnector("localhost", "3306", "pts2", "root", "");
		SQLGenerator.checkTables();
		LoginStage.showStage();
		CSVGenerator csv = new CSVGenerator(System.getProperty("user.home") + "//coucou.csv//");
		csv.generateFile();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
