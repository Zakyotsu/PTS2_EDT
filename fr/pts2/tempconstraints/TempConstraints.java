package fr.pts2.tempconstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.enums.ConstraintType;
import fr.pts2.login.LoginStage;
import fr.pts2.sql.TempConstraintHandler;
import fr.pts2.utils.TempConstraint;
import fr.pts2.utils.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TempConstraints implements Initializable {
	
	private ObservableList<TableViewConstraints> tvList;
	
	@FXML
	private TableColumn<TableViewConstraints, String> name, day, interval, availability, start, end;
	@FXML
	private TableView<TableViewConstraints> tableView;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(TempConstraints.class.getResource("TempConstraints.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			scene.fillProperty().set(Color.GRAY);
			stage.setScene(scene);
			stage.setTitle("Contraintes temporaires");
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
	public void add() {
		
	}
	
	@FXML
	public void manage() {
		
	}
	
	@FXML
	public void delete() {
		TableViewConstraints tvc = tableView.getSelectionModel().getSelectedItem();
		if (tvc != null) {
			TempConstraint tc = new TempConstraint(tvc.getConstraintType(), tvc.getAvailability(), tvc.getConstraintName(), tvc.getDay(), tvc.getInterval(), tvc.getBeginningWeek(), tvc.getEndingWeek());
			TempConstraintHandler.deleteTempConstraint(LoginStage.getUser(), tc);
			Utils.createAlert(AlertType.INFORMATION, "Information", "La contrainte temporaire sélectionnée a bien été supprimée.");
			tvList.remove(tvc);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(tvList);
		
		
		
	}
}
