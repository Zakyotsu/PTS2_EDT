package fr.pts2.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	StringProperty name, lastname, trigram;
	
	public User(String name, String lastname, String trigram) {
		this.name = new SimpleStringProperty(name);
		this.lastname = new SimpleStringProperty(lastname);
		this.trigram = new SimpleStringProperty(trigram);
	}

	public String getName() {
		return name.getValue();
	}

	public String getLastname() {
		return lastname.getValue();
	}

	public String getTrigram() {
		return trigram.getValue().toUpperCase();
	}
}
