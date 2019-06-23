package fr.pts2.weekbuilded;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.sql.WeekBuildedHandler;
import fr.pts2.timetable.TimeTable;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WeekBuilded implements Initializable {

	@FXML
	private Spinner<Integer> spinner;
	@FXML
	private CheckBox buildedShow;
	@FXML
	private ComboBox<String> comboBox;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(WeekBuilded.class.getResource("WeekBuilded.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			scene.fillProperty().set(Color.GRAY);
			stage.setScene(scene);
			stage.setTitle("Semaines construites");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onConfirm() {
		switch(comboBox.getSelectionModel().getSelectedIndex()) {
			case 0:
				WeekBuildedHandler.setWeekBuilded(spinner.getValue(), true);
				buildedShow.setSelected(WeekBuildedHandler.isWeekBuilded(spinner.getValue()));
				TimeTable.table.refreshTopInfo();
				break;
				
			case 1:
				WeekBuildedHandler.setWeekBuilded(spinner.getValue(), false);
				buildedShow.setSelected(WeekBuildedHandler.isWeekBuilded(spinner.getValue()));
				TimeTable.table.refreshTopInfo();
				break;
		}
		Utils.createAlert(AlertType.INFORMATION, "Information", "La constructibilité a bien été mise à jour.");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 52, 1));
		spinner.setOnKeyReleased(e -> {
			buildedShow.setSelected(WeekBuildedHandler.isWeekBuilded(spinner.getValue()));
		});
		spinner.setOnKeyPressed(e -> {
			buildedShow.setSelected(WeekBuildedHandler.isWeekBuilded(spinner.getValue()));
		});
		spinner.setOnMouseClicked(e -> {
			buildedShow.setSelected(WeekBuildedHandler.isWeekBuilded(spinner.getValue()));
		});
		
		comboBox.setItems(FXCollections.observableArrayList("Construite", "Non construite"));
		comboBox.getSelectionModel().clearAndSelect(0);
	}
}