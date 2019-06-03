package fr.pts2.fixedconstraints.add;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddFixedConstraints {

	static Stage s;
	
	public AddFixedConstraints() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddFixedConstraint.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			scene.fillProperty().set(Color.GRAY);
			s.setScene(scene);
			s.setTitle("Contraintes fixes - Ajouter");
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Stage getStage() {
		return s;
	}
}
