package edu.usp.ime.revolution.scm.git;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.executor.SimpleCommandExecutor;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SpecificSCMFactory;

public class GitCloneFactory implements SpecificSCMFactory {

	public SCM build(Config config) {
		return new GitClone(
				config.get("scm.remoteRepository"),
				config.get("scm.repository"),
				new SimpleCommandExecutor(),
				new Git(config.get("scm.repository"), new GitLogParser(), new GitDiffParser(), new GitBlameParser(), new SimpleCommandExecutor())
			);
	}

}
