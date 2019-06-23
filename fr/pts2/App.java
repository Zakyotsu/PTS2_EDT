package fr.pts2;

import fr.pts2.login.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	// Main stage launch
	@Override
	public void start(Stage stage) throws Exception {
		new LoginStage().showStage();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
