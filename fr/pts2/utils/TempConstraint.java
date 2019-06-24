package fr.pts2.utils;

import fr.pts2.enums.Availability;
import fr.pts2.enums.ConstraintType;

public class TempConstraint {

	private ConstraintType type;
	private Availability availability;
	private String constraintName;
	private int day;
	private int interval;
	private int beginningWeek;
	private int endingWeek;
	
	public TempConstraint(ConstraintType type, Availability availability, String constraintName, int day, int interval, int beginningWeek, int endingWeek) {
		this.type = type;
		this.availability = availability;
		this.constraintName = constraintName;
		this.day = day;
		this.interval = interval;
		this.beginningWeek = beginningWeek;
		this.endingWeek = endingWeek;
	}

	public int getDay() {
		return day;
	}

	public ConstraintType getType() {
		return type;
	}

	public Availability getAvailability() {
		return availability;
	}

	public String getConstraintName() {
		return constraintName;
	}
	
	public int getInterval() {
		return interval;
	}
	
	public int getBeginningWeek() {
		return beginningWeek;
	}
	
	public int getEndingWeek() {
		return endingWeek;
	}
	
	public String getStyle() {
		String color = "";
		switch(availability) {
		case AVAILABLE:
			color = "green";
			break;
		case AVOID:
			color = "orange";
			break;
		case UNAVAILABLE:
			color = "red";
			break;
		}
		return "-fx-background-color: " + color + ";-fx-alignment: CENTER;-fx-border-color: white;";
	}
	
	@Override
	public String toString() {
		return getType().getString() + "_" + getDay() + "_" + getInterval() + "_" + getAvailability().ordinal();
	}
}
