package fr.pts2.usermanagement.fixedconstraints.add;

import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.enums.Availability;
import fr.pts2.enums.Days;
import fr.pts2.enums.Intervals;
import fr.pts2.fixedconstraints.TableViewConstraints;
import fr.pts2.sql.FixedConstraintHandler;
import fr.pts2.usermanagement.UserManagement;
import fr.pts2.usermanagement.fixedconstraints.FixedConstraints;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddFixedConstraints implements Initializable {
	
	private ObservableList<String> dayList, intervalList, constraintList;
	private static Stage s;
	
	@FXML
	private ComboBox<String> dayBox, intervalBox, constraintBox;
	
	public static void showStage() {
		try {
			Parent root = FXMLLoader.load(AddFixedConstraints.class.getResource("AddFixedConstraints.fxml"));
			Scene scene = new Scene(root);
			s = new Stage();
			scene.fillProperty().set(Color.GRAY);
			s.setScene(scene);
			s.setTitle("Contraintes fixes - Ajouter");
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void validFixedConstraint() {
		Availability constraint = Availability.AVAILABLE;
		switch(constraintBox.getSelectionModel().getSelectedItem().toString()) {
		case "A éviter":
			constraint = Availability.AVOID;
			break;
		case "Indisponible":
			constraint = Availability.UNAVAILABLE;
			break;
		}
		
		int day = dayBox.getSelectionModel().getSelectedIndex() + 1;
		int interval = intervalBox.getSelectionModel().getSelectedIndex() + 1;		
		
		FixedConstraintHandler.addFixedConstraint(UserManagement.user, day, interval, constraint);
		FixedConstraints.tvList.add(new TableViewConstraints(dayBox.getSelectionModel().getSelectedItem().toString(), intervalBox.getSelectionModel().getSelectedItem().toString(), constraint));
		Utils.createAlert(AlertType.INFORMATION, "Information", "La contrainte fixe a bien été enregistrée.");
		s.close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		dayList = FXCollections.observableArrayList();
		intervalList = FXCollections.observableArrayList();
		constraintList = FXCollections.observableArrayList();
		for(Days d : Days.values()) dayList.add(d.getString());
		for(Intervals i : Intervals.values()) intervalList.add(i.getString());
		for(Availability c : Availability.values()) constraintList.add(c.getString());
		
		dayBox.setItems(dayList);
		intervalBox.setItems(intervalList);
		constraintBox.setItems(constraintList);
		
		dayBox.getSelectionModel().select(0);
		intervalBox.getSelectionModel().select(0);
		constraintBox.getSelectionModel().select(0);
	}
}
