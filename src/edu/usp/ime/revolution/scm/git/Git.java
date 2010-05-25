package edu.usp.ime.revolution.scm.git;

import java.util.List;
import edu.usp.ime.revolution.executor.SysCommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMException;

public class Git implements SCM {

	private final String repository;
	private final GitLogParser logParser;

	public Git(String repository, GitLogParser logParser) {
		this.repository = repository;
		this.logParser = logParser;
	}

	public ChangeSet getChangeSet(String name) {
		// TODO implementar get change set
		return new GitChangeSet(name);
	}

	public List<String> getChangeSetList() {
		SysCommandExecutor exec = new SysCommandExecutor();

		try {
			exec.setWorkingDirectory(repository);
			exec.runCommand("git log --reverse --format=oneline");
			
			String output = exec.getCommandOutput();
			return logParser.parse(output);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}

}
