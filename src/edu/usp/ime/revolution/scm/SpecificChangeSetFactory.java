package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;

public interface SpecificChangeSetFactory {
	ChangeSetCollection build(SCM scm, Config config);
}
