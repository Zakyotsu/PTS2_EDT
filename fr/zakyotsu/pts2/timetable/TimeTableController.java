package fr.zakyotsu.pts2.timetable;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.zakyotsu.pts2.login.LoginController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

public class TimeTableController implements Initializable {

	private LocalDate now = LocalDate.now();
	private ToggleGroup group = new ToggleGroup();
	@FXML
	private TableView<TableViewFiller> tableView;
	@FXML
	private TableColumn<TableViewFiller, String> monday, tuesday, wednesday, thursday, friday, saturday;
	@FXML
	private RadioButton dispo, pref, indispo;
	@FXML
	private Label name, weekId;
	@FXML
	private DatePicker datePicker;
	
	@FXML
	public void onConfirm() {

	}

	@FXML
	public void weekBefore() {
		if(now.minusWeeks(1).get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear()) < LocalDate.now().get(WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear())) {
			return;
		}
		now = now.minusWeeks(1);
		refreshColumns(now);
	}

	@FXML
	public void weekAfter() {
		now = now.plusWeeks(1);
		refreshColumns(now);
	}

	private void refreshColumns(LocalDate date) {
		WeekFields weekFields = WeekFields.of(Locale.FRANCE);
		int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
		String mois = "";
		switch (date.getMonth()) {
		case JANUARY:
			mois = "de janvier";
			break;
		case FEBRUARY:
			mois = "de février";
			break;
		case MARCH:
			mois = "de mars";
			break;
		case APRIL:
			mois = "d'avril";
			break;
		case MAY:
			mois = "de mai";
			break;
		case JUNE:
			mois = "de juin";
			break;
		case JULY:
			mois = "de juillet";
			break;
		case AUGUST:
			mois = "d'août";
			break;
		case SEPTEMBER:
			mois = "de septembre";
			break;
		case OCTOBER:
			mois = "d'octobre";
			break;
		case NOVEMBER:
			mois = "de novembre";
			break;
		case DECEMBER:
			mois = "de décembre";
			break;
		}

		weekId.setText("Semaine " + weekNumber + " " + mois);

		monday.setText("Lundi " + date.with(DayOfWeek.MONDAY).getDayOfMonth());
		tuesday.setText("Mardi " + date.with(DayOfWeek.TUESDAY).getDayOfMonth());
		wednesday.setText("Mercredi " + date.with(DayOfWeek.WEDNESDAY).getDayOfMonth());
		thursday.setText("Jeudi " + date.with(DayOfWeek.THURSDAY).getDayOfMonth());
		friday.setText("Vendredi " + date.with(DayOfWeek.FRIDAY).getDayOfMonth());
		saturday.setText("Samedi " + date.with(DayOfWeek.SATURDAY).getDayOfMonth());
		tableView.refresh();
	}

	@FXML
	public void onTableViewClick() {
		System.out.println("cc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		name.setText("Mr/Mme " + LoginController.getName());

		group.getToggles().addAll(dispo, pref, indispo);
		group.getToggles().get(0).setSelected(true);

		datePicker.setValue(now);
		refreshColumns(now);

		monday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		tuesday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		wednesday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		thursday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		friday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());
		saturday.setCellValueFactory(cellData -> cellData.getValue().getColumnNameProperty());

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

		for (TableColumn tc : tableView.getColumns()) {
			tc.setSortable(false);

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
		}

		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.setEditable(false);
		tableView.setFixedCellSize(72.8D);
		tableView.setItems(test);
	}

	private ObservableList<TableViewFiller> test = FXCollections.observableArrayList(new TableViewFiller("08h00-10h00"),
			new TableViewFiller("10h15-12h15"), new TableViewFiller("14h00-16h00"), new TableViewFiller("16h15-18h15"));
}
