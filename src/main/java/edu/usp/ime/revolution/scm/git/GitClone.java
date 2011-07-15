package edu.usp.ime.revolution.scm.git;

import java.util.List;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMException;

public class GitClone implements SCM{

	private final Git git;

	public GitClone(String gitRepository, String destiny, CommandExecutor exec, Git git) {
		this.git = git;
		
		try {
			exec.runCommand("git clone " + gitRepository + " " + destiny);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}
	
	public List<ChangeSetInfo> getChangeSetList() {
		return git.getChangeSetList();
	}

	public ChangeSet getChangeSet(ChangeSetInfo cs) {
		return git.getChangeSet(cs);
	}

	public Commit detail(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
