package fr.pts2.fixedconstraints;

import fr.pts2.enums.Constraints;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewConstraints {

	StringProperty day, interval;
	ObjectProperty constraint;
	
	public TableViewConstraints(String day, String interval, Constraints constraint) {
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
			constraint = Constraints.AVAILABLE.getString();
			break;
		case "AVOID":
			constraint = Constraints.AVOID.getString();
			break;
		case "UNAVAILABLE":
			constraint = Constraints.UNAVAILABLE.getString();
			break;
		}
		return constraint;
	}
}
