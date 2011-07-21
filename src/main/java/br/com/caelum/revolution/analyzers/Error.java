package br.com.caelum.revolution.analyzers;

import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.tools.Tool;

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
