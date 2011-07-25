package br.com.caelum.revolution.builds.ant;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildException;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.executor.CommandExecutor;
import br.com.caelum.revolution.scm.SCM;

public class Ant implements Build {

	private final CommandExecutor executor;
	private final String task;
	private final String buildPath;
	private final SCM scm;

	public Ant(SCM scm, CommandExecutor executor, String task, String buildPath) {
		this.scm = scm;
		this.executor = executor;
		this.task = task;
		this.buildPath = buildPath;
	}

	public BuildResult build(String commitId) throws BuildException {
		try {
			String path = scm.goTo(commitId);
			executor.execute("ant " + task, path);
			
			return new BuildResult(buildPath);
		}
		catch(Exception e) {
			throw new BuildException(e);
		}
	}

}
