package fr.pts2.fixedconstraints.add;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddFixedConstraints {

	public AddFixedConstraints() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AddFixedConstraints.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		scene.fillProperty().set(Color.GRAY);
		stage.setScene(scene);
		stage.setTitle("GPUv12");
		stage.setResizable(false);
		stage.show();
	}
	
}
