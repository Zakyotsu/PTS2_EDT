package fr.ihm.pts2.timetable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimeTable {

	private static Stage stage;

	private static Stage createStage() {
		try {
			stage = new Stage();
			stage.setTitle("GPUv12");
			stage.setScene(new Scene(FXMLLoader.load(TimeTable.class.getResource("TimeTable.fxml"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stage;
	}

	public static Stage getStage() {
		return stage == null ? createStage() : stage;
	}
}
