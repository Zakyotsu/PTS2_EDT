package fr.ihm.pts2;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {

	public static void createAlert(AlertType type, String title, String text) {
		Alert a = new Alert(type);
		a.setTitle(title);
		a.setContentText(text);
		a.show();
		a = null;
	}
}
