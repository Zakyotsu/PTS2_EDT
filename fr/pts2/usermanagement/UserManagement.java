package fr.pts2.usermanagement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserManagement {
	
	private static Stage createStage() {
		Stage stage = null;
		try {
			stage = new Stage();
			stage.setTitle("Utilisateurs");
			stage.setScene(new Scene(FXMLLoader.load(UserManagement.class.getResource("UserManagement.fxml"))));
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stage;
	}

	public static Stage getStage() {
		return createStage();
	}
}
