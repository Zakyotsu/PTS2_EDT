package fr.pts2.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import fr.pts2.sql.LoginHandler;
import fr.pts2.timetable.TimeTable;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginStage implements Initializable {

	public static File saveCredentialsFile  = new File(System.getProperty("user.home") + File.separatorChar + "GPUConstraints" + File.separatorChar + "credentials.enc");
	private static Stage stage;
	private static User user;

	@FXML
	private TextField trigram;

	@FXML
	private PasswordField password;

	@FXML
	private CheckBox saveCredentialsBox;

	public static void showStage() throws IOException {
		Parent root = FXMLLoader.load(LoginStage.class.getResource("Login.fxml"));
		Scene scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("GPUConstraints");
		stage.setResizable(false);
		stage.show();
	}

	// When click on "Login" button
	@FXML
	public void onLogin() {
		if (trigram.getText().isEmpty() || trigram.getText().replaceAll("[^a-zA-Z]", "").isEmpty()) {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Erreur dans le trigramme, veuillez le vérifier.");
			return;
		}
		
		if(LoginHandler.isPasswordGood(trigram.getText(), password.getText())) {
			user = LoginHandler.getUserFromTrigram(trigram.getText().toUpperCase());
			TimeTable.showStage();
			if(saveCredentialsBox.isSelected()) {
				saveCredentials(trigram.getText(), password.getText());
				Utils.log("User is now logged in!");
				stage.close();
			}
		} else Utils.createAlert(AlertType.ERROR, "Erreur", "Votre mot de passe est incorrect.");
	}

	// Save the user credentials
	private void saveCredentials(String trigram, String password) {
		// Delete the file if it already exists
		if (saveCredentialsFile.exists()) saveCredentialsFile.delete();

		// Regenerate the file
		try {
			File folder = new File(System.getProperty("user.home") + "\\GPUConstraints");
			if (!folder.exists())
				folder.mkdirs();
			FileWriter f = new FileWriter(saveCredentialsFile.getPath());
			BufferedWriter bf = new BufferedWriter(f);
			bf.write(trigram);
			bf.newLine();
			bf.write(new String(Base64.getEncoder().encode(password.getBytes())));
			bf.newLine();
			bf.close();
			f.close();
			Utils.log("Credentials file regenerated.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static User getUser() {
		return user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(saveCredentialsFile.exists()) {
			saveCredentialsBox.setSelected(true);
			try {
				FileReader fr = new FileReader(saveCredentialsFile.getPath());
				BufferedReader br = new BufferedReader(fr);
				
				trigram.setText(br.readLine());
				password.setText(new String(Base64.getDecoder().decode(br.readLine().getBytes())));
				
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}