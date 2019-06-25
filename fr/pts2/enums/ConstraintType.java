package fr.pts2.enums;

public enum ConstraintType {

	CONSTRAINT("C"), TEMP_CONSTRAINT("TC"), SCHOOL_CONSTRAINT("SC");
	
	String s;
	private ConstraintType(String s) {
		this.s = s;
	}
	
	public String getString() {
		return s;
	}
	
	public static ConstraintType fromString(String s) {
		s = s.toLowerCase();
		switch (s) {
		case "c":
			return CONSTRAINT;
		case "tc":
			return TEMP_CONSTRAINT;
		case "sc":
			return SCHOOL_CONSTRAINT;
		}
		return null;
	}
}
