package fr.pts2.csv;

import java.io.File;

import fr.pts2.login.LoginStage;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class CSVStage {

	private DirectoryChooser dc;
	
	public CSVStage() {
		dc = new DirectoryChooser();
		
		dc.setInitialDirectory(new File(System.getProperty("user.home")));
		dc.setTitle("Sélectionnez un dossier");
		
	}

	public File getDirectoryChooserFile(Stage s) {
		File file = dc.showDialog(s);
		if(file == null) return null;
		File csv = new File(file.getAbsolutePath() + File.separatorChar + LoginStage.getUser().getTrigram() + ".csv");
		return csv; 
	}
	
	public void generateForAllUsers(String path) {
		File file = new File(path);
	}
	
	public DirectoryChooser getDirectoryChooser() {
		return dc;
	}
}