package edu.usp.ime.revolution.builds.ant;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildException;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;

public class Ant implements Build {

	private final CommandExecutor executor;
	private final String task;
	private final String buildPath;

	public Ant(CommandExecutor executor, String task, String buildPath) {
		this.executor = executor;
		this.task = task;
		this.buildPath = buildPath;
	}

	public BuildResult build(String path) throws BuildException {
		try {
			executor.execute("ant " + task, path);
			
			return new BuildResult(buildPath);
		}
		catch(Exception e) {
			throw new BuildException(e);
		}
	}

}
