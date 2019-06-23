package fr.pts2.usermanagement.add;

import fr.pts2.sql.LoginHandler;
import fr.pts2.sql.UsersHandler;
import fr.pts2.usermanagement.UserManagement;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUser {

	private static Stage s;
	@FXML
	private TextField name, lastname, trigram;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(AddUser.class.getResource("AddUser.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			s.setScene(scene);
			s.setTitle("Ajouter utilisateur");
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void valid() {
		if (!name.getText().isEmpty() && !lastname.getText().isEmpty() && !trigram.getText().isEmpty()) {
			User u = new User(name.getText().toUpperCase(), lastname.getText().toUpperCase(), trigram.getText().toUpperCase(), LoginHandler.retrieveUserID(trigram.getText().toUpperCase()));
			UserManagement.users.add(u);
			UsersHandler.createUser(u);
			s.close();
		} else Utils.createAlert(AlertType.ERROR, "Erreur", "Vérifiez vos champs !");
	}
}