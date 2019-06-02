package fr.pts2;

import fr.pts2.login.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage stage;
	
	public static void main(String[] args) {
		Application.launch(App.class, args);
	}
	
	@Override
	public void start(Stage s) throws Exception {
		new Login();
	}
	
	public static void setStage(Stage stage) {
		if(App.stage != null) App.stage.close();
		App.stage = stage;
		App.stage.show();
	}
}
