package fr.ihm.pts2.timetable;

import fr.ihm.pts2.Utils;
import fr.ihm.pts2.login.LoginController;
import fr.ihm.pts2.sql.SQLAPI;
import fr.ihm.pts2.sql.SQLConnector;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class TimeTableController implements Initializable {

	private LocalDate now = LocalDate.now();
	private ToggleGroup group = new ToggleGroup();
	private ArrayList<Object> o = new ArrayList<>();
	private int currentWeek = now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
	@FXML
	private TableView<TableViewFiller> tableView;
	@FXML
	private TableColumn<TableViewFiller, String> monday, tuesday, wednesday, thursday, friday, saturday;
	@FXML
	private RadioButton dispo, pref, indispo;
	@FXML
	private Label name, weekId;
	@FXML
	private DatePicker datepicker;

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
		refreshColumns(now);
	}

	@FXML
	public void weekAfter() {
		Utils.log("Plus 1 week.");
		now = now.plusWeeks(1);
		currentWeek = now.get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear());
		refreshColumns(now);
	}

	private void refreshColumns(LocalDate date) {
		WeekFields weekFields = WeekFields.of(Locale.FRANCE);
		int weekNumber = date.get(weekFields.weekOfWeekBasedYear());

		weekId.setText("Semaine " + weekNumber);
		monday.setText("Lundi " + date.with(DayOfWeek.MONDAY).getDayOfMonth());
		tuesday.setText("Mardi " + date.with(DayOfWeek.TUESDAY).getDayOfMonth());
		wednesday.setText("Mercredi " + date.with(DayOfWeek.WEDNESDAY).getDayOfMonth());
		thursday.setText("Jeudi " + date.with(DayOfWeek.THURSDAY).getDayOfMonth());
		friday.setText("Vendredi " + date.with(DayOfWeek.FRIDAY).getDayOfMonth());
		saturday.setText("Samedi " + date.with(DayOfWeek.SATURDAY).getDayOfMonth());
		tableView.refresh();
		Utils.log("Columns have been refreshed.");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		name.setText(LoginController.getName()[0] + " " + LoginController.getName()[1]);

		group.getToggles().addAll(dispo, pref, indispo);
		group.getToggles().get(0).setSelected(true);

		datePicker.setValue(now);
		refreshColumns(now);

		// Adding intervals to columns
		monday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		tuesday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		wednesday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		thursday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		friday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		saturday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());

		// Setting up TableView
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setFixedCellSize(72.8D);
		tableView.setItems(test);

		// Prevent column sorting on TableView
		tableView.getColumns().addListener(new ListChangeListener<TableColumn<TableViewFiller, ?>>() {
			@Override
			public void onChanged(Change change) {
				change.next();
				if (change.wasReplaced()) {
					tableView.getColumns().clear();
					tableView.getColumns().addAll(monday, tuesday, wednesday, thursday, friday, saturday);
				}
			}
		});

		// Setting up columns
		/*for (TableColumn tc : tableView.getColumns()) {
			tc.setSortable(false);
			tc.setResizable(false);

			tc.setCellFactory(column -> {
				TableCell<TableViewFiller, String> cell = new TableCell<TableViewFiller, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						setStyle("-fx-background-color: green;-fx-alignment: CENTER;");
						setText(empty ? null : item);
					}
				};
				
				cell.setOnMouseClicked(e -> {
					if (group.getSelectedToggle().equals(indispo)) {
						cell.setStyle("-fx-background-color: red;-fx-alignment: CENTER;");
					} else if (group.getSelectedToggle().equals(dispo)) {
						cell.setStyle("-fx-background-color: green;-fx-alignment: CENTER;");
					} else if (group.getSelectedToggle().equals(pref)) {
						cell.setStyle("-fx-background-color: orange;-fx-alignment: CENTER;");
					}
				});

				return cell;
			});
		}*/

		applyConstraintsFromWeek(currentWeek);
	}

	int i = 0;
	int y = 0;
	private void applyConstraintsFromWeek(int week) {
		HashMap<int[], String> coloredCells = new HashMap<>();

		for (String s : SQLAPI.getConstraintsFromWeek(SQLConnector.getConnection(), LoginController.getName()[1], week)) {
			if(s == null) return;
			String[] split = s.split("_");

			int[] position = new int[2];
			position[0] = Integer.valueOf(split[1])-1;
			position[1] = Integer.valueOf(split[2])-1;

			String color = "";
			switch(split[3]) {
				case "E":
					color = "orange";
					break;
				case "I":
					color = "red";
					break;
			}

			coloredCells.put(position, color);
			Utils.log(coloredCells+"");
		}

		for(i = 0; i < tableView.getColumns().size(); i++) {

			((TableColumn<TableViewFiller, String>) tableView.getColumns().get(i)).setCellFactory(column -> {
				TableCell<TableViewFiller, String> cell = new TableCell<>() {

					/*{
						for(y = 0; y < 4; y++) {



						}
					}*/

					@Override
					protected void updateItem(String item, boolean empty) {


						setStyle("-fx-background-color: green;-fx-alignment: CENTER;");
						setText(empty ? null : item);

						for(y = 0; y < 4; y++) {
							int[] pos = new int[2];
							pos[0] = i;
							pos[1] = y;

							String color = coloredCells.get(pos);
							Utils.log(color);

							if(color == null) {
								color = "green";
							}

							setStyle("-fx-background-color: " + color + ";-fx-alignment: CENTER;");
							setText(empty ? null : item);

						}
					}
				};

				cell.setOnMouseClicked(e -> {
					if (group.getSelectedToggle().equals(indispo)) {
						cell.setStyle("-fx-background-color: red;-fx-alignment: CENTER;");
					} else if (group.getSelectedToggle().equals(dispo)) {
						cell.setStyle("-fx-background-color: green;-fx-alignment: CENTER;");
					} else if (group.getSelectedToggle().equals(pref)) {
						cell.setStyle("-fx-background-color: orange;-fx-alignment: CENTER;");
					}
				});

				return cell;
			});
		}
	tableView.refresh();
	}

	private ObservableList<TableViewFiller> test = FXCollections.observableArrayList(new TableViewFiller("08h00-10h00"),
			new TableViewFiller("10h15-12h15"), new TableViewFiller("14h00-16h00"), new TableViewFiller("16h15-18h15"));
}
