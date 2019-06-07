package fr.pts2.timetable;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.pts2.Utils;
import fr.pts2.enums.Constraints;
import fr.pts2.enums.Intervals;
import fr.pts2.fixedconstraints.FixedConstraints;
import fr.pts2.login.LoginController;
import fr.pts2.sql.SQLConstraints;
import fr.pts2.sql.SQLFixedConstraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class TimeTableController implements Initializable {

	private ToggleGroup group = new ToggleGroup();
	private LocalDate now = LocalDate.now();
	private ArrayList<Button> timeTableButtons = new ArrayList<>();
	private ArrayList<Button> dayButtons = new ArrayList<>();

	@FXML
	private RadioButton dispo, pref, indispo;
	@FXML
	private Label name, weekId;
	@FXML
	private DatePicker datePicker;
	@FXML
	private GridPane buttonPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		for (int column = 0; column < 6; column++) {
			for (int row = 1; row < 5; row++) {
				String text = "";
				switch (row) {
				case 1:
					text = Intervals.FIRST.getString();
					break;
				case 2:
					text = Intervals.SECOND.getString();
					break;
				case 3:
					text = Intervals.THIRD.getString();
					break;
				case 4:
					text = Intervals.FOURTH.getString();
					break;
				}

				Button button = new Button(text);

				button.setPrefHeight(70.0D);
				button.setPrefWidth(100.0D);

				timeTableButtons.add(button);
				buttonPane.setAlignment(Pos.CENTER);
				button.setAlignment(Pos.CENTER);
				buttonPane.add(button, column, row);
			}
		}

		for (int column = 0; column < 6; column++) {
			Button button = new Button("");
			button.setPrefWidth(100.0D);
			dayButtons.add(button);
			buttonPane.add(button, column, 0);
		}

		name.setText(LoginController.getName()[0] + " " + LoginController.getName()[1]);

		dispo.setStyle("-fx-mark-color: green;");
		pref.setStyle("-fx-mark-color: orange;");
		indispo.setStyle("-fx-mark-color: red;");

		group.getToggles().addAll(dispo, pref, indispo);
		group.getToggles().get(0).setSelected(true);

		datePicker.setValue(now);
		datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			now = newValue;
			refreshTimeTable(now);
		});
		refreshTimeTable(now);
	}
	
	@FXML
	public void openSettings() {
		
	}

	@FXML
	public void onConfirm() {
		for(int i = 0; i < timeTableButtons.size(); i++) {
			int day = i / 4 + 1;
			int interval = Intervals.fromString(timeTableButtons.get(i).getText()).ordinal() + 1;
			Constraints constraint = Constraints.AVAILABLE;
			
			if(timeTableButtons.get(i).getStyle().contains("orange")) {
				constraint = Constraints.AVOID;
			} else if(timeTableButtons.get(i).getStyle().contains("red")) {
				constraint = Constraints.UNAVAILABLE;
			}
			
			SQLConstraints.createOrUpdateConstraint(LoginController.getName()[1], getWeekInt(), day, interval, constraint);
		}
		Utils.createAlert(AlertType.INFORMATION, "Information", "Les contraintes ont bien été sauvegardées.");
	}

	@FXML
	public void openFixedConstraints() {
		new FixedConstraints();
	}

	@FXML
	public void weekBefore() {
		if (now.minusWeeks(1).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear()) < LocalDate.now()
				.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear())) {
			return;
		}
		Utils.log("Minus 1 week.");
		now = now.minusWeeks(1);
		refreshTimeTable(now);
	}

	@FXML
	public void weekAfter() {
		Utils.log("Plus 1 week.");
		now = now.plusWeeks(1);
		refreshTimeTable(now);
	}


	private void refreshTimeTable(LocalDate date) {
		int i = 0;
		weekId.setText("Semaine " + now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear()));
		for (Button b : dayButtons) {
			switch (i) {
			case 0:
				b.setText("Lundi " + date.with(DayOfWeek.MONDAY).getDayOfMonth());
				b.setOnAction(e -> {
					selectColumn(0);
				});
				break;
			case 1:
				b.setText("Mardi " + date.with(DayOfWeek.TUESDAY).getDayOfMonth());
				b.setOnAction(e -> {
					selectColumn(1);
				});
				break;
			case 2:
				b.setText("Mercredi " + date.with(DayOfWeek.WEDNESDAY).getDayOfMonth());
				b.setOnAction(e -> {
					selectColumn(2);
				});
				break;
			case 3:
				b.setText("Jeudi " + date.with(DayOfWeek.THURSDAY).getDayOfMonth());
				b.setOnAction(e -> {
					selectColumn(3);
				});
				break;
			case 4:
				b.setText("Vendredi " + date.with(DayOfWeek.FRIDAY).getDayOfMonth());
				b.setOnAction(e -> {
					selectColumn(4);
				});
				break;
			case 5:
				b.setText("Samedi " + date.with(DayOfWeek.SATURDAY).getDayOfMonth());
				b.setOnAction(e -> {
					selectColumn(5);
				});
				break;
			}
			b.setStyle("-fx-alignment: CENTER;-fx-border-color: white;");
			i++;
		}

		for (Button b : timeTableButtons) {
			b.setStyle("-fx-background-color: green;-fx-alignment: CENTER;-fx-border-color: white;");
			b.setOnAction(e -> {
				if (group.getSelectedToggle().equals(indispo)) {
					b.setStyle("-fx-background-color: red;-fx-alignment: CENTER;-fx-border-color: white;");
				} else if (group.getSelectedToggle().equals(dispo)) {
					b.setStyle("-fx-background-color: green;-fx-alignment: CENTER;-fx-border-color: white;");
				} else if (group.getSelectedToggle().equals(pref)) {
					b.setStyle("-fx-background-color: orange;-fx-alignment: CENTER;-fx-border-color: white;");
				}
			});
		}

		for (String s : SQLConstraints.getConstraintsFromWeek(LoginController.getName()[1],
				getWeekInt())) {
			if (s == null || s.isEmpty())
				return;

			String[] split = s.split("_");

			int day = Integer.valueOf(split[1]);
			int interval = Integer.valueOf(split[2]);
			String color = "";

			switch (split[3]) {
			case "AVOID":
				color = "orange";
				break;
			case "UNAVAILABLE":
				color = "red";
				break;
			default:
				color = "green";
				break;
			}

			int pos = (day * 4 - 4) + (interval - 1);
			timeTableButtons.get(pos).setStyle("-fx-background-color: " + color + ";-fx-alignment: CENTER;-fx-border-color: white;");
		}
		
		for (String s : SQLFixedConstraints.getFixedConstraints(LoginController.getName()[1])) {
			if (s == null || s.isEmpty()) return;

			String[] split = s.split("_");

			int day = Integer.valueOf(split[1]);
			Utils.log(day+"");
			int interval = Integer.valueOf(split[2]);
			Utils.log(interval+"");
			String color = "";

			switch (split[3]) {
			case "AVOID":
				color = "orange";
				break;
			case "UNAVAILABLE":
				color = "red";
				break;
			default:
				color = "green";
				break;
			}

			int pos = (day * 4 - 4) + (interval - 1);
			timeTableButtons.get(pos).setStyle("-fx-background-color: " + color + ";-fx-alignment: CENTER;-fx-border-color: white;");
		}
		
		Utils.log("TimeTable has been refreshed.");
	}

	private void selectColumn(int day) {
		String color = "green";
		if (group.getSelectedToggle().equals(pref)) {
			color = "orange";
		} else if (group.getSelectedToggle().equals(indispo)) {
			color = "red";
		}
		for (int y = day * 4; y < day * 4 + 4; y++) {
			timeTableButtons.get(y)
					.setStyle("-fx-background-color: " + color + ";-fx-alignment: CENTER;-fx-border-color: white;");
		}
	}

	private int getWeekInt() {
		return now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
	}
}