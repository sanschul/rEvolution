package edu.usp.ime.revolution.analyzers;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;
import edu.usp.ime.revolution.tools.Tool;

public class Error {

	private final String error;

	public Error(Commit commit, Exception e) {
		this.error = "something failed in commit " + commit.getCommitId() + "\n" + e.getMessage();
	}

	public Error(Tool tool, Commit commit, Exception e) {
		this.error = tool.getName() + " failed in changeset " + commit.getCommitId() + "\n" + e.getMessage();
	}

	public Error(ChangeSet changeSet, Exception e) {
		this.error = "something failed in changeset " + changeSet.getId() + "\n" + e.getMessage();
	}

	public String getError() {
		return error;
	}
}
