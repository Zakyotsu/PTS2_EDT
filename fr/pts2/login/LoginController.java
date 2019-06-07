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

import fr.pts2.App;
import fr.pts2.Utils;
import fr.pts2.sql.SQLAPI;
import fr.pts2.sql.SQLConnector;
import fr.pts2.timetable.TimeTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	SQLConnector sql;
	
	private static String[] name;
	private File file = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GPUConstraints\\credentials.txt");
	
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private CheckBox saveCredentialsBox;
	
	@FXML
	public void onLogin() {
		if(username.getText().isEmpty()) {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Problème dans votre nom, écrivez le sous la forme \"NOM\"");
			return;
		} else {
			username.setText(username.getText().replaceAll("[^a-zA-Z]", "").toUpperCase());
			if(username.getText().isEmpty()) {
				Utils.createAlert(AlertType.ERROR, "Erreur", "Problème dans votre nom, écrivez le sous la forme \"NOM\"");
				return;
			}
		}
		
		if(SQLAPI.isPasswordGood(username.getText(), password.getText())) {
			if(saveCredentialsBox.isSelected()) {
				try {
					File folder = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GPUConstraints");
					if(!folder.exists()) folder.mkdirs();
					FileWriter f = new FileWriter(file.getPath());
					BufferedWriter bf = new BufferedWriter(f);
					bf.write(username.getText());
					bf.newLine();
					bf.write(new String(Base64.getEncoder().encode(password.getText().getBytes())));
					bf.newLine();
					bf.close();
					f.close();
					Utils.log("File created!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if(file.exists()) file.delete();
			}
			name = SQLAPI.getUserStrings(username.getText());
			App.setStage(TimeTable.getStage());
			Utils.log("User is now logged in!");
		} else {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Nom d'utilisateur/mot de passe incorrect.");
			return;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		sql = new SQLConnector("localhost", "3306", "pts2", "root", "");
		SQLAPI.checkTables();
		if(file.exists()) {
			saveCredentialsBox.setSelected(true);
			
			try {
				FileReader fr = new FileReader(file.getPath());
				BufferedReader br = new BufferedReader(fr);
				
				username.setText(br.readLine());
				password.setText(new String(Base64.getDecoder().decode(br.readLine().getBytes())));
				
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String[] getName() {
		return name;
	}
}
