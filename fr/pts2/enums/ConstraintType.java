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
}
