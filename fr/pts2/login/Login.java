package fr.pts2.login;

import fr.pts2.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Login {

	public Login() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		scene.fillProperty().set(Color.GRAY);
		stage.setScene(scene);
		stage.setTitle("PTS2 - App");
		stage.setResizable(false);
		App.setCurrentStage(stage);
	}
}
