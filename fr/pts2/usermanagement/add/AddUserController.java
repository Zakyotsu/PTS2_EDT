package fr.pts2.usermanagement.add;

import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.sql.SQLUsers;
import fr.pts2.usermanagement.UserManagementController;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AddUserController implements Initializable {

	@FXML
	private TextField name, lastname, trigram;

	@FXML
	public void valid() {
		if (!name.getText().isEmpty() && !lastname.getText().isEmpty() && !trigram.getText().isEmpty()) {
			User u = new User(name.getText().toUpperCase(), lastname.getText().toUpperCase(), trigram.getText().toUpperCase());
			UserManagementController.users.add(u);
			SQLUsers.createUser(u);
			AddUser.s.close();
		} else Utils.createAlert(AlertType.ERROR, "Erreur", "Vérifiez vos champs !");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
}