package fr.pts2.fixedconstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.enums.Days;
import fr.pts2.enums.Intervals;
import fr.pts2.fixedconstraints.add.AddFixedConstraints;
import fr.pts2.login.LoginStage;
import fr.pts2.sql.FixedConstraintHandler;
import fr.pts2.utils.Constraint;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FixedConstraints implements Initializable {

	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(FixedConstraints.class.getResource("FixedConstraints.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			scene.fillProperty().set(Color.GRAY);
			stage.setScene(scene);
			stage.setTitle("Contraintes fixes");
			stage.setResizable(false);
			stage.setOnCloseRequest(e -> {
				try {
					fr.pts2.timetable.TimeTable.table.refreshConstraints();
					fr.pts2.usermanagement.timetable.TimeTable.table.refreshConstraints();
				} catch(Exception exception) {}
			});
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private TableView<TableViewConstraints> tableView;
	@FXML
	private TableColumn<TableViewConstraints, String> day, interval, constraint;

	public static ObservableList<TableViewConstraints> tvList = FXCollections.observableArrayList();

	@FXML
	public void addFixedConstraint() {
		AddFixedConstraints.showStage();
	}

	@FXML
	public void deleteFixedConstraint() {
		TableViewConstraints tvc = tableView.getSelectionModel().getSelectedItem();
		if (tvc != null) {
			FixedConstraintHandler.deleteFixedConstraint(LoginStage.getUser(), Days.fromString(tvc.getDay()).ordinal()+1, Intervals.fromString(tvc.getInterval()).ordinal()+1);
			Utils.createAlert(AlertType.INFORMATION, "Information", "La contrainte fixe sélectionnée a bien été supprimée.");
			tvList.remove(tvc);
		}
	}

	private void setupTableView() {
		for (Constraint fc : FixedConstraintHandler.getFixedConstraints(LoginStage.getUser())) {
			tvList.add(new TableViewConstraints(Days.fromInt(fc.getDay()).getString(), Intervals.fromInt(fc.getInterval()).getString(), fc.getAvailability()));
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tvList.clear();
		tableView.setItems(tvList);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		day.setCellValueFactory(new PropertyValueFactory<>("day"));
		interval.setCellValueFactory(new PropertyValueFactory<>("interval"));
		constraint.setCellValueFactory(new PropertyValueFactory<>("constraint"));
		setupTableView();
	}
	
}
