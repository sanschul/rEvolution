package edu.usp.ime.revolution.analyzers;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.tools.MetricTool;

public class Error {

	private final String error;

	public Error(Commit commit, Exception e) {
		this.error = "something failed in commit " + commit.getId() + "\n" + e.getMessage();
	}

	public Error(MetricTool tool, Commit commit, Exception e) {
		this.error = tool.getName() + " failed in changeset " + commit.getId() + "\n" + e.getMessage();
	}

	public Error(ChangeSet changeSet, Exception e) {
		this.error = "something failed in changeset " + changeSet.getId() + "\n" + e.getMessage();
	}

	public String getError() {
		return error;
	}
}
