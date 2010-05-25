package edu.usp.ime.revolution.scm.git;

import java.util.Calendar;

import edu.usp.ime.revolution.scm.ChangeSet;

public class GitChangeSet implements ChangeSet {

	private final String id;
	private final String path;

	public GitChangeSet(String id, String path) {
		this.id = id;
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public Calendar getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPath() {
		return path;
	}

}
