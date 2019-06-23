package fr.pts2.advanced;

import fr.pts2.changepassword.PasswordChanger;
import fr.pts2.fixedconstraints.FixedConstraints;
import fr.pts2.usermanagement.UserManagement;
import fr.pts2.weekbuilded.WeekBuilded;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdvancedMenu {

	private static Stage s;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(AdvancedMenu.class.getResource("AdvancedMenu.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			scene.fillProperty().set(Color.GRAY);
			s.setScene(scene);
			s.setTitle("Avancé...");
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openUserManagement() {
		UserManagement.showStage();
		s.close();
	}
	
	@FXML
	public void openChangePassword() {
		PasswordChanger.showStage();
		s.close();
	}
	
	@FXML
	public void openFixedConstraints() {
		FixedConstraints.showStage();
		s.close();
	}
	
	@FXML
	public void openWeeksBuilded() {
		WeekBuilded.showStage();
		s.close();
	}
	
	@FXML
	public void openExportToCSV() {
		
	}
}
