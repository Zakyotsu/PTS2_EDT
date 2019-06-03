package fr.pts2.enums;

public enum Intervals {

	FIRST("8h00-10h00"), SECOND("10h15-12h15"), THIRD("14h00-16h00"), FOURTH("16h15-18h15");

	String s;

	private Intervals(String s) {
		this.s = s;
	}

	public String getString() {
		return s;
	}

	public static Intervals fromString(String s) {
		s = s.toLowerCase();
		switch (s) {
		case "8h00-10h00":
			return FIRST;
		case "10h15-12h15":
			return SECOND;
		case "14h00-16h00":
			return THIRD;
		case "16h15-18h15":
			return FOURTH;
		}
		return null;
	}
	
	public static Intervals fromInt(int s) {
		switch (s) {
		case 1:
			return FIRST;
		case 2:
			return SECOND;
		case 3:
			return THIRD;
		case 4:
			return FOURTH;
		}
		return null;
	}
}