package edu.usp.ime.revolution.builds.ant;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildException;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;

public class Ant implements Build {

	private final CommandExecutor executor;
	private final String task;
	private final String buildPath;

	public Ant(CommandExecutor executor, String task, String buildPath) {
		this.executor = executor;
		this.task = task;
		this.buildPath = buildPath;
	}

	public BuildResult build(ChangeSet set) throws BuildException {
		try {
			executor.setWorkingDirectory(set.getPath());
			executor.runCommand("ant " + task);
			
			return new BuildResult(buildPath);
		}
		catch(Exception e) {
			throw new BuildException(e);
		}
	}

}
