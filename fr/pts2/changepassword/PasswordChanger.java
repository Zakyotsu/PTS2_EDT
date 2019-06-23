package fr.pts2.changepassword;

import fr.pts2.login.LoginStage;
import fr.pts2.sql.LoginHandler;
import fr.pts2.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PasswordChanger {

	private static Stage s;
	
	@FXML
	private PasswordField oldPassword, newPassword, passwordConfirmation;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(PasswordChanger.class.getResource("PasswordChanger.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			scene.fillProperty().set(Color.GRAY);
			s.setScene(scene);
			s.setTitle("Changer de mot de passe");
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void confirm() {
		if(oldPassword.getText().isEmpty() || newPassword.getText().isEmpty() || passwordConfirmation.getText().isEmpty()) {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Veuillez renseigner tout les champs.");
			return;
		}
		
		if(LoginHandler.isPasswordGood(LoginStage.getUser().getTrigram(), oldPassword.getText())) {
			if(newPassword.getText().equals(passwordConfirmation.getText())) {
				LoginHandler.changePassword(LoginStage.getUser(), newPassword.getText());
				if (LoginStage.saveCredentialsFile.exists()) LoginStage.saveCredentialsFile.delete();
				Utils.createAlert(AlertType.INFORMATION, "Information", "Votre mot de passe a bien été modifié.");
				s.close();
			} else Utils.createAlert(AlertType.ERROR, "Erreur", "Vos deux mots de passe ne correspondent pas.");
		} else Utils.createAlert(AlertType.ERROR, "Erreur", "Votre ancien mot de passe est eronné.");
	}
}