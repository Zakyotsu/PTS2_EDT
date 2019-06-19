package fr.pts2.fixedconstraints;

import java.io.IOException;

import fr.pts2.timetable.TimeTableController;
import fr.pts2.usermanagement.edittimetable.EditTimeTableController;
import fr.pts2.utils.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FixedConstraints {

	private static User u;
	
	public FixedConstraints(User u) {
		FixedConstraints.u = u;
	}
	
	public void showStage() {
		try {
			Parent root = FXMLLoader.load(FixedConstraints.class.getResource("FixedConstraints.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			scene.fillProperty().set(Color.GRAY);
			stage.setScene(scene);
			stage.setTitle("Contraintes fixes");
			stage.setResizable(false);
			stage.setOnCloseRequest(e -> {
				try {
					TimeTableController.table.refreshConstraints();
					EditTimeTableController.table.refreshConstraints();
				} catch(Exception exception) {}
			});
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static User getUser() {
		return u;
	}
}
