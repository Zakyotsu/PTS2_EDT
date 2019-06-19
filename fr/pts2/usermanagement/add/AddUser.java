package fr.pts2.usermanagement.add;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddUser {

	static Stage s;

	public AddUser() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			scene.fillProperty().set(Color.GRAY);
			s.setScene(scene);
			s.setTitle("Utilisateurs - Ajouter");
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
