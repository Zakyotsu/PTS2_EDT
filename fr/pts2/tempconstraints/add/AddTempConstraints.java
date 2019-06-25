package fr.pts2.tempconstraints.add;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.enums.Availability;
import fr.pts2.enums.ConstraintType;
import fr.pts2.enums.Days;
import fr.pts2.enums.Intervals;
import fr.pts2.login.LoginStage;
import fr.pts2.sql.TempConstraintHandler;
import fr.pts2.tempconstraints.TableViewConstraints;
import fr.pts2.tempconstraints.TempConstraints;
import fr.pts2.utils.TempConstraint;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTempConstraints implements Initializable {

	private static Stage s;
	
	@FXML
	private TextField nameField;
	@FXML
	private Spinner<Integer> startWeek, endWeek;
	@FXML
	private ComboBox<String> dayBox, intervalBox, availabilityBox;

	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(AddTempConstraints.class.getResource("AddTempConstraints.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			s.setScene(scene);
			s.setTitle("Ajouter");
			s.setResizable(false);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onConfirm() {
		if (nameField.getText().isEmpty() || startWeek.getValue() > 52 || endWeek.getValue() > 52 
				|| availabilityBox.getSelectionModel().getSelectedItem() == null
				|| dayBox.getSelectionModel().getSelectedItem() == null
				|| intervalBox.getSelectionModel().getSelectedItem() == null) {
			Utils.createAlert(AlertType.ERROR, "Erreur", "Veuillez vérifiez tous les champs.");
			return;
		}

		Availability availability = null;
		switch (availabilityBox.getSelectionModel().getSelectedIndex()) {
		case 0:
			availability = Availability.AVOID;
			break;
		case 1:
			availability = Availability.UNAVAILABLE;
			break;
		}

		Days day = null;
		switch (dayBox.getSelectionModel().getSelectedIndex()) {
		case 0:
			day = Days.MONDAY;
			break;
		case 1:
			day = Days.TUESDAY;
			break;
		case 2:
			day = Days.WEDNESDAY;
			break;
		case 3:
			day = Days.THURSDAY;
			break;
		case 4:
			day = Days.FRIDAY;
			break;
		}

		Intervals intervals = null;
		switch (intervalBox.getSelectionModel().getSelectedIndex()) {
		case 0:
			intervals = Intervals.FIRST;
			break;
		case 1:
			intervals = Intervals.SECOND;
			break;
		case 2:
			intervals = Intervals.THIRD;
			break;
		case 3:
			intervals = Intervals.FOURTH;
			break;
		}

		TempConstraint tc = new TempConstraint(ConstraintType.TEMP_CONSTRAINT, availability, nameField.getText(), day.ordinal() + 1,
				intervals.ordinal() + 1, startWeek.getValue(), endWeek.getValue());
		
		TempConstraintHandler.addTempConstraint(LoginStage.getUser(), tc);
		TempConstraints.tvList.add(new TableViewConstraints(tc));

		Utils.createAlert(AlertType.INFORMATION, "Information", "La contrainte temporaire a bien été ajoutée.");
		s.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startWeek.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 52, 1));
		endWeek.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 52, 1));

		dayBox.setItems(FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"));
		intervalBox.setItems(FXCollections.observableArrayList("8h00-10h00", "10h15-12h15", "14h00-16h00", "16h15-18h15"));
		availabilityBox.setItems(FXCollections.observableArrayList("A éviter", "Indisponible"));
	}
}