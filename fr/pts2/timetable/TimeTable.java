package fr.pts2.timetable;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.pts2.advanced.AdvancedMenu;
import fr.pts2.login.LoginStage;
import fr.pts2.utils.User;
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

	private static Stage stage;
	private User user = LoginStage.getUser();
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
			stage = new Stage();
			stage.setTitle("GPUConstraints");
			stage.setScene(new Scene(FXMLLoader.load(TimeTable.class.getResource("TimeTable.fxml"))));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		table = new TimeTableGenerator(user, buttonPane, name, week, group, available, avoid, unavailable, now);

		// Little dots inside the button
		available.setStyle("-fx-mark-color: green;");
		avoid.setStyle("-fx-mark-color: orange;");
		unavailable.setStyle("-fx-mark-color: red;");

		group.getToggles().addAll(available, avoid, unavailable);
		group.getToggles().get(0).setSelected(true);

		datePicker.setValue(now);
		datePicker.valueProperty().addListener((list, oldValue, newValue) -> {
			now = newValue;
			table.setDateAndRefresh(now);
		});
		table.setDateAndRefresh(now);
	}

	@FXML
	public void openAdvancedMenu() {
		AdvancedMenu.showStage();
	}

	@FXML
	public void onConfirm() {
		table.saveAllConstraints();
	}

	// 1 week back
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

	// 1 week forward
	@FXML
	public void weekAfter() {
		Utils.log("Plus 1 week.");
		now = now.plusWeeks(1);
		table.setDateAndRefresh(now);
	}

	public User getUser() {
		return user;
	}
}
