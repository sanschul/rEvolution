package edu.usp.ime.revolution.scm;

import java.util.Calendar;

public class ChangeSetInfo {
	
	private final Calendar date;
	private final String id;

	public ChangeSetInfo(String id, Calendar date) {
		this.id = id;
		this.date = date;
		
	}

	public Calendar getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

}
