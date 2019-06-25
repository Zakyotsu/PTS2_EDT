package fr.pts2.csv;

import java.io.File;

import fr.pts2.login.LoginStage;
import fr.pts2.sql.UsersHandler;
import fr.pts2.utils.User;
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
		for(User u : UsersHandler.getUsers()) {
			File f = new File(path, u.getTrigram() + ".csv");
			CSVGenerator csv = new CSVGenerator(f.getAbsolutePath());
			csv.generateFile(u);
		}
	}
	
	public DirectoryChooser getDirectoryChooser() {
		return dc;
	}
}