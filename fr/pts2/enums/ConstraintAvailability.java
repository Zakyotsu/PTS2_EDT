package fr.pts2.enums;

public enum ConstraintAvailability {

	AVAILABLE("Disponible"), AVOID("A éviter"), UNAVAILABLE("Indisponible");

	String s;

	private ConstraintAvailability(String s) {
		this.s = s;
	}

	public String getString() {
		return s;
	}

	public static ConstraintAvailability fromString(String s) {
		s = s.toLowerCase();
		switch (s) {
		case "available":
			return AVAILABLE;
		case "avoid":
			return AVOID;
		case "unavailable":
			return UNAVAILABLE;
		}
		return null;
	}
}
