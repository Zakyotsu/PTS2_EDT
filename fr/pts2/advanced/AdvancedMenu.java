package fr.pts2.advanced;

import java.io.File;

import fr.pts2.changepassword.PasswordChanger;
import fr.pts2.csv.CSVGenerator;
import fr.pts2.csv.CSVStage;
import fr.pts2.login.LoginStage;
import fr.pts2.tempconstraints.TempConstraints;
import fr.pts2.usermanagement.UserManagement;
import fr.pts2.utils.Utils;
import fr.pts2.weekbuilded.WeekBuilded;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdvancedMenu {

	private static Stage s;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(AdvancedMenu.class.getResource("AdvancedMenu.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			s.setScene(scene);
			s.setTitle("Avanc�...");
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
	public void openTempConstraints() {
		TempConstraints.showStage();
		s.close();
	}
	
	@FXML
	public void openWeeksBuilded() {
		WeekBuilded.showStage();
		s.close();
	}
	
	@FXML
	public void openExportToCSV() {
		CSVStage csv = new CSVStage();
		File file = csv.getDirectoryChooserFile(s);
		if(file == null) return;
		CSVGenerator csvGen = new CSVGenerator(file.getAbsolutePath());
		csvGen.generateFile(LoginStage.getUser());
		Utils.createAlert(AlertType.INFORMATION, "Information", "Le fichier csv a bien �t� cr��.");
	}
}
