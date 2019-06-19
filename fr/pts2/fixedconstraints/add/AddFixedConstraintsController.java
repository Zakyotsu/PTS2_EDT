package fr.pts2.fixedconstraints.add;

import java.net.URL;
import java.util.ResourceBundle;

import fr.pts2.enums.Availability;
import fr.pts2.enums.Days;
import fr.pts2.enums.Intervals;
import fr.pts2.fixedconstraints.FixedConstraints;
import fr.pts2.fixedconstraints.FixedConstraintsController;
import fr.pts2.fixedconstraints.TableViewConstraints;
import fr.pts2.sql.SQLFixedConstraints;
import fr.pts2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;

public class AddFixedConstraintsController implements Initializable {

	@FXML
	private ComboBox dayBox, intervalBox, constraintBox;
	private ObservableList<String> dayList, intervalList, constraintList;
	
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
		
		SQLFixedConstraints.addFixedConstraint(FixedConstraints.getUser(), day, interval, constraint);
		FixedConstraintsController.tvList.add(new TableViewConstraints(dayBox.getSelectionModel().getSelectedItem().toString(), intervalBox.getSelectionModel().getSelectedItem().toString(), constraint));
		Utils.createAlert(AlertType.INFORMATION, "Information", "La contrainte fixe a bien été enregistrée.");
		AddFixedConstraints.getStage().close();
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
