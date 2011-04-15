package edu.usp.ime.revolution.scm.git;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetInfo;

public class GitChangeSet implements ChangeSet {

	private final String path;
	private final ChangeSetInfo info;

	public GitChangeSet(ChangeSetInfo info, String path) {
		this.info = info;
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public ChangeSetInfo getInfo() {
		return info;
	}

}
