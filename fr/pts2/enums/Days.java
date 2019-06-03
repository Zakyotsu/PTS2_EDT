package fr.pts2.enums;

public enum Days {

	MONDAY("Lundi"), TUESDAY("Mardi"), WEDNESDAY("Mercredi"), THURSDAY("Jeudi"), FRIDAY("Vendredi"), SATURDAY("Samedi");

	String s;

	private Days(String s) {
		this.s = s;
	}

	public String getString() {
		return s;
	}

	public static Days fromString(String s) {
		s = s.toLowerCase();
		switch (s) {
		case "lundi":
			return MONDAY;
		case "mardi":
			return TUESDAY;
		case "mercredi":
			return WEDNESDAY;
		case "jeudi":
			return THURSDAY;
		case "vendredi":
			return FRIDAY;
		case "samedi":
			return SATURDAY;
		}
		return null;
	}
	
	public static Days fromInt(int s) {
		switch (s) {
		case 0:
			return MONDAY;
		case 1:
			return TUESDAY;
		case 2:
			return WEDNESDAY;
		case 3:
			return THURSDAY;
		case 4:
			return FRIDAY;
		case 5:
			return SATURDAY;
		}
		return null;
	}
}