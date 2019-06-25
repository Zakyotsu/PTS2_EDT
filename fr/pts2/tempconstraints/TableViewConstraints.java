package fr.pts2.tempconstraints;

import fr.pts2.enums.Availability;
import fr.pts2.enums.ConstraintType;
import fr.pts2.utils.TempConstraint;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewConstraints {

	StringProperty name, constraintType, availability;
	IntegerProperty day, interval, beginningWeek, endingWeek;
	private TempConstraint tc;
	
	public TableViewConstraints(TempConstraint tc) {
		this.tc = tc;
		this.name = new SimpleStringProperty(tc.getConstraintName());
		this.day = new SimpleIntegerProperty(tc.getDay());
		this.interval = new SimpleIntegerProperty(tc.getInterval());
		this.beginningWeek = new SimpleIntegerProperty(tc.getBeginningWeek());
		this.endingWeek = new SimpleIntegerProperty(tc.getEndingWeek());
		this.constraintType = new SimpleStringProperty(tc.getType().getString());
		this.availability = new SimpleStringProperty(tc.getAvailability().getString());
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
	
	public String getName() {
		return name.getValue();
	}
	
	public ConstraintType getConstraintType() {
		return ConstraintType.fromString(constraintType.getValue());
	}
	
	public Availability getAvailability() {
		return Availability.fromString(availability.getValue());
	}
	
	public String getAvailabilityString() {
		return availability.getValue();
	}
	
	public TempConstraint getTempConstraint() {
		return tc;
	}
}
