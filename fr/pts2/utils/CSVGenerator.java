package fr.pts2.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import fr.pts2.enums.Availability;
import fr.pts2.sql.ConstraintHandler;
import fr.pts2.sql.WeekBuildedHandler;

public class CSVGenerator {

	private File file;
	private int year;

	public CSVGenerator(String path) {
		this.file = new File(path);
		this.year = LocalDate.now().getYear();
	}

	public void generateFile(User u) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			// Header
			bw.write("semaine (majuscule => construite) ; début - fin ; créneaux (minuscules => contrainte souple)\n");
			bw.write(";;\n");

			// On suppose que les vacances et les débuts/fins de cours sont les mêmes semaines chaque année.
			for (int i = 36; i < 42; i++) {
				bw.write(getStringWeek(u, i, year));
			}
			bw.write(";;\n");
			for (int i = 45; i < 51; i++) {
				bw.write(getStringWeek(u, i, year));
			}
			bw.write(";;\n");
			for (int i = 2; i < 7; i++) {
				bw.write(getStringWeek(u, i, year + 1));
			}
			bw.write(";;\n");
			for (int i = 10; i < 15; i++) {
				bw.write(getStringWeek(u, i, year + 1));
			}
			bw.write(";;\n");
			for (int i = 18; i < 25; i++) {
				bw.write(getStringWeek(u, i, year + 1));
			}
			bw.write(";;\n");
			bw.write(getStringWeek(u, 26, year + 1));
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getStringWeek(User u, int week, int year) {
		String weekBuilded = "s";
		if (WeekBuildedHandler.isWeekBuilded(week)) {
			weekBuilded = "S";
		}

		ArrayList<Constraint> constraints = ConstraintHandler.getConstraintsFromWeek(u, week);
		String result = "";
		
		for(int i = 1; i <= 5; i++)
			for(int y = 1; y <= 4; y++) {
				boolean alreadyAdded = false;
				//On vérifie pour une contrainte
				for(Constraint c : constraints) {
					if(c.getDay() == i && c.getInterval() == y) {
						result += getStringToCSV(c.getDay(), c.getInterval(), c.getAvailability());
						alreadyAdded = true;
					}
				}
				if(!alreadyAdded) result += getStringToCSV(i, y, Availability.AVAILABLE);
			}

		return weekBuilded + week + " ; " + getBeginningOfWeek(week, year) + " - " + getEndingOfWeek(week, year) + " ; " + result + "\n";
	}

	public String getStringToCSV(int day, int interval, Availability a) {
		String dayString = "";
		switch (day) {
		case 1:
			dayString = "L";
			break;
		case 2:
			dayString = "Ma";
			break;
		case 3:
			dayString = "Me";
			break;
		case 4:
			dayString = "J";
			break;
		case 5:
			dayString = "V";
			break;
		}

		String intervalString = "";
		switch (interval) {
		case 1:
			intervalString = "11";
			break;
		case 2:
			intervalString = "12";
			break;
		case 3:
			intervalString = "21";
			break;
		case 4:
			intervalString = "22";
			break;
		}

		
		// Si c'est "A éviter", mettre la lettre en minuscule
		if (a.equals(Availability.AVOID)) {
			dayString = dayString.toLowerCase();
			return dayString + intervalString + " ";
			
			// Si c'est "Indisponible", remplacer par des espaces
		} else if (a.equals(Availability.UNAVAILABLE)) {
			String result = "";
			String length = dayString + intervalString + " ";
			for (int i = 0; i < length.length(); i++) {
				result += " ";
			}
			return result;
			
			//Si c'est "Disponible", afficher le créneau
		} else {
			return dayString + intervalString + " ";
		}
	}

	public String getBeginningOfWeek(int week, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setWeekDate(year, week, Calendar.MONDAY);

		int dayNumber = cal.get(Calendar.DAY_OF_MONTH);
		String day = "" + dayNumber;
		if (dayNumber < 10) {
			day = "0" + dayNumber;
		}

		int monthNumber = cal.get(Calendar.MONTH) + 1;
		String month = "" + monthNumber;
		if (monthNumber < 10) {
			month = "0" + monthNumber;
		}

		return day + "/" + month + "/" + year;
	}

	public String getEndingOfWeek(int week, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setWeekDate(year, week, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, 5);

		int dayNumber = cal.get(Calendar.DAY_OF_MONTH);
		String day = "" + dayNumber;
		if (dayNumber < 10) {
			day = "0" + dayNumber;
		}

		int monthNumber = cal.get(Calendar.MONTH) + 1;
		String month = "" + monthNumber;
		if (monthNumber < 10) {
			month = "0" + monthNumber;
		}

		return day + "/" + month + "/" + year;
	}
}