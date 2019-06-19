package fr.pts2.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {

	public static void createAlert(AlertType type, String title, String text) {
		Alert a = new Alert(type);
		a.setTitle(title);
		a.setHeaderText(null);
		a.setContentText(text);
		a.show();
		a = null;
	}
	
	public static void logErr(String msg) {
		System.err.println(msg);
	}
	
	public static void log(String msg) {
		System.out.println(msg);
	}
}
