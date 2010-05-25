package edu.usp.ime.revolution.scm.git;

import java.util.List;

import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMException;

public class Git implements SCM {

	private final String repository;
	private final GitLogParser logParser;
	private final CommandExecutor exec;

	public Git(String repository, GitLogParser logParser, CommandExecutor exec) {
		this.repository = repository;
		this.logParser = logParser;
		this.exec = exec;
	}

	public ChangeSet getChangeSet(String name) {
		try {
			exec.setWorkingDirectory(repository);
			exec.runCommand("git checkout master");
			exec.runCommand("git branch --no-track -f revolution " + name);
			exec.runCommand("git checkout revolution ");
		}
		catch(Exception e) {
			throw new SCMException(e);
		}
		
		return new GitChangeSet(name, repository);
	}

	public List<String> getChangeSetList() {
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
