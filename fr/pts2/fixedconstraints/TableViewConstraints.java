package fr.pts2.fixedconstraints;

import fr.pts2.enums.ConstraintAvailability;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewConstraints {

	StringProperty day, interval;
	ObjectProperty constraint;
	
	public TableViewConstraints(String day, String interval, ConstraintAvailability constraint) {
		this.day = new SimpleStringProperty(day);
		this.interval = new SimpleStringProperty(interval);
		this.constraint = new SimpleObjectProperty(constraint);
	}
	
	public String getDay() {
		return day.getValue();
	}
	
	public String getInterval() {
		return interval.getValue();
	}
	
	public String getConstraint() {
		String constraint = "";
		switch(this.constraint.getValue().toString()) {
		case "AVAILABLE":
			constraint = ConstraintAvailability.AVAILABLE.getString();
			break;
		case "AVOID":
			constraint = ConstraintAvailability.AVOID.getString();
			break;
		case "UNAVAILABLE":
			constraint = ConstraintAvailability.UNAVAILABLE.getString();
			break;
		}
		return constraint;
	}
}
