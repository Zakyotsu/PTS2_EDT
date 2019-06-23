package fr.pts2.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	StringProperty name, lastname, trigram;
	IntegerProperty uuid;
	
	public User(String name, String lastname, String trigram, int uuid) {
		this.name = new SimpleStringProperty(name);
		this.lastname = new SimpleStringProperty(lastname);
		this.trigram = new SimpleStringProperty(trigram);
		this.uuid = new SimpleIntegerProperty(uuid);
	}

	public String getName() {
		return name.getValue().toUpperCase();
	}

	public String getLastname() {
		return lastname.getValue().toUpperCase();
	}

	public String getTrigram() {
		return trigram.getValue().toUpperCase();
	}
	
	public int getUUID() {
		return uuid.getValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			if(((User) obj).getUUID() == getUUID()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "User:[name=" + getName() + ", lastname=" + getLastname() + ", trigram=" + getTrigram() + ", id=" + getUUID()+"]";
	}
}
