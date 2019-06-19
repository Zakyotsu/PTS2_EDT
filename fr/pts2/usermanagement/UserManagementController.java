package fr.pts2.usermanagement;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import fr.pts2.sql.SQLUsers;
import fr.pts2.usermanagement.add.AddUser;
import fr.pts2.usermanagement.edittimetable.EditTimeTable;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserManagementController implements Initializable {

	@FXML
	private TableColumn<String, String> name, lastname, trigram;
	@FXML
	private TableView<User> tableView;
	public static ObservableList<User> users;
	public static EditTimeTable ett;
	
	
	@FXML
	public void add() {
		new AddUser();
	}
	
	@FXML
	public void manage() {
		User user = tableView.getSelectionModel().getSelectedItem();
		if(user != null) {
			ett = new EditTimeTable(user);
			ett.getStage().show();
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
				SQLUsers.deleteUser(user.getName(), user.getLastname(), user.getTrigram());
				users.remove(user);
				Utils.createAlert(AlertType.INFORMATION, "Information", "L'utilisateur sélectionné à bien été supprimé.");
			}
		} else return;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		users = FXCollections.observableArrayList();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
		trigram.setCellValueFactory(new PropertyValueFactory<>("trigram"));
		tableView.setItems(users);
		for(User u : SQLUsers.getUsers()) users.add(u);
	}
}