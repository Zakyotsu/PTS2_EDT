package fr.ihm.pts2.timetable;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.ihm.pts2.Utils;
import fr.ihm.pts2.login.LoginController;
import fr.ihm.pts2.sql.SQLAPI;
import fr.ihm.pts2.sql.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class TimeTableController implements Initializable {

	private ToggleGroup group = new ToggleGroup();
	private LocalDate now = LocalDate.now();
	private int currentWeek = now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
	private ArrayList<Button> buttons = new ArrayList<>();

	@FXML
	private RadioButton dispo, pref, indispo;
	@FXML
	private Label name, weekId;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Label monday, tuesday, wednesday, thursday, friday, saturday;
	@FXML
	private GridPane buttonPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		for (int column = 0; column < 6; column++) {
			for (int row = 1; row < 5; row++) {
				String text = "";
				switch (row) {
				case 1:
					text = "8h00 - 10h00";
					break;
				case 2:
					text = "10h15 - 12h15";
					break;
				case 3:
					text = "14h00 - 16h00";
					break;
				case 4:
					text = "16h15 - 18h15";
					break;
				}

				Button button = new Button(text);

				button.setPrefHeight(70.0D);
				button.setPrefWidth(100.0D);

				buttons.add(button);
				buttonPane.add(button, column, row);
			}
		}

		name.setText(LoginController.getName()[0] + " " + LoginController.getName()[1]);

		group.getToggles().addAll(dispo, pref, indispo);
		group.getToggles().get(0).setSelected(true);

		datePicker.setValue(now);
		refreshColumnsTitles(now);
		refreshButtonsColors();
	}

	@FXML
	public void onConfirm() {

	}

	@FXML
	public void weekBefore() {
		if (now.minusWeeks(1).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear()) < LocalDate.now()
				.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear())) {
			return;
		}
		Utils.log("Minus 1 week.");
		now = now.minusWeeks(1);
		currentWeek = now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
		refreshColumnsTitles(now);
		refreshButtonsColors();
	}

	@FXML
	public void weekAfter() {
		Utils.log("Plus 1 week.");
		now = now.plusWeeks(1);
		currentWeek = now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
		refreshColumnsTitles(now);
		refreshButtonsColors();
	}

	private void refreshColumnsTitles(LocalDate date) {
		weekId.setText("Semaine " + currentWeek);
		monday.setText("Lundi " + date.with(DayOfWeek.MONDAY).getDayOfMonth());
		tuesday.setText("Mardi " + date.with(DayOfWeek.TUESDAY).getDayOfMonth());
		wednesday.setText("Mercredi " + date.with(DayOfWeek.WEDNESDAY).getDayOfMonth());
		thursday.setText("Jeudi " + date.with(DayOfWeek.THURSDAY).getDayOfMonth());
		friday.setText("Vendredi " + date.with(DayOfWeek.FRIDAY).getDayOfMonth());
		saturday.setText("Samedi " + date.with(DayOfWeek.SATURDAY).getDayOfMonth());
		Utils.log("Columns titles have been refreshed.");
	}

	private void refreshButtonsColors() {
		for(Button b : buttons) {
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
		
		for (String s : SQLAPI.getConstraintsFromWeek(SQLConnector.getConnection(), LoginController.getName()[1], currentWeek)) {
			if(s == null || s.isEmpty()) return;
			
			String[] split = s.split("_");
			
			int day = Integer.valueOf(split[1]);
			int interval = Integer.valueOf(split[2]); 
			String color = "";

			switch(split[3]) {
			case "E":
				color = "orange";
				break;
			case "I":
				color = "red";
				break;
			default:
				color = "green";
				break;
			}
			 
			int pos = (day * 4 - 4) + (interval - 1);
			buttons.get(pos).setStyle("-fx-background-color: " + color + ";-fx-alignment: CENTER;-fx-border-color: white;");
		}
	}
}