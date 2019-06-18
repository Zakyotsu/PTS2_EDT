package fr.pts2.timetable;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.pts2.fixedconstraints.FixedConstraints;
import fr.pts2.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class TimeTableController implements Initializable {

	private ToggleGroup group = new ToggleGroup();
	private LocalDate now = LocalDate.now();

	@FXML
	private RadioButton available, avoid, unavailable;
	@FXML
	private Label name, week;
	@FXML
	private DatePicker datePicker;
	@FXML
	private GridPane buttonPane;
	
	public static fr.pts2.utils.TimeTable table;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Little dots inside the button
		available.setStyle("-fx-mark-color: green;");
		avoid.setStyle("-fx-mark-color: orange;");
		unavailable.setStyle("-fx-mark-color: red;");

		group.getToggles().addAll(available, avoid, unavailable);
		group.getToggles().get(0).setSelected(true);

		table = new fr.pts2.utils.TimeTable(buttonPane, name, week, group, available, avoid, unavailable, now);
		
		datePicker.setValue(now);
		datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			now = newValue;
			table.setDateAndRefresh(now);
		});
		table.setDateAndRefresh(now);
	}
	
	@FXML
	public void openSettings() {
		//TO DO OPEN SETTINGS
	}
	
	@FXML
	public void openUserManager() {
		
	}

	@FXML
	public void onConfirm() {
		table.saveAllConstraints(now);
	}

	@FXML
	public void openFixedConstraints() {
		new FixedConstraints();
	}

	//Reculer 1 semaine
	@FXML
	public void weekBefore() {
		if (now.minusWeeks(1).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear()) < LocalDate.now()
				.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear())) {
			return;
		}
		Utils.log("Minus 1 week.");
		now = now.minusWeeks(1);
		table.setDateAndRefresh(now);
	}

	//Avancer 1 semaine
	@FXML
	public void weekAfter() {
		Utils.log("Plus 1 week.");
		now = now.plusWeeks(1);
		table.setDateAndRefresh(now);
	}
}