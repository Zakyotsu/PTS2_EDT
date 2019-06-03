package fr.pts2.fixedconstraints;

import fr.pts2.fixedconstraints.add.AddFixedConstraints;
import javafx.fxml.FXML;

public class FixedConstraintsController {

	@FXML
	public void addFixedConstraint() {
		try {
			new AddFixedConstraints();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void deleteFixedConstraint() {
		
	}
	
}
