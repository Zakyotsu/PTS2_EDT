package fr.pts2.usermanagement.edittimetable;

import fr.pts2.utils.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditTimeTable {

	private User u;

	public EditTimeTable(User u) {
		this.u = u;
	}

	public Stage getStage() {
		Stage stage = null;
		try {
			stage = new Stage();
			stage.setTitle("Gérer utilisateur");
			stage.setScene(new Scene(FXMLLoader.load(EditTimeTable.class.getResource("EditTimeTable.fxml"))));
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stage;
	}

	public User getUser() {
		return u;
	}
}
