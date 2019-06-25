package fr.pts2.timetable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import fr.pts2.enums.Availability;
import fr.pts2.enums.Intervals;
import fr.pts2.login.LoginStage;
import fr.pts2.sql.ConstraintHandler;
import fr.pts2.sql.TempConstraintHandler;
import fr.pts2.utils.Constraint;
import fr.pts2.utils.TempConstraint;
import fr.pts2.utils.User;
import fr.pts2.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class TimeTableGenerator {

	private ArrayList<Button> dayButtons = new ArrayList<>();
	private ArrayList<Button> timeTableButtons = new ArrayList<>();
	private LocalDate currentDate;
	private Label name, week;
	private ToggleGroup group;
	private RadioButton available, avoid, unavailable;
	private GridPane pane;
	private User user;

	public TimeTableGenerator(User user, GridPane pane, Label name, Label week, ToggleGroup group,
			RadioButton available, RadioButton avoid, RadioButton unavailable, LocalDate currentDate) {
		this.user = user;
		this.pane = pane;
		this.name = name;
		this.week = week;
		this.group = group;
		this.available = available;
		this.avoid = avoid;
		this.unavailable = unavailable;
		this.currentDate = currentDate;
		generateTable();
	}

	private void generateTable() {
		// Generate headers
		for (int column = 0; column < 5; column++) {
			Button button = new Button("");
			button.setPrefWidth(120.0D);
			dayButtons.add(button);
			pane.add(button, column, 0);
		}

		// Generate constraints buttons
		for (int column = 0; column < 5; column++) {
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
				button.setPrefWidth(120.0D);

				timeTableButtons.add(button);
				pane.setAlignment(Pos.CENTER);
				button.setAlignment(Pos.CENTER);
				pane.add(button, column, row);
			}
		}

		// Refresh events handlers
		for (Button b : timeTableButtons) {
			b.setStyle("-fx-background-color: green;-fx-alignment: CENTER;-fx-border-color: white;");
			b.setOnAction(e -> {
				if (group.getSelectedToggle().equals(unavailable)) {
					b.setStyle("-fx-background-color: red;-fx-alignment: CENTER;-fx-border-color: white;");
				} else if (group.getSelectedToggle().equals(available)) {
					b.setStyle("-fx-background-color: green;-fx-alignment: CENTER;-fx-border-color: white;");
				} else if (group.getSelectedToggle().equals(avoid)) {
					b.setStyle("-fx-background-color: orange;-fx-alignment: CENTER;-fx-border-color: white;");
				}
			});
		}
	}

	public void refreshTopInfo() {
		name.setText("M/Mme " + user.getName() + " " + user.getLastname());
		week.setText("Semaine " + ConstraintHandler.isWeekBuilded(getWeekInt()) + " : " + getWeekInt());
	}

	public void setDateAndRefresh(LocalDate date) {
		currentDate = date;
		refreshTopInfo();
		int i = 0;
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
			}
			b.setStyle("-fx-alignment: CENTER;-fx-border-color: white;");
			i++;
		}
		Utils.log("TimeTable has been refreshed.");
		refreshConstraints();
	}

	public void refreshConstraints() {
		for (Button b : timeTableButtons) {
			b.setStyle("-fx-background-color: green;-fx-alignment: CENTER;-fx-border-color: white;");
		}

		for (TempConstraint tc : TempConstraintHandler.getTempConstraints(user)) {
			if (tc.getBeginningWeek() <= getWeekInt() && tc.getEndingWeek() >= getWeekInt()) {
				int pos = (tc.getDay() * 4 - 4) + tc.getInterval() - 1;
				timeTableButtons.get(pos).setStyle(tc.getStyle());
				
				//Supprimer la contrainte temporaire obsolète.
			} else if(tc.getEndingWeek() > getWeekInt()) TempConstraintHandler.deleteTempConstraint(LoginStage.getUser(), tc);
		}

		for (Constraint c : ConstraintHandler.getConstraintsFromWeek(user, getWeekInt())) {
			int pos = (c.getDay() * 4 - 4) + c.getInterval() - 1;
			timeTableButtons.get(pos).setStyle(c.getStyle());
		}
	}

	// If the user wants to select an entire day
	private void selectColumn(int day) {
		String color = "green";
		if (group.getSelectedToggle().equals(avoid)) {
			color = "orange";
		} else if (group.getSelectedToggle().equals(unavailable)) {
			color = "red";
		}
		for (int y = day * 4; y < day * 4 + 4; y++) {
			timeTableButtons.get(y)
					.setStyle("-fx-background-color: " + color + ";-fx-alignment: CENTER;-fx-border-color: white;");
		}
	}

	// Save regular constraints
	public void saveAllConstraints() {
		for (int i = 0; i < timeTableButtons.size(); i++) {
			int day = i / 4 + 1;
			int interval = Intervals.fromString(timeTableButtons.get(i).getText()).ordinal() + 1;

			Availability constraint = Availability.AVAILABLE;

			if (timeTableButtons.get(i).getStyle().contains("orange")) {
				constraint = Availability.AVOID;
			} else if (timeTableButtons.get(i).getStyle().contains("red")) {
				constraint = Availability.UNAVAILABLE;
			}

			ConstraintHandler.createOrUpdateConstraint(user, getWeekInt(), day, interval, constraint, false);
		}
		Utils.createAlert(AlertType.INFORMATION, "Information", "Les contraintes ont bien été sauvegardées.");
	}

	private int getWeekInt() {
		return currentDate.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
	}
}
