package edu.usp.ime.revolution.scm.git;

import java.util.Calendar;

import edu.usp.ime.revolution.scm.ChangeSet;

public class GitChangeSet implements ChangeSet {

	private final String id;

	public GitChangeSet(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Calendar getTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
