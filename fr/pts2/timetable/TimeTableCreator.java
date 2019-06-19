package fr.pts2.timetable;

import fr.pts2.utils.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimeTableCreator {

	private static Stage stage;
	private static User user;
	
	public TimeTableCreator(User user) {
		TimeTableCreator.user = user;
	}

	private static Stage createStage() {
		try {
			stage = new Stage();
			stage.setTitle("PTS2 - App");
			stage.setScene(new Scene(FXMLLoader.load(TimeTableCreator.class.getResource("TimeTable.fxml"))));
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stage;
	}

	public static Stage getStage() {
		return stage == null ? createStage() : stage;
	}
	
	public static User getUser() {
		return user;
	}
}
