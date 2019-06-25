package fr.pts2.enums;

public enum Days {

	MONDAY("Lundi"), TUESDAY("Mardi"), WEDNESDAY("Mercredi"), THURSDAY("Jeudi"), FRIDAY("Vendredi");

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
		}
		return null;
	}
	
	public static Days fromInt(int s) {
		switch (s) {
		case 1:
			return MONDAY;
		case 2:
			return TUESDAY;
		case 3:
			return WEDNESDAY;
		case 4:
			return THURSDAY;
		case 5:
			return FRIDAY;
		}
		return null;
	}
}