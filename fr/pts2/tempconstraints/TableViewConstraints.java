package fr.pts2.tempconstraints;

import fr.pts2.enums.Availability;
import fr.pts2.enums.ConstraintType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewConstraints {

	StringProperty name;
	IntegerProperty day, interval, beginningWeek, endingWeek;
	ObjectProperty<ConstraintType> constraintType;
	ObjectProperty<Availability> availability;
	
	public TableViewConstraints(String name, int day, int interval, int beginningWeek, int endingWeek, ConstraintType constraintType, Availability availability) {
		this.name = new SimpleStringProperty(name);
		this.day = new SimpleIntegerProperty(day);
		this.interval = new SimpleIntegerProperty(interval);
		this.beginningWeek = new SimpleIntegerProperty(beginningWeek);
		this.endingWeek = new SimpleIntegerProperty(endingWeek);
		this.constraintType = new SimpleObjectProperty<ConstraintType>(constraintType);
		this.availability = new SimpleObjectProperty<Availability>(availability);
	}
	
	public int getDay() {
		return day.getValue();
	}
	
	public int getEndingWeek() {
		return endingWeek.getValue();
	}
	
	public int getBeginningWeek() {
		return beginningWeek.getValue();
	}
	
	public int getInterval() {
		return interval.getValue();
	}
	
	public String getConstraintName() {
		return name.getValue();
	}
	
	public ConstraintType getConstraintType() {
		return constraintType.getValue();
	}
	
	public Availability getAvailability() {
		Availability constraint = null;
		switch(this.availability.getValue().toString()) {
		case "AVAILABLE":
			constraint = Availability.AVAILABLE;
			break;
		case "AVOID":
			constraint = Availability.AVOID;
			break;
		case "UNAVAILABLE":
			constraint = Availability.UNAVAILABLE;
			break;
		}
		return constraint;
	}
}
