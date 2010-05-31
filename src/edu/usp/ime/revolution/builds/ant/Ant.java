package edu.usp.ime.revolution.builds.ant;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildException;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;

public class Ant implements Build {

	private final CommandExecutor executor;
	private final String task;

	public Ant(CommandExecutor executor, String task) {
		this.executor = executor;
		this.task = task;
	}

	public BuildResult build(ChangeSet set) throws BuildException {
		try {
			executor.setWorkingDirectory(set.getPath());
			executor.runCommand("ant " + task);
			
			return new BuildResult();
		}
		catch(Exception e) {
			throw new BuildException(e);
		}
	}

}
