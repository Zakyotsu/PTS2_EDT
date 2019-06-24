package fr.pts2.usermanagement.timetable;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.pts2.timetable.TimeTableGenerator;
import fr.pts2.usermanagement.UserManagement;
import fr.pts2.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TimeTable implements Initializable {

	private static Stage s;
	private ToggleGroup group = new ToggleGroup();
	private LocalDate now = LocalDate.now();
		public static TimeTableGenerator table;
	
	@FXML
	private RadioButton available, avoid, unavailable;
	@FXML
	private Label name, week;
	@FXML
	private DatePicker datePicker;
	@FXML
	private GridPane buttonPane;
	
	public static void showStage() {
		try {
			s = new Stage();
			s.setTitle("Gérer utilisateur");
			s.setScene(new Scene(FXMLLoader.load(TimeTable.class.getResource("TimeTable.fxml"))));
			s.setResizable(false);
			s.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onConfirm() {
		table.saveAllConstraints();
	}

	@FXML
	public void openFixedConstraints() {
		//FixedConstraints.showStage();
	}

	//1 week before
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

	//1 week after
	@FXML
	public void weekAfter() {
		Utils.log("Plus 1 week.");
		now = now.plusWeeks(1);
		table.setDateAndRefresh(now);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Little dots inside the button
		available.setStyle("-fx-mark-color: green;");
		avoid.setStyle("-fx-mark-color: orange;");
		unavailable.setStyle("-fx-mark-color: red;");

		group.getToggles().addAll(available, avoid, unavailable);
		group.getToggles().get(0).setSelected(true);

		table = new TimeTableGenerator(UserManagement.user, buttonPane, name, week, group, available, avoid, unavailable, now);
		
		datePicker.setValue(now);
		datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			now = newValue;
			table.setDateAndRefresh(now);
		});
		table.setDateAndRefresh(now);
	}
}