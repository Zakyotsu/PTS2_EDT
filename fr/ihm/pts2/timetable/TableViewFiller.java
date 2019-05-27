package fr.ihm.pts2.timetable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewFiller {

	private StringProperty columnName;
	
	public TableViewFiller(String columnName) {
		this.columnName = new SimpleStringProperty(columnName);
	}
	
	public String getColumnName() {
		return columnName.get();
	}
	
	public StringProperty getColumnNameProperty() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName.set(columnName);
	}
}
