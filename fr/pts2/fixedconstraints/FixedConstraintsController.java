package fr.pts2.fixedconstraints;

import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.enums.ConstraintAvailability;
import fr.pts2.enums.Days;
import fr.pts2.enums.Intervals;
import fr.pts2.fixedconstraints.add.AddFixedConstraints;
import fr.pts2.login.LoginController;
import fr.pts2.sql.SQLFixedConstraints;
import fr.pts2.utils.Constraint;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FixedConstraintsController implements Initializable {

	@FXML
	private TableView<TableViewConstraints> tableView;
	@FXML
	private TableColumn day, interval, constraint;

	public static ObservableList<TableViewConstraints> tvList = FXCollections.observableArrayList();

	@FXML
	public void addFixedConstraint() {
		new AddFixedConstraints();
	}

	@FXML
	public void deleteFixedConstraint() {
		TableViewConstraints tvc = tableView.getSelectionModel().getSelectedItem();
		if (tvc != null) {
			SQLFixedConstraints.deleteFixedConstraint(LoginController.getName()[1], Days.fromString(tvc.getDay()).ordinal()+1, Intervals.fromString(tvc.getInterval()).ordinal()+1);
			Utils.createAlert(AlertType.INFORMATION, "Information", "La contrainte fixe sélectionnée a bien été supprimée.");
			tvList.remove(tvc);
		}

	}

	private void setupTableView() {
		for (Constraint fc : SQLFixedConstraints.getFixedConstraints(LoginController.getName()[1])) {
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