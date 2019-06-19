package fr.pts2.utils;

import fr.pts2.enums.ConstraintType;
import fr.pts2.enums.Availability;

public class Constraint {

	private ConstraintType type;
	private Availability availability;
	private int day;
	private int interval;
	
	public Constraint(ConstraintType type, Availability availability, int day, int interval) {
		this.type = type;
		this.availability = availability;
		this.day = day;
		this.interval = interval;
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

	public int getInterval() {
		return interval;
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
	
	public String toString() {
		return getType().getString() + "_" + getDay() + "_" + getInterval() + "_" + getAvailability().ordinal();
	}
}