package fr.pts2;

import java.io.File;

public class Constants {

	public static final File DEFAULT_FOLDER = new File(System.getProperty("user.home") + File.separatorChar + "GPUConstraints" + File.separatorChar);
	public static final File CREDENTIALS_FILE = new File(DEFAULT_FOLDER.getAbsolutePath(), "credentials.enc");
	public static final File DEFAULT_FOLDER_EXPORT = new File(DEFAULT_FOLDER.getAbsolutePath() + "csvFiles" + File.separatorChar);
	
}
