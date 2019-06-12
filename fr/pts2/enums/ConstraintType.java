package fr.pts2.enums;

public enum ConstraintType {

	CONSTRAINT("C"), FIXED_CONSTRAINT("FC"), SCHOOL_CONSTRAINT("SC");
	
	String s;
	private ConstraintType(String s) {
		this.s = s;
	}
	
	public String getString() {
		return s;
	}
}
