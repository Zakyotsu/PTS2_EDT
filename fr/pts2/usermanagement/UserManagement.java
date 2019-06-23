package fr.pts2.usermanagement;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import fr.pts2.login.LoginStage;
import fr.pts2.sql.UsersHandler;
import fr.pts2.usermanagement.add.AddUser;
import fr.pts2.usermanagement.timetable.TimeTable;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserManagement implements Initializable {

	public static ObservableList<User> users;
	public static TimeTable ett;
	private static Stage s;
	public static User user;
	
	@FXML
	private TableColumn<String, String> name, lastname, trigram;
	@FXML
	private TableView<User> tableView;

	public static void showStage() {
		try {
			s = new Stage();
			s.setTitle("Utilisateurs - Gérer");
			s.setScene(new Scene(FXMLLoader.load(UserManagement.class.getResource("UserManagement.fxml"))));
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void add() {
		AddUser.showStage();
	}
	
	@FXML
	public void manage() {
		user = tableView.getSelectionModel().getSelectedItem();
		if(user != null) {
			if(!LoginStage.getUser().equals(user)) {
				TimeTable.showStage();
				s.close();
			} else Utils.createAlert(AlertType.ERROR, "Erreur", "Vous ne pouvez pas vous gérer vous même !");
		}
	}
	
	@FXML
	public void delete() {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Etes vous sûr ?");
		a.setContentText("Voulez vous vraiment supprimer cet utilisateur ?");
		a.setHeaderText(null);
		Optional<ButtonType> result = a.showAndWait();
		if (result.get() == ButtonType.OK) {
			User user = tableView.getSelectionModel().getSelectedItem();
			if (user != null) {
				UsersHandler.deleteUser(user.getName(), user.getLastname(), user.getTrigram());
				users.remove(user);
				Utils.createAlert(AlertType.INFORMATION, "Information", "L'utilisateur sélectionné à bien été supprimé.");
			}
		} else return;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		users = FXCollections.observableArrayList();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
		trigram.setCellValueFactory(new PropertyValueFactory<>("trigram"));
		tableView.setItems(users);
		for(User u : UsersHandler.getUsers()) users.add(u);
	}
}