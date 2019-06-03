package fr.pts2.fixedconstraints;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FixedConstraints {

	public FixedConstraints() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("FixedConstraints.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			scene.fillProperty().set(Color.GRAY);
			stage.setScene(scene);
			stage.setTitle("Contraintes fixes");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
