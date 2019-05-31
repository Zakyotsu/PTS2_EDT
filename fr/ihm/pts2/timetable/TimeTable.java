package fr.ihm.pts2.timetable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimeTable {

	private static Stage stage;

	private static Stage createStage() {
		try {
			stage = new Stage();
			stage.setTitle("PTS2 - App");
			stage.setScene(new Scene(FXMLLoader.load(TimeTable.class.getResource("TimeTable.fxml"))));
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stage;
	}

	public static Stage getStage() {
		return stage == null ? createStage() : stage;
	}
}
