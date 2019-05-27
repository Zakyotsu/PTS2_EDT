package fr.ihm.pts2.login;

import java.net.URL;
import java.util.ResourceBundle;

import fr.ihm.pts2.App;
import fr.ihm.pts2.Utils;
import fr.ihm.pts2.sql.SQLAPI;
import fr.ihm.pts2.sql.SQLConnector;
import fr.ihm.pts2.timetable.TimeTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	SQLConnector sql;
	
	private static String[] name;
	
	@FXML private TextField surname;
	
	@FXML private CheckBox saveCredentialsBox;
	
	@FXML
	public void onLogin() {
		if(surname.getText().isEmpty()) {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Problème dans votre nom, écrivez le sous la forme \"NOM\"");
			return;
		} else {
			surname.setText(surname.getText().replaceAll("[^a-zA-Z]", ""));
			if(surname.getText().isEmpty()) {
				Utils.createAlert(AlertType.ERROR, "Erreur", "Problème dans votre nom, écrivez le sous la forme \"NOM\"");
				return;
			}
		}
		
		if(SQLAPI.userExists(SQLConnector.getConnection(), surname.getText())) {
			if(saveCredentialsBox.isSelected()) {
				//Save username somewhere in AppData
				sql.disconnect();
			}
			name = SQLAPI.getUserStrings(SQLConnector.getConnection(), surname.getText());
			App.setStage(TimeTable.getStage());
		} else {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Nom d'utilisateur inconnu dans la base de données.");
			return;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		sql = new SQLConnector("localhost", "3306", "pts2", "root", "");
		SQLAPI.checkTables(SQLConnector.getConnection());
	}

	public static String[] getName() {
		return name;
	}
}
