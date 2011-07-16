package edu.usp.ime.revolution.scm.git;

import java.util.List;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMException;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;

public class GitClone implements SCM{

	private final Git git;

	public GitClone(String gitRepository, String destiny, CommandExecutor exec, Git git) {
		this.git = git;
		
		try {
			exec.execute("git clone " + gitRepository + " " + destiny, destiny);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}

	public List<ChangeSet> getChangeSets() {
		return git.getChangeSets();
	}

	public String goTo(ChangeSet cs) {
		return git.goTo(cs);
	}

	public Commit detail(String id) {
		return git.detail(id);
	}
	

}
